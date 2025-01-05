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

    @Column(nullable = false)
    private String hashPackage;

    @Column(nullable = false)
    private String hashPackageClass;

    @Column(nullable = false)
    private int qntCommentLine;

    @Column(nullable = false)
    private int qntCommentBlock;

    @Column(nullable = false)
    private int qntCommentDoc;

    @Column(nullable = false)
    private int qntSegmentos;

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
     * @return String return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return String return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return String return the hashPackage
     */
    public String getHashPackage() {
        return hashPackage;
    }

    /**
     * @param hashPackage the hashPackage to set
     */
    public void setHashPackage(String hashPackage) {
        this.hashPackage = hashPackage;
    }

    /**
     * @return String return the hashPackageClass
     */
    public String getHashPackageClass() {
        return hashPackageClass;
    }

    /**
     * @param hashPackageClass the hashPackageClass to set
     */
    public void setHashPackageClass(String hashPackageClass) {
        this.hashPackageClass = hashPackageClass;
    }

    /**
     * @return int return the qntSegmentos
     */
    public int getQntSegmentos() {
        return qntSegmentos;
    }

    /**
     * @param qntSegmentos the qntSegmentos to set
     */
    public void setQntSegmentos(int qntSegmentos) {
        this.qntSegmentos = qntSegmentos;
    }

    /**
     * @return int return the qntCommentLine
     */
    public int getQntCommentLine() {
        return qntCommentLine;
    }

    /**
     * @param qntCommentLine the qntCommentLine to set
     */
    public void setQntCommentLine(int qntCommentLine) {
        this.qntCommentLine = qntCommentLine;
    }

    /**
     * @return int return the qntCommentBlock
     */
    public int getQntCommentBlock() {
        return qntCommentBlock;
    }

    /**
     * @param qntCommentBlock the qntCommentBlock to set
     */
    public void setQntCommentBlock(int qntCommentBlock) {
        this.qntCommentBlock = qntCommentBlock;
    }

    /**
     * @return int return the qntCommentDoc
     */
    public int getQntCommentDoc() {
        return qntCommentDoc;
    }

    /**
     * @param qntCommentDoc the qntCommentDoc to set
     */
    public void setQntCommentDoc(int qntCommentDoc) {
        this.qntCommentDoc = qntCommentDoc;
    }

}
