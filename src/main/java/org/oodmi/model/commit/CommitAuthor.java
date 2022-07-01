package org.oodmi.model.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitAuthor {
    private String name;
    private String email;
    private LocalDateTime date;
}