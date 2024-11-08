package com.projectanalyzer.entities.refactoring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Refactoring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "commit_id", nullable = false)
    private long commitId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "refactoring_type", nullable = false)
    private String refactoringType;

    @Column(name = "description", nullable = false)
    private String description;

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return long return the commitId
     */
    public long getCommitId() {
        return commitId;
    }

    /**
     * @param commitId the commitId to set
     */
    public void setCommitId(long commitId) {
        this.commitId = commitId;
    }

    /**
     * @return String return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return String return the refactoringType
     */
    public String getRefactoringType() {
        return refactoringType;
    }

    /**
     * @param refactoringType the refactoringType to set
     */
    public void setRefactoringType(String refactoringType) {
        this.refactoringType = refactoringType;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
