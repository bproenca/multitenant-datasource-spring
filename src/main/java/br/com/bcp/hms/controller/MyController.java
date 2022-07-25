package br.com.bcp.hms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bcp.hms.repository.TenantRepo;
import br.com.bcp.hms.tenant.TenantContext;

@RestController
public class MyController {
    
    @Autowired
    private TenantRepo tenantRepo;

    private Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    @RequestMapping(value = "/tenant/{tenant}/data")
	public List<Map<String, Object>> getPB(@PathVariable String tenant) {
        log.info(">> [GET] /tenant/{}/data at {}", tenant, LocalDateTime.now());
        log.info(">> ThreadLocal tenant {}", TenantContext.getCurrentTenant());
        return tenantRepo.getData();
	}

    @RequestMapping(value = "/ping")
	public String ping() {
        return "pong";
	}
}