package com.minerprojects;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.minerprojects.cli.CLIExecute;
import com.minerprojects.cli.CLIExecution;
import com.minerprojects.data.DataComment;

public class CommentReporter {

    private static final Logger logger = Logger.getLogger(CommentReporter.class.getName());

    private String hash;

    private String hashPackage;

    private String hashParent;

    private String hashPackageClass;

    private int type;

    private int segmentos;

    private CommitReporter commit;

    public CommentReporter(CommitReporter commit, int type, int startNumber,
            int endNumber, String hpackage, String hclass, boolean isParent, String hashParent) {

        this.hash = commit.getHash();

        this.hashPackage = commit.getHash() + "." + hpackage;

        this.hashPackageClass = commit.getHash() + "." + hpackage + "." + hclass;

        this.type = type;

        this.segmentos = 1 + endNumber - startNumber;

        this.hashParent = hashParent;

        if (!isParent) {

            if (DataComment.dataComments.stream()
                    .noneMatch(c -> c.getHashPackageClass().equals(this.hashPackageClass))) {

                commit.getParentHashes()
                        .forEach(p -> new DataComment(commit, this.hashPackage, this.hashPackageClass, p));

            } else {

                DataComment.updateDadosByhashClassPath(this);
            }

        } else {

            DataComment.updateParentComment(this);

        }

    }

    public static void walkToRepositorySeachComment(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout -f " + commit.getHash();

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

        if (!execute.getError().isEmpty()) {

            logger.info(String.format("ERROR%s%s", command, execute.toString()));

            new CommitError(projectName,
                    commit.getHash(),
                    command + "\n" + execute.toString());

            return;

        }

        getPathJavaFilesADM(commit).forEach(p -> processJavaFile(p, commit, false, null));

    }

    public static void walkToRepositoryParentSeachComment(CommitReporter commit, String projectName, String hashParent)
            throws Exception {

        String command = "git checkout -f " + hashParent;

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

        if (!execute.getError().isEmpty()) {

            logger.info(String.format("ERROR%s%s", command, execute.toString()));

            new CommitError(projectName,
                    commit.getHash(),
                    command + "\n" + execute.toString());

            return;

        }

        getPathJavaFilesADM(commit).forEach(p -> processJavaFile(p, commit, true, hashParent));

    }

    public static Set<Path> getPathJavaFilesADM(CommitReporter commit) {

        Set<Path> javaFiles = new HashSet<>();

        commit.getFilesMAD().forEach((k, v) -> {

            javaFiles.add(FileSystems.getDefault()
                    .getPath("tmp" + File.separator + commit.getProjectName() + File.separator + k));

        });

        return javaFiles;

    }

    public static void processJavaFile(Path filePath, CommitReporter commit, boolean isParent, String hashParent) {

        try {

            CompilationUnit staticJavaParser = StaticJavaParser.parse(filePath);

            String packageName = staticJavaParser.getPackageDeclaration()
                    .map(PackageDeclaration::getNameAsString)
                    .orElse("default");

            String className = staticJavaParser.findFirst(ClassOrInterfaceDeclaration.class)
                    .map(ClassOrInterfaceDeclaration::getNameAsString)
                    .orElse("Unknown");

            staticJavaParser.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReporter(
                            commit,
                            p.isBlockComment() ? 2 : p.isLineComment() ? 1 : p.isJavadocComment() ? 3 : 0,
                            p.getRange().map(r -> r.begin.line).orElse(0),
                            p.getRange().map(r -> r.end.line).orElse(0),
                            packageName, className, isParent, hashParent

                    ))
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllComments(String projectName) {

        RestTemplate restTemplate = new RestTemplate();

        for (CommitReporter commit : CommitReporter.commits) {

            try {

                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info(
                            String.format("check comments - tmp%s%s%s%s", commit.getHash(), File.separator, projectName,
                                    File.separator));
                }

                DataComment.dataComments.clear();

                CommentReporter.walkToRepositorySeachComment(commit, projectName);

                List<DataComment> cmm = DataComment.dataComments;

                for (String parentHash : commit.getParentHashes()) {

                    CommentReporter.walkToRepositoryParentSeachComment(commit, projectName, parentHash);

                }

                DataComment.dataComments.forEach(data -> {
                    try {

                        restTemplate.postForObject("http://localhost:8080/api/comments", data, DataComment.class);

                    } catch (Exception e) {

                        logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
                        new CommitError(projectName,
                                commit.getHash(),
                                "Erro ao enviar dados para a api. " + DataComment.class.getName() + "."
                                        + data.getHashPackageClass());
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @param hash the hash to set
     */
    public String getHash() {
        return this.hash;
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
     * @return int return the type
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