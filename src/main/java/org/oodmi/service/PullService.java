package org.oodmi.service;

import org.oodmi.model.commit.CommitInfo;
import org.oodmi.model.pull.Pull;
import org.oodmi.model.pull.PullState;
import org.oodmi.model.request.MergeRequest;
import org.oodmi.model.request.MergeResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class PullService {

    private final RestTemplate restTemplate;

    public PullService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<Pull> getPulls(String auth) {
        return restTemplate.exchange("/pulls",
                        HttpMethod.GET,
                        new HttpEntity<>(getHttpHeaders(auth)),
                        new ParameterizedTypeReference<Collection<Pull>>() {
                        })
                .getBody();
    }

    public Collection<CommitInfo> getPullCommits(String auth, Integer id) {
        String url = String.format("/pulls/%s/commits", id);
        return restTemplate.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(getHttpHeaders(auth)),
                        new ParameterizedTypeReference<Collection<CommitInfo>>() {
                        })
                .getBody();
    }

    public MergeResponse mergePull(String auth, Integer id, MergeRequest request) {
        String url = String.format("/pulls/%s/merge", id);
        return restTemplate.exchange(url,
                        HttpMethod.PUT,
                        new HttpEntity<>(request, getHttpHeaders(auth)),
                        MergeResponse.class)
                .getBody();
    }

    public MergeResponse closePull(String auth, Integer id) {
        return managePull(auth, id, new PullState("closed"));
    }

    public MergeResponse reopenPull(String auth, Integer id) {
        return managePull(auth, id, new PullState("open"));
    }

    private MergeResponse managePull(String auth, Integer id, PullState state) {
        String url = String.format("/pulls/%s", id);
        return restTemplate.exchange(url,
                        HttpMethod.PATCH,
                        new HttpEntity<>(state, getHttpHeaders(auth)),
                        MergeResponse.class)
                .getBody();
    }

    private static HttpHeaders getHttpHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + auth);
        headers.add("Accept", "application/vnd.github.v3+json");
        return headers;
    }
}
