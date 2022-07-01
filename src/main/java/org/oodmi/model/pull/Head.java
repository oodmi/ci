package org.oodmi.model.pull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Head {
    private String label;
    private String ref;
    private String sha;
}