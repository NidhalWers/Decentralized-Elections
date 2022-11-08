package com.septgrandcorsaire.blockchain.api.auth;

import com.septgrandcorsaire.blockchain.api.error.ErrorResource;
import com.septgrandcorsaire.blockchain.api.error.exception.ElectionNotFoundException;
import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
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

        String key = currentRequest.getHeader("Key") == null ? "" : currentRequest.getHeader("Key");

        if (path.equals("/smart-vote/api/v1/get-sandbox/")) {
            String electionName = "sandbox";
            boolean isExactApiKey = this.apiKeyRepository.isApiKeyCorrespondingToElection(key, electionName);
            handleApiKeyAccuracy(servletResponse, chain, myRequestWrapper, isExactApiKey, electionName);
            return;
        }
        if (!path.startsWith("/smart-vote/api/v1/vote")) { //todo equals ?
            chain.doFilter(myRequestWrapper, servletResponse);
            return;
        }

        String electionName = getElectionNameFromBody(myRequestWrapper.getReader().lines().reduce("", String::concat));

        boolean isExactApiKey = false;
        try {
            isExactApiKey = this.apiKeyRepository.isApiKeyCorrespondingToElection(key, electionName);
        } catch (ElectionNotFoundException e) {
            returnErrorMessage(servletResponse, e.getMessage());
        }
        handleApiKeyAccuracy(servletResponse, chain, myRequestWrapper, isExactApiKey, electionName);
    }

    private static void handleApiKeyAccuracy(
            ServletResponse servletResponse,
            FilterChain chain,
            MyRequestWrapper myRequestWrapper,
            boolean isExactApiKey, String electionName) throws IOException, ServletException {
        if (isExactApiKey) {
            chain.doFilter(myRequestWrapper, servletResponse);
        } else {
            returnErrorMessage(servletResponse, electionName);
            //throw new InvalidApiKeyException(String.format(ErrorCode.INVALID_PARAMETER.getDefaultMessage(), electionName));
        }
    }

    private static void returnErrorMessage(ServletResponse servletResponse, String electionName) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String errorMessage = String.format(ErrorCode.INVALID_API_KEY.getDefaultMessage(), electionName);
        ErrorResource errorResource = new ErrorResource(ErrorCode.INVALID_API_KEY.getValue(), errorMessage);
        String jsonError = JsonService.toJson(errorResource);

        resp.reset();
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setContentLength(jsonError.length());
        servletResponse.getWriter().write(jsonError); //todo throw exception
    }

    private String getElectionNameFromBody(String body) {
        VotePayload votePayload = JsonService.votePayloadfromJson(body);
        return votePayload.getElectionName();

    }

}
