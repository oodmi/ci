package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlSearchExternal {
    private Integer issueCount;

    private List<GraphqlEdgeExternal> edges;
    private GraphqlPageInfoExternal pageInfo;

    public Integer getIssueCount() {
        return issueCount;
    }

    public List<GraphqlEdgeExternal> getEdges() {
        return edges;
    }

    public GraphqlPageInfoExternal getPageInfo() {
        return pageInfo;
    }
}
