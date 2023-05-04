package com.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@Slf4j
public class MicroservicesTest {

    RestTemplate restTemplate = new RestTemplate();

    static Network network = Network.newNetwork();

    @Container
    static GenericContainer container = new GenericContainer<>(DockerImageName.parse("liqoo/product-service:latest"))
            .withCreateContainerCmdModifier(cmd -> cmd.withName("product-service").withHostName("product-service"))
            .withExposedPorts(8080)
            .withNetwork(network)
            .dependsOn(new GenericContainer<>(DockerImageName.parse("mongo:latest"))
                    .withCreateContainerCmdModifier(cmd -> cmd.withName("product-db").withHostName("product-db"))
                    .withExposedPorts(27017)
                    .withNetwork(network))
            .dependsOn(
                    new GenericContainer<>(DockerImageName.parse("liqoo/gateway-server"))
                            .withCreateContainerCmdModifier(cmd -> cmd.withName("gateway-server").withHostName("gateway-server"))
                            .withExposedPorts(8084)
                            .withNetwork(network)
                            .dependsOn(
                                    new GenericContainer<>(DockerImageName.parse("liqoo/discovery-server"))
                                            .withExposedPorts(8761)
                                            .withCreateContainerCmdModifier(cmd -> cmd.withHostName("discovery-server"))
                                            .withNetwork(network)
                            )
            );

    @Test
    @DisplayName("Get List of Product")
    public void test(){
        String url = "http://" + container.getHost() + ":" + container.getFirstMappedPort() + "/api/product";
        log.info("URL :: {}", url);
        log.info("LOG :: {}", container.getLogs());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("RESPONSE :: {}", response.getBody());
        log.info("STATUS CODE :: {}", response.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
