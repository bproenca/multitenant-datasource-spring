package br.com.bcp.hms.tenant;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

@Component
@Order(1)
public class TenantFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(TenantFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        log.info(">> Filter Logging Request  {} : {}", req.getMethod(), req.getRequestURI());

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables != null) {
            String tenantIdentifier = (String) pathVariables.get("tenant");
            TenantContext.setCurrentTenant(tenantIdentifier);
        } else {
            String requestURI = req.getRequestURI();
            if (requestURI.contains("dbone")) {
                TenantContext.setCurrentTenant("dbone");
            } else if (requestURI.contains("dbtwo")) {
                TenantContext.setCurrentTenant("dbtwo");
            }
        }

		chain.doFilter(request, response);
		log.info(">> Filter Logging Response :{}", res.getContentType());
    }

}