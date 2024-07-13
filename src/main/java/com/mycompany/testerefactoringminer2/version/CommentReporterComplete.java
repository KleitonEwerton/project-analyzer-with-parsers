package com.mycompany.testerefactoringminer2.version;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.io.Writer;

public class CommentReporterComplete {

    static Commit atualCommit = null;
    static int contador = 0;
    static int contador2 = 0;

    static List<CommentsTodosDoCommit> todosOsComentarios = new ArrayList<>();

    public static HashMap<String, Integer> qntSegmentosApenasArquivosExistenteNoPai = new HashMap<>();

    public static void walkToRepositorySeachComment(Commit commit)
            throws Exception {

        for (Path path : commit.getFilesPath()) {

            processJavaFile(path);

        }

    }

    public static Set<Path> collectJavaFiles(String projectPath, Commit commit) throws IOException {

        Set<Path> javaFiles = new HashSet<>();

        try (Stream<Path> paths = Files.walk(FileSystems.getDefault().getPath(projectPath))) {
            javaFiles = paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return javaFiles;
    }

    public static void saveCommentsCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<CommentReporterComplete.CommentsTodosDoCommit> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(CommentReporterComplete.todosOsComentarios);
        writer.flush();
        writer.close();
        CommentReporterComplete.todosOsComentarios.clear();

    }

    public static class CommentsTodosDoCommit {

        @CsvBindByName(column = "11_hash")
        private String hash;

        @CsvBindByName(column = "12_parentsHash")
        private String parentsHash;

        @CsvBindByName(column = "13_hash_classPath")
        private String hash_classPath;

        @CsvBindByName(column = "14_parentHash_classPath")
        private String parentHash_classPath;

        @CsvBindByName(column = "15_type")
        private String type;

        @CsvBindByName(column = "16_startLine")
        private int startLine;

        @CsvBindByName(column = "17_endLine")
        private int endLine;

        @CsvBindByName(column = "18_segmentos")
        private int segmentos;

        @CsvBindByName(column = "19_classPath")
        private String classPath;

        @CsvBindByName(column = "20_isBlockComment")
        private int isBlockComment;

        @CsvBindByName(column = "21_isLineComment")
        private int isLineComment;

        @CsvBindByName(column = "22_isJavaDocComment")
        private int isJavaDocComment;

        @CsvBindByName(column = "23_isOrphanCommenta")
        private int isOrphanCommenta;

        private Path filePath;

        private Commit commit;

        public CommentsTodosDoCommit(Commit commit, Path filePath, String type, int startNumber,
                int endNumber, int isBlockComment, int isLineComment, int isJavaDocComment, int isOrphanComment,
                String classPath) {

            System.out.println(commit.getHash() + " " + filePath);

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
            this.hash_classPath = this.hash + "/" + this.classPath;
            this.parentHash_classPath = this.commit.getParentHash() + "/" + this.classPath;

            todosOsComentarios.add(this);

        }

        public String getType() {
            return type;
        }

        public void setCommit(Commit commit) {
            this.commit = commit;
        }

        public Commit getCommit() {
            return commit;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getStartLine() {
            return startLine;
        }

        public void setStartLine(int startLine) {
            this.startLine = startLine;
        }

        public int getEndLine() {
            return endLine;
        }

        public void setEndLine(int endLine) {
            this.endLine = endLine;
        }

        public int getsegmentos() {
            return segmentos;
        }

        public void setsegmentos(int segmentos) {
            this.segmentos = segmentos;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getParentsHash() {
            return this.parentsHash;
        }

        public void setParentsHash(String parentsHash) {
            this.parentsHash = parentsHash;

        }

        public Path getFilePath() {
            return filePath;
        }

        public void setFilePath(Path filePath) {
            this.filePath = filePath;
        }

        public String getClassPath() {
            return classPath;
        }

        public void setClassPath(String classPath) {
            this.classPath = classPath;
        }

        public int getIsBlockComment() {
            return isBlockComment;
        }

        public void setIsBlockComment(int isBlockComment) {
            this.isBlockComment = isBlockComment;
        }

        public int getIsLineComment() {
            return isLineComment;
        }

        public void setIsLineComment(int isLineComment) {
            this.isLineComment = isLineComment;
        }

        public int getIsJavaDocComment() {
            return isJavaDocComment;
        }

        public void setIsJavaDocComment(int isJavaDocComment) {
            this.isJavaDocComment = isJavaDocComment;
        }

        public String getHash_classPath() {
            return hash_classPath;
        }

        public void setHash_classPath(String hash_classPath) {
            this.hash_classPath = hash_classPath;
        }

        public int getIsOrphanCommenta() {
            return isOrphanCommenta;
        }

        public void setIsOrphanCommenta(int isOrphanCommenta) {
            this.isOrphanCommenta = isOrphanCommenta;
        }

        public String getParentHash_classPath() {
            return parentHash_classPath;
        }

        public void setParentHash_classPath(String parentHash_classPath) {
            this.parentHash_classPath = parentHash_classPath;
        }

        @Override
        public String toString() {
            return "CommentsTodosDoCommit{" +

                    ", hash_classPath='" + hash_classPath + '\'' +
                    ", parentHash_classPath='" + parentHash_classPath + '\'' +

                    '}';
        }

    }

    public static void processJavaFile(Path filePath) {

        try {

            CompilationUnit staticJavaParser = StaticJavaParser
                    .parse(filePath);

            staticJavaParser.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentsTodosDoCommit(
                            CommentReporterComplete.atualCommit,
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

}