package com.sumcli.gitcli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubResponse {
    private List<Repository> items;

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}
