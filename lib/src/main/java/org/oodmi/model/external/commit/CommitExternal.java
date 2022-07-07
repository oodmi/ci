package org.oodmi.model.external.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodmi.model.Commit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitExternal {
    private String sha;
    private CommitInfoExternal commit;

    public Commit map() {
        return new Commit()
                .setAuthor(commit.getAuthor().getName())
                .setEmail(commit.getAuthor().getEmail())
                .setDate(commit.getAuthor().getDate())
                .setMessage(commit.getMessage())
                .setSha(sha);
    }

    public String getSha() {
        return this.sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public CommitInfoExternal getCommit() {
        return this.commit;
    }

    public void setCommit(CommitInfoExternal commit) {
        this.commit = commit;
    }
}