package com.minerprojects.data;

public class DataPMD {

    private long id;

    private String projectName;

    private String hash;

    private String hashPackageClass;

    private String type;

    private int beginLine;

    private int endLine;

    private int beginColumn;

    private String priority;

    private String parentHash;

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
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return int return the beginLine
     */
    public int getBeginLine() {
        return beginLine;
    }

    /**
     * @param beginLine the beginLine to set
     */
    public void setBeginLine(int beginLine) {
        this.beginLine = beginLine;
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
     * @return int return the beginColumn
     */
    public int getBeginColumn() {
        return beginColumn;
    }

    /**
     * @param beginColumn the beginColumn to set
     */
    public void setBeginColumn(int beginColumn) {
        this.beginColumn = beginColumn;
    }

    /**
     * @return String return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return String return the parentHash
     */
    public String getParentHash() {
        return parentHash;
    }

    /**
     * @param parentHash the parentHash to set
     */
    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

}
