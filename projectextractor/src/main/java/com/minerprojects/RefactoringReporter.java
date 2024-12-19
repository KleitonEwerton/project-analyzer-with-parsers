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

    private String movedToPathClass;

    private String type;

    private String ocorrido;

    public RefactoringReporter(String projectName, String hash, String parentHash, String type, String ocorrido,
            String pathClass,
            String movedToPathClass) {

        this.projectName = projectName;
        this.parentHash = parentHash;
        this.pathClass = pathClass;
        this.hash = hash;
        this.parentHash = parentHash;
        this.type = type;
        this.ocorrido = ocorrido;
        this.movedToPathClass = movedToPathClass;
        this.pathClass = pathClass;

        this.hashPathClass = hash + java.io.File.separator + pathClass;

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
     * @return String return the ocorrido
     */
    public String getOcorrido() {
        return ocorrido;
    }

    /**
     * @param ocorrido the ocorrido to set
     */
    public void setOcorrido(String ocorrido) {
        this.ocorrido = ocorrido;
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
     * @return String return the movedToPathClass
     */
    public String getMovedToPathClass() {
        return movedToPathClass;
    }

    /**
     * @param movedToPathClass the movedToPathClass to set
     */
    public void setMovedToPathClass(String movedToPathClass) {
        this.movedToPathClass = movedToPathClass;
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

            String refactoringType = ref.getRefactoringType().toString();

            String referencia = ref.getName() + " "
                    + ref.toString().replace(",", " ").replace(";", " ");

            String oldPathClass = ref.getInvolvedClassesBeforeRefactoring().toString().split(",")[0];
            String newPathClass = ref.getInvolvedClassesAfterRefactoring().toString().split(",")[0];

            new RefactoringReporter(commit.getProjectName(), commit.getHash(), commit.getParentHash(),
                    refactoringType, referencia, oldPathClass.substring(2, oldPathClass.length()),
                    newPathClass.substring(2, newPathClass.length()));

        }
    }

}
