package org.oodmi.model.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfo {
    private String sha;
    private Commit commit;
}