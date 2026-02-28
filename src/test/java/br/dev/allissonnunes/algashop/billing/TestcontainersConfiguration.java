package br.dev.allissonnunes.algashop.billing;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    public static final PostgreSQLContainer POSTGRESQL_CONTAINER
            = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        return POSTGRESQL_CONTAINER;
    }

}
