package org.oodmi.model.external.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodmi.model.Branch;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchExternal {
    private String name;
    private BranchCommitExternal commit;

    public Branch map() {
        return new Branch()
                .setSha(commit.getSha())
                .setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchCommitExternal getCommit() {
        return this.commit;
    }

    public void setCommit(BranchCommitExternal commit) {
        this.commit = commit;
    }
}