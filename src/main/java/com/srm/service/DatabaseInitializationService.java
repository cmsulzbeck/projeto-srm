package com.srm.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Service
public class DatabaseInitializationService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public DatabaseInitializationService(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initializeDatabase() {
        if (areTablesEmpty()) {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao inicializar o banco de dados", e);
            }
        }
    }

    private boolean areTablesEmpty() {
        try {
            Integer moedasCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MOEDAS", Integer.class);
            Integer reinosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM REINOS", Integer.class);
            Integer produtosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PRODUTOS", Integer.class);
            Integer transacoesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRANSACOES", Integer.class);
            
            return (moedasCount != null && moedasCount == 0) && 
                   (reinosCount != null && reinosCount == 0) && 
                   (produtosCount != null && produtosCount == 0) && 
                   (transacoesCount != null && transacoesCount == 0);
        } catch (Exception e) {
            return true;
        }
    }
} 