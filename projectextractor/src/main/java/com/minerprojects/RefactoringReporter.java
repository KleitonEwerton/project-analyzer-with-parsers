package com.minerprojects;

import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

public class RefactoringReporter {

    private String projectName;

    private String parentHash;

    private String pathClass;

    private String hash;

    private String hashPathClass;

    private String type;

    private String hashPackageClass;

    public RefactoringReporter(
            String projectName,
            String hash,
            String parentHash,
            String type,
            String hashPackageClass) {

        this.projectName = projectName;
        this.hash = hash;
        this.parentHash = parentHash;
        this.type = type;
        this.hashPackageClass = hashPackageClass;
    }

    public String getHashPackageClass() {
        return hashPackageClass;
    }

    public void setHashPackageClass(String hashPackageClass) {
        this.hashPackageClass = hashPackageClass;
    }

    @Override
    public String toString() {
        return "RefactoringReporter [hash=" + hash + ", type=" + type + ", parentHash="
                + parentHash + ", pathClass=" + pathClass + ", projectName=" + projectName + ", hashPathClass="
                + hashPathClass + "]";
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

    /**
     * @return String return the pathClass
     */
    public String getPathClass() {
        return pathClass;
    }

    /**
     * @param pathClass the pathClass to set
     */
    public void setPathClass(String pathClass) {
        this.pathClass = pathClass;
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
     * @return String return the hashPathClass
     */

    public String getHashPathClass() {
        return hashPathClass;

    }

    /**
     * @param hashPathClass the hashPathClass to set
     */

    public void setHashPathClass(String hashPathClass) {
        this.hashPathClass = hashPathClass;

    }

    public static void getAllRefactoring(GitHistoryRefactoringMiner miner,
            Repository repo) {

        for (CommitReporter commit : CommitReporter.commits) {

            try {
                miner.detectAtCommit(repo, commit.getHash(), new RefactoringHandler() {

                    @Override
                    public void handle(String commitHash, List<Refactoring> refactorings) {
                        salveRefactiongByRefactionList(refactorings, commit);
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static void salveRefactiongByRefactionList(List<Refactoring> refactorings, CommitReporter commit) {

        for (Refactoring ref : refactorings) {

            new RefactoringReporter(
                    commit.getProjectName(),
                    commit.getHash(),
                    commit.getParentHash(),
                    ref.getRefactoringType().toString(),
                    commit.getHash() + "." + ref.getClass().getPackageName() + "." + ref.getClass().getSimpleName());
        }
    }

}
