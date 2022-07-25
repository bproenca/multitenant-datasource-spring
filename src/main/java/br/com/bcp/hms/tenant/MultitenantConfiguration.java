package br.com.bcp.hms.tenant;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MultitenantConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MultitenantConfiguration.class);

    @Autowired
    private DataSourceProperties properties;
    
    @Autowired
    private TenantDataSourceProperties tenantDataSourceProperties;

    /**
     * Defines the data source for the application
     * @return
     */
    @Bean
    public DataSource dataSource() {
        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(buildTenantsDataSource());

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();

        return dataSource;
    }

    private Map<Object, Object> buildTenantsDataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (Map<String, String> tenantProperties : tenantDataSourceProperties.getDatasource()) {
            log.info("Building Tenant Datasource:  {}", tenantProperties.get("poolName"));
    
            HikariDataSource ds = new HikariDataSource();
            ds.setPoolName("Hikari-" + tenantProperties.get("poolName"));
            ds.setJdbcUrl(tenantProperties.get("jdbcUrl"));
            ds.setUsername(tenantProperties.get("username"));
            ds.setPassword(tenantProperties.get("password"));
            configDS(ds);
            
            resolvedDataSources.put(tenantProperties.get("poolName"), ds);
        }

        return resolvedDataSources;
    }

    /**
     * Creates the default data source for the application
     * @return
     */
    private DataSource defaultDataSource() {
        log.info("Building Default  Datasource:  {}", properties.getUrl());

        HikariDataSource ds = new HikariDataSource();
        ds.setPoolName("Hikari-Main");
        ds.setJdbcUrl(properties.getUrl());
        ds.setUsername(properties.getUsername());
        ds.setPassword(properties.getPassword());
        configDS(ds);
        return ds;
    }

    private void configDS(HikariDataSource ds) {
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setAllowPoolSuspension(false);
        ds.setAutoCommit(true);
        ds.setConnectionTimeout(30000);
        ds.setIdleTimeout(300000);
        ds.setMinimumIdle(4);
        ds.setInitializationFailTimeout(1);
        ds.setIsolateInternalQueries(false);
        ds.setLeakDetectionThreshold(600000);
        ds.setMaxLifetime(1800000);
        ds.setMaximumPoolSize(7);
        ds.setReadOnly(false);
        ds.setRegisterMbeans(false);
        ds.setValidationTimeout(5000);

        StringBuilder initSQL = new StringBuilder();
        initSQL
            .append("BEGIN")
            //.append(" execute immediate 'alter session set NLS_DATE_FORMAT = ''DD/MM/YYYY'''; ")
            .append(" dbms_session.set_nls('NLS_LANGUAGE', '''BRAZILIAN PORTUGUESE''');  ")
            .append(" dbms_session.set_nls('NLS_TERRITORY', '''BRAZIL''');  ")
            .append(" dbms_session.set_nls('NLS_CURRENCY', '''R$''');  ")
            .append(" dbms_session.set_nls('NLS_ISO_CURRENCY', '''BRAZIL''');  ")
            .append(" dbms_session.set_nls('NLS_NUMERIC_CHARACTERS', ''',.''');  ")
            .append(" dbms_session.set_nls('NLS_CALENDAR', '''GREGORIAN''');  ")
            .append(" dbms_session.set_nls('NLS_DATE_FORMAT', '''DD/MM/YYYY''');  ")
            .append(" dbms_session.set_nls('NLS_DATE_LANGUAGE', '''BRAZILIAN PORTUGUESE''');  ")
            .append(" dbms_session.set_nls('NLS_SORT', '''WEST_EUROPEAN''');  ")
            .append(" dbms_session.set_nls('NLS_TIME_FORMAT', '''HH24:MI:SSXFF''');  ")
            .append(" dbms_session.set_nls('NLS_TIMESTAMP_FORMAT', '''DD/MM/YYYY HH24:MI:SSXFF''');  ")
            .append(" dbms_session.set_nls('NLS_TIME_TZ_FORMAT', '''HH24:MI:SSXFF TZR''');  ")
            .append(" dbms_session.set_nls('NLS_TIMESTAMP_TZ_FORMAT', '''DD/MM/YYYY HH24:MI:SSXFF TZR''');  ")
            .append(" dbms_session.set_nls('NLS_DUAL_CURRENCY', '''R$''');  ")
            .append(" dbms_session.set_nls('NLS_COMP', '''BINARY''');  ")
            .append(" dbms_session.set_nls('NLS_LENGTH_SEMANTICS', '''CHAR''');  ")
            .append(" dbms_session.set_nls('NLS_NCHAR_CONV_EXCP', '''FALSE''');  ")
            .append("END;");

        ds.setConnectionInitSql(initSQL.toString());        
    }
}
