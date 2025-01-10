package com.minerprojects.data;

import java.util.ArrayList;
import java.util.List;

import com.minerprojects.CommentReporter;
import com.minerprojects.CommitReporter;

public class DataComment {

    public static List<DataComment> dataComments = new ArrayList<>();

    private String projectName;

    private String hash;

    private String hashParent;

    private String hashPackage;

    private String hashPackageClass;

    private int qntCommentLine;

    private int qntCommentBlock;

    private int qntCommentDoc;

    private int qntSegmentos;

    private int parentQntCommentLine;

    private int parentQntCommentBlock;

    private int parentQntCommentDoc;

    private int parentQntSegmentos;

    public DataComment(CommitReporter commit, String hashPackage, String hashPackageClass, String hashParent) {

        this.projectName = commit.getProjectName();
        this.hashParent = hashParent;
        this.hash = commit.getHash();
        this.hashPackage = hashPackage;
        this.hashPackageClass = hashPackageClass;
        this.qntCommentLine = 0;
        this.qntCommentBlock = 0;
        this.qntCommentDoc = 0;
        this.qntSegmentos = 0;

        if (commit.getParentHashes().size() > 1) {
            System.out.println("Aqui");
        }
        dataComments.add(this);
    }

    public DataComment() {
    }

    public static void updateDadosByhashClassPath(CommentReporter comment) {

        dataComments.stream()
                .filter(c -> c.getHashPackageClass().equals(comment.getHashPackageClass()))
                .forEach(c -> {

                    c.qntCommentLine += comment.getType() == 1 ? 1 : 0;
                    c.qntCommentBlock += comment.getType() == 2 ? 1 : 0;
                    c.qntCommentDoc += comment.getType() == 3 ? 1 : 0;
                    c.qntSegmentos += comment.getSegmentos();

                });

    }

    public static void updateParentComment(CommentReporter comment) {

        dataComments.stream()
                .filter(c -> c.getHashPackageClass().equals(comment.getHashPackageClass())
                        && c.getHashParent().equals(comment.getHashParent()))
                .forEach(c -> {

                    c.parentQntCommentLine += comment.getType() == 1 ? 1 : 0;
                    c.parentQntCommentBlock += comment.getType() == 2 ? 1 : 0;
                    c.parentQntCommentDoc += comment.getType() == 3 ? 1 : 0;
                    c.parentQntSegmentos += comment.getSegmentos();

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

    /**
     * @return int return the parentQntCommentLine
     */
    public int getParentQntCommentLine() {
        return parentQntCommentLine;
    }

    /**
     * @param parentQntCommentLine the parentQntCommentLine to set
     */
    public void setParentQntCommentLine(int parentQntCommentLine) {
        this.parentQntCommentLine = parentQntCommentLine;
    }

    /**
     * @return int return the parentQntCommentBlock
     */
    public int getParentQntCommentBlock() {
        return parentQntCommentBlock;
    }

    /**
     * @param parentQntCommentBlock the parentQntCommentBlock to set
     */
    public void setParentQntCommentBlock(int parentQntCommentBlock) {
        this.parentQntCommentBlock = parentQntCommentBlock;
    }

    /**
     * @return int return the parentQntCommentDoc
     */
    public int getParentQntCommentDoc() {
        return parentQntCommentDoc;
    }

    /**
     * @param parentQntCommentDoc the parentQntCommentDoc to set
     */
    public void setParentQntCommentDoc(int parentQntCommentDoc) {
        this.parentQntCommentDoc = parentQntCommentDoc;
    }

    /**
     * @return int return the parentQntSegmentos
     */
    public int getParentQntSegmentos() {
        return parentQntSegmentos;
    }

    /**
     * @param parentQntSegmentos the parentQntSegmentos to set
     */
    public void setParentQntSegmentos(int parentQntSegmentos) {
        this.parentQntSegmentos = parentQntSegmentos;
    }

    /**
     * @return String return the hashParent
     */
    public String getHashParent() {
        return hashParent;
    }

    /**
     * @param hashParent the hashParent to set
     */
    public void setHashParent(String hashParent) {
        this.hashParent = hashParent;
    }

}