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

    @Column(nullable = true)
    private String packager;

    @Column(nullable = true)
    private String packageClass;

    @Column(nullable = false)
    private int qntCommentLine;

    @Column(nullable = false)
    private int qntCommentBlock;

    @Column(nullable = false)
    private int qntCommentDoc;

    @Column(nullable = false)
    private int qntSegmentos;

    @Column(nullable = true)
    private int parentQntCommentLine;

    @Column(nullable = true)
    private int parentQntCommentBlock;

    @Column(nullable = true)
    private int parentQntCommentDoc;
    @Column(nullable = true)

    private int parentQntSegmentos;
    @Column(nullable = true)
    private String hashParent;

    /*
     * 
     * UPDATE public.comment
     * SET
     * package_class = REGEXP_REPLACE(hash_package_class, '^.*?\.(.*)$', '\1'),
     * packager = REGEXP_REPLACE(hash_package, '^.*?\.(.*)$', '\1')
     * WHERE
     * package_class NOT LIKE '%.%' -- Atualiza apenas se o formato estiver errado
     * OR packager NOT LIKE '%.%'
     * OR package_class is null
     * or packager is null
     * ; -- Atualiza apenas se o formato estiver errado
     */

    /*
     * 
     * CREATE INDEX idx_comment_hash ON public.comment (hash);
     * CREATE INDEX idx_commit_hash ON public.commit (hash);
     * CREATE INDEX idx_commit_parents_hash ON public.commit USING gin
     * (parents_hash);
     * CREATE INDEX idx_comment_package_class ON public.comment (package_class);
     * CREATE INDEX idx_comment_project_name ON public.comment (project_name);
     * CREATE INDEX idx_comment_package_class_project_name ON public.comment
     * (package_class, project_name);
     * 
     */

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

    /**
     * @return String return the packager
     */
    public String getPackager() {
        return packager;
    }

    /**
     * @param packager the packager to set
     */
    public void setPackager(String packager) {
        this.packager = packager;
    }

    /**
     * @return String return the packageClass
     */
    public String getPackageClass() {
        return packageClass;
    }

    /**
     * @param packageClass the packageClass to set
     */
    public void setPackageClass(String packageClass) {
        this.packageClass = packageClass;
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
