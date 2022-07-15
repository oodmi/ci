package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlAuthorExternal {
    private String login;
    private String email;
    private String name;

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}