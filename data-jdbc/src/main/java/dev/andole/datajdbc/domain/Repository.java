package dev.andole.datajdbc.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    @Id
    private Long id;

    private String name;
    private long star;
    private long forked;
    private String description;
    @MappedCollection(keyColumn = "repository_id")
    private List<Branch> branches = new ArrayList<>();

    public Repository(String name, long star, long forked, String description) {
        this.name = name;
        this.star = star;
        this.forked = forked;
        this.description = description;
    }
}
