package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import org.example.configurations.DataStaxAstraProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.sql.ResultSet;

@SpringBootApplication(exclude = CassandraDataAutoConfiguration.class)
public class KeyspacesexampleApplication  {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyspacesexampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KeyspacesexampleApplication.class, args);

    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
}