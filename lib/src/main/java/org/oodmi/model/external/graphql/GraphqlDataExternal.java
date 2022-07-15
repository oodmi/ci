package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlDataExternal {
    private GraphqlSearchExternal search;

    public GraphqlSearchExternal getSearch() {
        return search;
    }
}
