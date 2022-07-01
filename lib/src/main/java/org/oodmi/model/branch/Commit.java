package org.oodmi.model.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String sha;

    public String getSha() {
        return this.sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}