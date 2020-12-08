package dev.andole.datajdbc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    @Id
    private Long id;

    private String name;
    private Repository repository;

    @MappedCollection(keyColumn = "branch_id")
    private List<Commit> commits = new ArrayList<>();
}
