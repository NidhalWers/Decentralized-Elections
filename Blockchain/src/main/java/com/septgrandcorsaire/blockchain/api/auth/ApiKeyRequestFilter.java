package com.septgrandcorsaire.blockchain.api.auth;

import com.septgrandcorsaire.blockchain.api.payload.VotePayload;
import com.septgrandcorsaire.blockchain.infrastructure.dao.ApiKeyRepository;
import com.septgrandcorsaire.blockchain.infrastructure.http.MyRequestWrapper;
import com.septgrandcorsaire.blockchain.infrastructure.service.JsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(ApiKeyRequestFilter.class);

    private ApiKeyRepository apiKeyRepository;

    public ApiKeyRequestFilter() {
        apiKeyRepository = ApiKeyRepository.INSTANCE;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(currentRequest);
        String path = currentRequest.getRequestURI();

        if (!path.startsWith("/smart-vote/api/v1/vote")) {
            chain.doFilter(myRequestWrapper, servletResponse);
            return;
        }

        String key = currentRequest.getHeader("Key") == null ? "" : currentRequest.getHeader("Key");
        String electionName = getElectionNameFromBody(myRequestWrapper.getReader().lines().reduce("", String::concat));

        boolean isExactApiKey = this.apiKeyRepository.isApiKeyCorrespondingToElection(key, electionName);
        if (isExactApiKey) {
            chain.doFilter(myRequestWrapper, servletResponse);
        } else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.setContentLength(error.length());
            servletResponse.getWriter().write(error);
        }

    }

    private String getElectionNameFromBody(String body) {
        VotePayload votePayload = JsonService.votePayloadfromJson(body);
        return votePayload.getElectionName();

    }

}
