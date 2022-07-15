package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlEdgeExternal {
    private GraphqlNodeExternal node;
    private String cursor;

    public GraphqlNodeExternal getNode() {
        return node;
    }

    public String getCursor() {
        return cursor;
    }
}
