package br.com.bcp.hms.tenant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;


@Component
public class TenantInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(TenantInterceptor.class);

    @Override
    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String tenantIdentifier = (String) pathVariables.get("tenant");
        log.info(">> Interceptor PRE tenant {}", tenantIdentifier);
        if (tenantIdentifier != null) {
            ThreadContext.setCurrentTenant(tenantIdentifier);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    @Nullable ModelAndView modelAndView) throws Exception {
        log.info(">> Interceptor POST");
        ThreadContext.clear();
    }
}
