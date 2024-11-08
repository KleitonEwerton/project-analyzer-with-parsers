package com.projectanalyzer.entity.commits;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String hash;

    private Set<String> parentsHash;

    private String develop;
}
