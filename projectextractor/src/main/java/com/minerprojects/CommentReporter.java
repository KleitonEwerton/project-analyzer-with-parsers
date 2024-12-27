package com.minerprojects;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashSet;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.CLI.CLIExecution;
import com.minerprojects.Data.DataComment;

public class CommentReporter {

    private static final Logger logger = Logger.getLogger(CommentReporter.class.getName());

    private String hashClassPath;

    private String parentHashClassPath;

    private int type;

    private int startLine;

    private int endLine;

    private int segmentos;

    private Path filePath;

    private CommitReporter commit;

    public CommentReporter(CommitReporter commit, Path filePath, int type, int startNumber,
            int endNumber, boolean isParent) {

        this.commit = commit;
        this.hashClassPath = commit.getHash() + File.separator + filePath.toString();
        this.parentHashClassPath = this.commit.getParentHash() + File.separator + filePath.toString();

        this.filePath = filePath;

        this.type = type;

        this.startLine = startNumber;

        this.endLine = endNumber;

        this.segmentos = 1 + this.endLine - this.startLine;

        if (isParent) {

            DataComment.updateDadosByhashParentClassPath(this);

        } else {
            DataComment.updateDadosByhashClassPath(this);

        }

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
     * @return String return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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
     * @return int return the segmentos
     */
    public int getSegmentos() {
        return segmentos;
    }

    /**
     * @param segmentos the segmentos to set
     */
    public void setSegmentos(int segmentos) {
        this.segmentos = segmentos;
    }

    /**
     * @return Path return the filePath
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * @return CommitReporter return the commit
     */
    public CommitReporter getCommit() {
        return commit;
    }

    /**
     * @param commit the commit to set
     */
    public void setCommit(CommitReporter commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "CommentReporter [commit=" + commit.getHash() + ", endLine=" + endLine + ", filePath=" + filePath
                + ", hashClassPath="
                + hashClassPath + ", parentHashClassPath=" + parentHashClassPath + ", segmentos=" + segmentos
                + ", startLine=" + startLine + ", type=" + type + "]";
    }

    public static void walkToRepositorySeachComment(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getHash();

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

        if (execute.toString().contains("error:")) {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {

                logger.info(String.format("ERROR%n%s", command));

            }
            new ErrorReporter(commit.getHash(), projectName, command + "\n" + execute.toString());
            return;
        }

        getPathJavaFilesADM(commit).forEach(p -> processJavaFile(p, commit, false));

    }

    public static void walkParentToRepositorySeachComment(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getParentHash();

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

        if (execute.toString().contains("error:")) {

            if (logger.isLoggable(java.util.logging.Level.INFO)) {

                logger.info(String.format("ERROR%n%s", command));

            }
            new ErrorReporter(commit.getHash(), projectName, command + "\n" + execute.toString());
            return;
        }

        getParentPathJavaFilesADM(commit).forEach(p -> processJavaFile(p, commit, true));

    }

    public static Set<Path> getPathJavaFilesADM(CommitReporter commit) {

        Set<Path> javaFiles = new HashSet<>();

        commit.getFilesMAD().forEach((k, v) -> {

            if (v.equals("M") || v.equals("A")) {
                javaFiles.add(FileSystems.getDefault()
                        .getPath("tmp" + File.separator + commit.getProjectName() + File.separator +
                                k));
                DataComment.dataComments.add(new DataComment(commit.getHash() + File.separator + k,
                        commit.getParentHash() + File.separator + k));
            }

        });

        return javaFiles;

    }

    public static Set<Path> getParentPathJavaFilesADM(CommitReporter commit) {

        Set<Path> javaFiles = new HashSet<>();

        commit.getFilesMAD().forEach((k, v) -> {

            if (v.equals("M") || v.equals("A")) {
                javaFiles.add(FileSystems.getDefault()
                        .getPath("tmp" + File.separator + commit.getProjectName() + File.separator +
                                k));
            }

        });

        return javaFiles;

    }

    public static void processJavaFile(Path filePath, CommitReporter commit, Boolean isParent) {

        try {

            CompilationUnit staticJavaParser = StaticJavaParser.parse(filePath);

            staticJavaParser.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReporter(
                            commit,
                            FileSystems.getDefault().getPath(filePath.toString()),
                            p.isBlockComment() ? 2 : p.isLineComment() ? 1 : p.isJavadocComment() ? 3 : 0,
                            p.getRange().map(r -> r.begin.line).orElse(0),
                            p.getRange().map(r -> r.end.line).orElse(0),
                            isParent

                    ))
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            // ! AQUI PODE HAVER ERRO SE O ARQVUIO TIVER O CODIGO QUEBRADO OU VERSAO ANTIGA
        }
    }

    public static void getAllComments(String projectName) {

        for (CommitReporter commit : CommitReporter.commits) {

            try {

                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info(
                            String.format("check comments - tmp%s%s%s", projectName, File.separator, commit.getHash()));
                }

                CommentReporter.walkToRepositorySeachComment(commit, projectName);
                CommentReporter.walkParentToRepositorySeachComment(commit, projectName);

                DataComment.dataComments.forEach(c -> {

                    System.out.println(c.toString());
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
