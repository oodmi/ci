package org.oodmi.model.external.pull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserExternal {
    private String login;
    private String email;
    private String name;

    public String getLogin() {
        return this.login;
    }

    public UserExternal setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserExternal setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserExternal setName(String name) {
        this.name = name;
        return this;
    }
}