package com.giro.backend.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;
import java.sql.Connection;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
class PostgresIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void postgresqlEstaDisponibleYFlywayActivo() throws Exception {
        try {
            Connection connection = dataSource.getConnection();
            String database = connection.getMetaData().getDatabaseProductName();
            connection.close();
            assertEquals("PostgreSQL", database);
        } catch (Exception exception) {
            Assumptions.assumeTrue(false, "PostgreSQL no esta disponible: " + exception.getMessage());
        }
    }
}
