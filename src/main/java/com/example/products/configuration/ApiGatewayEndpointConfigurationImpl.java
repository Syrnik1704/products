package com.example.products.configuration;

import com.example.endpoints_registration.ApiGatewayEndpointConfiguration;
import com.example.endpoints_registration.entity.Endpoint;
import com.example.endpoints_registration.entity.HttpMethod;
import com.example.endpoints_registration.entity.Response;
import com.example.endpoints_registration.entity.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiGatewayEndpointConfigurationImpl implements ApiGatewayEndpointConfiguration {

    @Value("${api-gateway.url}")
    private String GATEWAY_URL;

    @PostConstruct
    public void startOperation(){
        initMap();
        register();
    }

    @Override
    public void initMap() {
        endpointList.add(new Endpoint("/api/v1/product", HttpMethod.GET, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/product", HttpMethod.POST, Role.ADMIN));
        endpointList.add(new Endpoint("/api/v1/product", HttpMethod.DELETE, Role.ADMIN));
        endpointList.add(new Endpoint("/api/v1/product/getExternal", HttpMethod.GET, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/category", HttpMethod.GET, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/category", HttpMethod.POST, Role.ADMIN));
    }

    @Override
    public void register() {
        initMap();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Response> response = restTemplate.postForEntity(GATEWAY_URL, endpointList, Response.class);
        if (response.getStatusCode().isError()) throw new RuntimeException();
    }
}

