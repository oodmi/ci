package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlPageInfoExternal {
    private Boolean hasNextPage;
    private String endCursor;
    private String startCursor;
    private Boolean hasPreviousPage;

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public String getEndCursor() {
        return endCursor;
    }

    public String getStartCursor() {
        return startCursor;
    }

    public Boolean getHasPreviousPage() {
        return hasPreviousPage;
    }
}
