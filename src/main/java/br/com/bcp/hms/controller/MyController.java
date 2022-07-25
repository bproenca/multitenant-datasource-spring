package br.com.bcp.hms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.bcp.hms.repository.TenantRepo;

@RestController
public class MyController {
    
    @Autowired
    private TenantRepo tenantRepo;

    private Logger log = LoggerFactory.getLogger(MyController.class);

    @GetMapping("/tenant/{tenant}/data")
	public List<Map<String, Object>> getPB(@PathVariable String tenant) {
        log.info(">> [GET] /tenant/{}/data at {}", tenant, LocalDateTime.now());
        return tenantRepo.getData();
	}

    @GetMapping("/ping")
	public String ping() {
        log.info(">> [GET] /ping at {}", LocalDateTime.now());
        return "pong";
	}
}