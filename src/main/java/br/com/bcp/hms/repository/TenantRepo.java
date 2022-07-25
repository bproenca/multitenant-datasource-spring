package br.com.bcp.hms.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bcp.hms.tenant.TenantContext;

@Repository
public class TenantRepo {

    @Autowired
	private JdbcTemplate jdbcTemplate;

    private static Logger log = LoggerFactory.getLogger(TenantRepo.class);

    public List<Map<String, Object>> getData() {
        log.info(">> Repo JdcbTemplate using DS tenant: {}", TenantContext.getCurrentTenant());
        return jdbcTemplate.queryForList("select * from abcd");
	}
    
}
