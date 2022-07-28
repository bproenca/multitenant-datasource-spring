package br.com.bcp.hms.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bcp.hms.tenant.MultitenantDataSource;

@Repository
public class TenantRepo {

    private static Logger log = LoggerFactory.getLogger(TenantRepo.class);

    @Autowired
    @Qualifier("multitenantDataSource")
    private MultitenantDataSource multitenantDataSource;

    public List<Map<String, Object>> getData(String tenant) {
        log.info(">> Repo JdcbTemplate using DS tenant: {}", tenant);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(multitenantDataSource.getDataSource(tenant));
        return jdbcTemplate.queryForList("select * from abcd");
	}
    
}
