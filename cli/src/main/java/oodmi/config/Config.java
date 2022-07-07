package oodmi.config;

import oodmi.properties.GitHubProperties;
import org.oodmi.client.BranchClient;
import org.oodmi.client.PullRequestClient;
import org.oodmi.client.TokenClient;
import org.oodmi.model.GithubSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GitHubProperties.class)
public class Config {

    @Bean
    public BranchClient branchClient(GitHubProperties gitHubProperties) {
        return new BranchClient(new GithubSettings(
                gitHubProperties.getToken(),
                gitHubProperties.getName(),
                gitHubProperties.getProject(),
                3));
    }

    @Bean
    public PullRequestClient pullRequestClient(GitHubProperties gitHubProperties) {
        return new PullRequestClient(new GithubSettings(
                gitHubProperties.getToken(),
                gitHubProperties.getName(),
                gitHubProperties.getProject(),
                3));
    }

    @Bean
    public TokenClient tokenClient(GitHubProperties gitHubProperties) {
        return new TokenClient(new GithubSettings(
                gitHubProperties.getToken(),
                gitHubProperties.getName(),
                gitHubProperties.getProject(),
                3));
    }
}
