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

public class CommentReporter {

    private static final Logger logger = Logger.getLogger(CommentReporter.class.getName());

    private String hash;
    private String hashClassPath;
    private String classPath;

    private String parentsHash;
    private String parentHashClassPath;

    private String type;
    private int startLine;
    private int endLine;
    private int segmentos;

    private int isBlockComment;
    private int isLineComment;
    private int isJavaDocComment;
    private int isOrphanCommenta;

    private Path filePath;
    private CommitReporter commit;

    public CommentReporter(CommitReporter commit, Path filePath, String type, int startNumber,
            int endNumber, int isBlockComment, int isLineComment, int isJavaDocComment, int isOrphanComment,
            String classPath) {

        this.commit = commit;
        this.hash = commit.getHash();
        this.parentsHash = commit.getParentHash();
        this.filePath = filePath;
        this.type = type;
        this.startLine = startNumber;
        this.endLine = endNumber;
        this.segmentos = 1 + this.endLine - this.startLine;
        this.isBlockComment = isBlockComment;
        this.isLineComment = isLineComment;
        this.isJavaDocComment = isJavaDocComment;
        this.isOrphanCommenta = isOrphanComment;

        classPath = classPath.replace("\\", "/");
        classPath = classPath.substring(classPath.indexOf("/") + 1);

        this.classPath = classPath.substring(classPath.indexOf("/") + 1);

        this.hashClassPath = this.hash + File.separator + this.classPath;
        this.parentHashClassPath = this.commit.getParentHash() + File.separator + this.classPath;

    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
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
     * @return String return the classPath
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * @param classPath the classPath to set
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * @return String return the parentsHash
     */
    public String getParentsHash() {
        return parentsHash;
    }

    /**
     * @param parentsHash the parentsHash to set
     */
    public void setParentsHash(String parentsHash) {
        this.parentsHash = parentsHash;
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
     * @return int return the isBlockComment
     */
    public int getIsBlockComment() {
        return isBlockComment;
    }

    /**
     * @param isBlockComment the isBlockComment to set
     */
    public void setIsBlockComment(int isBlockComment) {
        this.isBlockComment = isBlockComment;
    }

    /**
     * @return int return the isLineComment
     */
    public int getIsLineComment() {
        return isLineComment;
    }

    /**
     * @param isLineComment the isLineComment to set
     */
    public void setIsLineComment(int isLineComment) {
        this.isLineComment = isLineComment;
    }

    /**
     * @return int return the isJavaDocComment
     */
    public int getIsJavaDocComment() {
        return isJavaDocComment;
    }

    /**
     * @param isJavaDocComment the isJavaDocComment to set
     */
    public void setIsJavaDocComment(int isJavaDocComment) {
        this.isJavaDocComment = isJavaDocComment;
    }

    /**
     * @return int return the isOrphanCommenta
     */
    public int getIsOrphanCommenta() {
        return isOrphanCommenta;
    }

    /**
     * @param isOrphanCommenta the isOrphanCommenta to set
     */
    public void setIsOrphanCommenta(int isOrphanCommenta) {
        this.isOrphanCommenta = isOrphanCommenta;
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

    public static void walkToRepositorySeachComment(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getHash();

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp/" + projectName);

        if (execute.toString().contains("error:")) {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info(String.format("ERROR%n%s", command));
            }
            new ErrorReporter(commit.getHash(), projectName, command + "\n" + execute.toString());
            return;
        }

        collectJavaFiles("tmp/" + projectName).forEach(p -> processJavaFile(p, commit));

    }

    public static Set<Path> collectJavaFiles(String projectPath) throws IOException {

        Set<Path> javaFiles = new HashSet<>();

        try (Stream<Path> paths = Files.walk(FileSystems.getDefault().getPath(projectPath))) {
            javaFiles = paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> !isHidden(p))
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return javaFiles;
    }

    private static boolean isHidden(Path path) {
        try {
            return Files.isHidden(path) || path.getParent().toFile().isHidden();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void processJavaFile(Path filePath, CommitReporter commit) {

        try {

            CompilationUnit staticJavaParser = StaticJavaParser
                    .parse(filePath);

            staticJavaParser.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReporter(
                            commit,
                            filePath,
                            p.getClass().getSimpleName(),
                            p.getRange().map(r -> r.begin.line).orElse(0),
                            p.getRange().map(r -> r.end.line).orElse(0),
                            p.isBlockComment() ? 1 : 0,
                            p.isLineComment() ? 1 : 0,
                            p.isJavadocComment() ? 1 : 0,
                            p.isOrphan() ? 1 : 0,
                            filePath.toString()))
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            // ! AQUI PODE HAVER ERRO SE O ARQVUIO TIVER O CODIGO QUEBRADO OU VERSAO ANTIGA
        }
    }

    public static void getAllComments(String projectName) {

        for (CommitReporter commit : CommitReporter.commits) {

            try {

                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info(String.format("check comments - tmp/%s/%s", projectName, commit.getHash()));
                }

                CommentReporter.walkToRepositorySeachComment(commit, projectName);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
