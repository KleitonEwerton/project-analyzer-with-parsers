package com.projectanalyzer.entities.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = true)
    private String parentPackageClass;

    @Column(nullable = false)
    private int qntCommentReporterType1; // LINE

    @Column(nullable = false)
    private int qntInParentCommentReporterType1;// LINE

    @Column(nullable = false)
    private int qntCommentReporterType2; // BLOCK

    @Column(nullable = false)
    private int qntInParentCommentReporterType2; // BLOCK

    @Column(nullable = false)
    private int qntCommentReporterType3;// DOC

    @Column(nullable = false)
    private int qntInParentCommentReporterType3;// DOC

    @Column(nullable = false)
    private int qntSegmentos;

    @Column(nullable = false)
    private int qntParentSegmentos;

    @Column(nullable = true)
    private String parentHash;

    @Column(nullable = true)
    private String hashPackageClass;

}
