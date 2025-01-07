package com.minerprojects.data;

import java.util.HashSet;
import java.util.Set;

import com.minerprojects.CommentReporter;
import com.minerprojects.CommitReporter;

public class DataComment {

    public static Set<DataComment> dataComments = new HashSet();

    private String projectName;

    private String hash;

    private String hashPackage;

    private String hashPackageClass;

    private int qntCommentLine;

    private int qntCommentBlock;

    private int qntCommentDoc;

    private int qntSegmentos;

    public DataComment(CommitReporter commit, String hashPackage, String hashPackageClass) {

        this.projectName = commit.getProjectName();

        this.hash = commit.getHash();
        this.hashPackage = hashPackage;
        this.hashPackageClass = hashPackageClass;

        this.qntCommentLine = 0;
        this.qntCommentBlock = 0;
        this.qntCommentDoc = 0;
        this.qntSegmentos = 0;
        dataComments.add(this);
    }

    public DataComment() {
    }

    public static void updateDadosByhashClassPath(CommentReporter comment) {

        dataComments.stream().filter(c -> c.getHashPackageClass().equals(comment.getHashPackageClass()))
                .forEach(c -> {

                    c.qntCommentLine += comment.getType() == 1 ? 1 : 0;
                    c.qntCommentBlock += comment.getType() == 2 ? 1 : 0;
                    c.qntCommentDoc += comment.getType() == 3 ? 1 : 0;
                    c.qntSegmentos += comment.getSegmentos();

                });

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
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {

        return this.hash;
    }

    /**
     * {
     * }
     * 
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

    @Override
    public String toString() {
        return "DataComment [projectName=" + projectName + ", hash=" + hash + ", hashPackage=" + hashPackage
                + ", hashPackageClass=" + hashPackageClass + ", qntCommentLine=" + qntCommentLine
                + ", qntCommentBlock=" + qntCommentBlock + ", qntCommentDoc=" + qntCommentDoc
                + ", qntSegmentos=" + qntSegmentos + "]";
    }
}