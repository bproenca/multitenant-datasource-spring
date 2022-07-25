package br.com.bcp.hms.tenant;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="tenant")
public class TenantDataSourceProperties {

    private List<Map<String, String>> datasource;

    public List<Map<String, String>> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<Map<String, String>> datasource) {
        this.datasource = datasource;
    }
    
}