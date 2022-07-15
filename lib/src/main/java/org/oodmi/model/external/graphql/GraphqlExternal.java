package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodmi.model.PullRequest;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlExternal {
    private GraphqlDataExternal data;

    public GraphqlDataExternal getData() {
        return data;
    }

    public List<PullRequest> map() {
        return this.getData()
                .getSearch()
                .getEdges().stream()
                .map(edge -> {
                    GraphqlNodeExternal node = edge.getNode();
                    return new PullRequest()
                            .setName(node.getAuthor().getName())
                            .setEmail(node.getAuthor().getEmail())
                            .setLogin(node.getAuthor().getLogin())
                            .setTitle(node.getTitle())
                            .setSummary(node.getBody())
                            .setNumber(node.getNumber())
                            .setState(node.getState().toLowerCase())
                            .setCreatedAt(node.getCreatedAt())
                            .setUpdatedAt(node.getUpdatedAt())
                            .setFrom(node.getHeadRefName())
                            .setInto(node.getBaseRefName());
                }).collect(Collectors.toList());
    }
}
