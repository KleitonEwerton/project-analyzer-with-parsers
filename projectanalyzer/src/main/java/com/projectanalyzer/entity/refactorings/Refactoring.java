package com.projectanalyzer.entity.refactorings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Refactoring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long commitId;

}
