package org.oodmi.model.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {
    private String name;
    private Commit commit;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commit getCommit() {
        return this.commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}