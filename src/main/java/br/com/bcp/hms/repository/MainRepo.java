package br.com.bcp.hms.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bcp.hms.tenant.MultitenantDataSource;

@Repository
public class MainRepo {
    
    private static Logger log = LoggerFactory.getLogger(MainRepo.class);
       
    @Autowired
    private MultitenantDataSource dataSource;

    public List<Map<String, Object>> getDataBean() {
        log.info(">> Repo MAIN using DS tenant");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.getResolvedDefaultDataSource());
        return jdbcTemplate.queryForList("select * from abcd");
	}
    
}