package dev.andole.datajdbc.domain;


import org.springframework.data.annotation.Id;

public class Commit {
    @Id
    private Long id;

    private String uuid;
    private String message;

    private Branch branch;
}
