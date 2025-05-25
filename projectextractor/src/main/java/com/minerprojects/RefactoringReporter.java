package com.minerprojects;

import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import com.minerprojects.data.DataRefactoring;

public class RefactoringReporter {

    private String projectName;

    private String hash;

    private String hashPackage;

    private String hashPackageClass;

    private String type;

    public RefactoringReporter(
            CommitReporter commit, String hPackage, String hashPackageClass, String type) {

        this.projectName = commit.getProjectName();
        this.hash = commit.getHash();
        this.hashPackage = this.hash + "." + hPackage;
        this.hashPackageClass = this.hash + "." + hashPackageClass;
        this.type = type;

        new DataRefactoring(this);

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

            // Itera pelos elementos "antes"
            ref.getInvolvedClassesBeforeRefactoring().forEach(c -> {

                new RefactoringReporter(
                        commit,
                        extractPackageFromClass(c.getValue()),
                        c.getValue(),
                        ref.getRefactoringType().toString());
            });

        }
    }

    public static String extractPackageFromClass(String fullyQualifiedName) {
        if (fullyQualifiedName != null && fullyQualifiedName.contains(".")) {
            int lastDotIndex = fullyQualifiedName.lastIndexOf(".");
            return fullyQualifiedName.substring(0, lastDotIndex); // Retorna o nome do pacote
        }
        return ""; // Caso não seja possível identificar
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

    public String getProjectName() {
        return this.projectName;
    }

    public String getHash() {
        return this.hash;
    }

}
