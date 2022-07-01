package org.oodmi.model.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String sha;
    private CommitInfo commit;

    public String getSha() {
        return this.sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public CommitInfo getCommit() {
        return this.commit;
    }

    public void setCommit(CommitInfo commit) {
        this.commit = commit;
    }
}