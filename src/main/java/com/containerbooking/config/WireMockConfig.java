package com.containerbooking.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
@Configuration
@Profile("dev")
public class WireMockConfig {
    @Value("${wiremock.server.port}")
    private int wireMockServerPort;
    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options()
                .port(wireMockServerPort));

        wireMockServer.stubFor(any(urlPathEqualTo("/checkAvailable"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"available\": 6")));

        return wireMockServer;
    }
}



