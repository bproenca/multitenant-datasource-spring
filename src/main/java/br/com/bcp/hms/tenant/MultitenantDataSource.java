package br.com.bcp.hms.tenant;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.zaxxer.hikari.HikariDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(MultitenantDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        final String threadName = Thread.currentThread().getName();
        final Long threadId = Thread.currentThread().getId();
        log.info(">> DetermineCurrentLookupKey running on: " + threadName + " - " + threadId + " returning key: "
                + ThreadContext
                        .getCurrentTenant());

        return ThreadContext.getCurrentTenant();
    }

    public void close() {
        log.info(">> BEGIN Destroy Bean MultitenantDataSource");

        Map<Object, DataSource> resolvedDataSources = this.getResolvedDataSources();
        resolvedDataSources.values().forEach(rds -> {
            HikariDataSource hds = (HikariDataSource) rds;
            if (!hds.isClosed()) {
                log.info(">> Closing Tenant DS {}", hds.getPoolName());
                hds.close();
            }
        });

        HikariDataSource ds = (HikariDataSource) this.getResolvedDefaultDataSource();
        if (!ds.isClosed()) {
            log.info(">> Closing Default DS {}", ds.getPoolName());
            ds.close();
        }

        log.info(">> END Destroy Bean MultitenantDataSource");
    }
}
