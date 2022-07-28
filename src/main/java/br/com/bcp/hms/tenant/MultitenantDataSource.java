package br.com.bcp.hms.tenant;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public class MultitenantDataSource {

    private static final Logger log = LoggerFactory.getLogger(MultitenantDataSource.class);

    private final Map<String, DataSource> dsMap = new HashMap<String, DataSource>();

    public void addDataSource(String name, DataSource dataSource) {
        dsMap.put(name, dataSource);
    }

    @PreDestroy
    public void close() {
        log.info(">> BEGIN Destroy Bean MultitenantDataSource hashcode = {}", hashCode());
        
        dsMap.values().forEach(rds -> {
            HikariDataSource hds = (HikariDataSource) rds;
            if (!hds.isClosed()) {
                log.info(">> Closing Tenant DS {}", hds.getPoolName());
                hds.close();
            }
        });
        log.info(">> END Destroy Bean MultitenantDataSource");
    }
    
    public DataSource getDataSource(String tenant) {
        log.info(">> Get DataSource with name {} in MDS hashcode = {}", tenant, hashCode());
        DataSource dataSource = dsMap.get(tenant);
		if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + tenant + "]");
		}
        return dataSource;
    }
}
