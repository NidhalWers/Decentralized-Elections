package com.septgrandcorsaire.blockchain.api;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("webmvc")
@WebMvcTest
@ComponentScan()
public @interface CustomWebMvcTest {

}
