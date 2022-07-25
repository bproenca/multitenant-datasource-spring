package br.com.bcp.hms.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(MultitenantDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        final String threadName = Thread.currentThread().getName();
        final Long threadId = Thread.currentThread().getId();
        log.info(">> DetermineCurrentLookupKey running on: " + threadName + " - " + threadId + " returning key: " + ThreadContext
                .getCurrentTenant());

        return ThreadContext.getCurrentTenant();
    }
}
