package com.projectanalyzer.entity.comments;

import com.projectanalyzer.types.comments.TypeComments;

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

    @Column(name = "commit_id", nullable = false)
    private long commitId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "type_comment", nullable = false)
    private TypeComments typeComment;

    @Column(name = "start_line", nullable = false)
    private int startLine;

    @Column(name = "end_line", nullable = false)
    private int endLine;

    @Column(name = "segments", nullable = false)
    private int segments;

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
     * @return TypeComments return the typeComment
     */
    public TypeComments getTypeComment() {
        return typeComment;
    }

    /**
     * @param typeComment the typeComment to set
     */
    public void setTypeComment(TypeComments typeComment) {
        this.typeComment = typeComment;
    }

    /**
     * @return int return the startLine
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * @param startLine the startLine to set
     */
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    /**
     * @return int return the endLine
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * @param endLine the endLine to set
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    /**
     * @return int return the segments
     */
    public int getSegments() {
        return segments;
    }

    /**
     * @param segments the segments to set
     */
    public void setSegments(int segments) {
        this.segments = segments;
    }

}
