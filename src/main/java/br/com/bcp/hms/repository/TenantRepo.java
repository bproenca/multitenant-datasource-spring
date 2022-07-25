package br.com.bcp.hms.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepo {

    @Autowired
	private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getData() {
        return jdbcTemplate.queryForList("select * from abcd");
	}
    
}
