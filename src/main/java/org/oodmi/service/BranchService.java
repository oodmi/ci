package org.oodmi.service;

import org.oodmi.model.branch.Branch;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class BranchService {

    private final RestTemplate restTemplate;

    public BranchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<Branch> getBranches(String auth) {
        return restTemplate.exchange("/branches",
                        HttpMethod.GET,
                        new HttpEntity<>(getHttpHeaders(auth)),
                        new ParameterizedTypeReference<Collection<Branch>>() {
                        })
                .getBody();
    }

    private static HttpHeaders getHttpHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + auth);
        headers.add("Accept", "application/vnd.github.v3+json");
        return headers;
    }
}
