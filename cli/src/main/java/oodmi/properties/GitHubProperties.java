package oodmi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("lib")
public class GitHubProperties {
    private String token;
    private String name;
    private String project;

    public String getToken() {
        return token;
    }

    public GitHubProperties setToken(String token) {
        this.token = token;
        return this;
    }

    public String getName() {
        return name;
    }

    public GitHubProperties setName(String name) {
        this.name = name;
        return this;
    }

    public String getProject() {
        return project;
    }

    public GitHubProperties setProject(String project) {
        this.project = project;
        return this;
    }
}
