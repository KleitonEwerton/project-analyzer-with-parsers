package com.minerprojects.Data;

import java.util.HashSet;
import java.util.Set;

import com.minerprojects.CommentReporter;

public class DataComment {

    public static Set<DataComment> dataComments = new HashSet();

    private String hashClassPath;
    private String parentHashClassPath;

    private int qntCommentReporterType1;
    private int qntInParentCommentReporterType1;

    private int qntCommentReporterType2;
    private int qntInParentCommentReporterType2;

    private int qntCommentReporterType3;
    private int qntInParentCommentReporterType3;

    private int qntSegmentos;
    private int qntParentSegmentos;

    public DataComment(String hashClassPath, String parentHashClassPath) {

        this.hashClassPath = hashClassPath;
        this.parentHashClassPath = parentHashClassPath;

        this.qntCommentReporterType1 = 0;
        this.qntInParentCommentReporterType1 = 0;

        this.qntCommentReporterType2 = 0;
        this.qntInParentCommentReporterType2 = 0;

        this.qntCommentReporterType3 = 0;
        this.qntInParentCommentReporterType3 = 0;

        this.qntSegmentos = 0;
        this.qntParentSegmentos = 0;
    }

    public static void updateDadosByhashClassPath(CommentReporter comment) {
        System.out.println("DataComment.updateDadosByhashClassPath");
        System.out.println("comment: " + comment.getHashClassPath());

        dataComments.stream().filter(c -> c.getHashClassPath().equals(comment.getHashClassPath())).forEach(c -> {

            c.qntCommentReporterType1 += comment.getType() == 1 ? 1 : 0;
            c.qntCommentReporterType2 += comment.getType() == 2 ? 1 : 0;
            c.qntCommentReporterType3 += comment.getType() == 3 ? 1 : 0;
            c.qntSegmentos += comment.getSegmentos();

        });

    }

    public static void updateDadosByhashParentClassPath(CommentReporter comment) {

        dataComments.stream().filter(c -> c.getParentHashClassPath().equals(comment.getHashClassPath())).forEach(c -> {

            c.qntInParentCommentReporterType1 += comment.getType() == 1 ? 1 : 0;
            c.qntInParentCommentReporterType2 += comment.getType() == 2 ? 1 : 0;
            c.qntInParentCommentReporterType3 += comment.getType() == 3 ? 1 : 0;
            c.qntParentSegmentos += comment.getSegmentos();

        });

    }

    @Override
    public String toString() {

        return "DataComment [hashClassPath=" + hashClassPath + ", parentHashClassPath=" + parentHashClassPath
                + ", qntCommentReporterType1=" + qntCommentReporterType1 + ", qntInParentCommentReporterType1="
                + qntInParentCommentReporterType1 + ", qntCommentReporterType2=" + qntCommentReporterType2
                + ", qntInParentCommentReporterType2=" + qntInParentCommentReporterType2
                + ", qntCommentReporterType3=" + qntCommentReporterType3 + ", qntInParentCommentReporterType3="
                + qntInParentCommentReporterType3 + ", qntSegmentos=" + qntSegmentos + ", qntParentSegmentos="
                + qntParentSegmentos + "]";
    }

    /**
     * @return String return the hashClassPath
     */
    public String getHashClassPath() {
        return hashClassPath;
    }

    /**
     * @param hashClassPath the hashClassPath to set
     */
    public void setHashClassPath(String hashClassPath) {
        this.hashClassPath = hashClassPath;
    }

    /**
     * @return String return the parentHashClassPath
     */
    public String getParentHashClassPath() {
        return parentHashClassPath;
    }

    /**
     * @param parentHashClassPath the parentHashClassPath to set
     */
    public void setParentHashClassPath(String parentHashClassPath) {
        this.parentHashClassPath = parentHashClassPath;
    }

    /**
     * @return int return the qntCommentReporterType1
     */
    public int getQntCommentReporterType1() {
        return qntCommentReporterType1;
    }

    /**
     * @param qntCommentReporterType1 the qntCommentReporterType1 to set
     */
    public void setQntCommentReporterType1(int qntCommentReporterType1) {
        this.qntCommentReporterType1 = qntCommentReporterType1;
    }

    /**
     * @return int return the qntCommentReporterType2
     */
    public int getQntCommentReporterType2() {
        return qntCommentReporterType2;
    }

    /**
     * @param qntCommentReporterType2 the qntCommentReporterType2 to set
     */
    public void setQntCommentReporterType2(int qntCommentReporterType2) {
        this.qntCommentReporterType2 = qntCommentReporterType2;
    }

    /**
     * @return int return the qntCommentReporterType3
     */
    public int getQntCommentReporterType3() {
        return qntCommentReporterType3;
    }

    /**
     * @param qntCommentReporterType3 the qntCommentReporterType3 to set
     */
    public void setQntCommentReporterType3(int qntCommentReporterType3) {
        this.qntCommentReporterType3 = qntCommentReporterType3;
    }

    /**
     * @return int return the qntInParentCommentReporterType1
     */
    public int getQntInParentCommentReporterType1() {
        return qntInParentCommentReporterType1;
    }

    /**
     * @param qntInParentCommentReporterType1 the qntInParentCommentReporterType1 to
     *                                        set
     */
    public void setQntInParentCommentReporterType1(int qntInParentCommentReporterType1) {
        this.qntInParentCommentReporterType1 = qntInParentCommentReporterType1;
    }

    /**
     * @return int return the qntInParentCommentReporterType2
     */
    public int getQntInParentCommentReporterType2() {
        return qntInParentCommentReporterType2;
    }

    /**
     * @param qntInParentCommentReporterType2 the qntInParentCommentReporterType2 to
     *                                        set
     */
    public void setQntInParentCommentReporterType2(int qntInParentCommentReporterType2) {
        this.qntInParentCommentReporterType2 = qntInParentCommentReporterType2;
    }

    /**
     * @return int return the qntInParentCommentReporterType3
     */
    public int getQntInParentCommentReporterType3() {
        return qntInParentCommentReporterType3;
    }

    /**
     * @param qntInParentCommentReporterType3 the qntInParentCommentReporterType3 to
     *                                        set
     */
    public void setQntInParentCommentReporterType3(int qntInParentCommentReporterType3) {
        this.qntInParentCommentReporterType3 = qntInParentCommentReporterType3;
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
     * @return int return the qntParentSegmentos
     */
    public int getQntParentSegmentos() {
        return qntParentSegmentos;
    }

    /**
     * @param qntParentSegmentos the qntParentSegmentos to set
     */
    public void setQntParentSegmentos(int qntParentSegmentos) {
        this.qntParentSegmentos = qntParentSegmentos;
    }

}
