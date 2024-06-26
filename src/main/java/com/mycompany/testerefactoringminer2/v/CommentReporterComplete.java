package com.mycompany.testerefactoringminer2.v;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.testerefactoringminer2.Commit;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.io.Writer;

public class CommentReporterComplete {

    static Commit atualCommit = null;

    static List<CommentsTodosDoCommit> todosOsComentarios = new ArrayList<>();

    public static HashMap<String, Integer> qntComentarios = new HashMap<>();
    public static HashMap<String, Integer> qntSegmentos = new HashMap<>();
    public static HashMap<String, Integer> qntComentariosApenasArquivosExistenteNoPai = new HashMap<>();
    public static HashMap<String, Integer> qntSegmentosApenasArquivosExistenteNoPai = new HashMap<>();

    public static void walkToRepositorySeachComment(List<Path> filesPath, Commit commit)
            throws Exception {

        for (Path path : filesPath) {

            processJavaFile(path);

        }

    }

    public static List<Path> collectJavaFiles(String projectPath, Commit commit) throws IOException {

        List<Path> javaFiles = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(FileSystems.getDefault().getPath(projectPath))) {
            javaFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());

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

        private List<String> parentsHash;

        @CsvBindByName(column = "13_type")
        private String type;

        @CsvBindByName(column = "14_startLine")
        private int startLine;

        @CsvBindByName(column = "15_endLine")
        private int endLine;

        @CsvBindByName(column = "16_segmentos")
        private int segmentos;

        private Path filePath;

        private Commit commit;

        @CsvBindByName(column = "17_classPath")
        private String classPath;

        @CsvBindByName(column = "18_isBlockComment")
        private int isBlockComment;

        @CsvBindByName(column = "19_isLineComment")
        private int isLineComment;

        @CsvBindByName(column = "20_isJavaDocComment")
        private int isJavaDocComment;

        @CsvBindByName(column = "21_hash_classPath")
        private String hash_classPath;

        public CommentsTodosDoCommit(Commit commit, Path filePath, String type, int startNumber,
                int endNumber, int isBlockComment, int isLineComment, int isJavaDocComment, String classPath) {

            // ! Se precisar de pegar os dados sem levar em conta as documentações geradas
            // if (type.equals("JavadocComment")) {
            // return;
            // }

            // Apenas para os arquivos que existem no pai
            new CommentsApenasArquivosExistenteNoPai(commit, filePath, type, startNumber, endNumber);

            this.commit = commit;
            this.hash = commit.getHash();
            this.filePath = filePath;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.segmentos = 1 + this.endLine - this.startLine;
            this.isBlockComment = isBlockComment;
            this.isLineComment = isLineComment;
            this.isJavaDocComment = isJavaDocComment;

            classPath = classPath.replace("\\", "/");
            classPath = classPath.substring(classPath.indexOf("/") + 1);
            this.classPath = classPath.substring(classPath.indexOf("/") + 1);

            this.hash_classPath = this.hash + "/" + this.classPath;

            todosOsComentarios.add(this);

            if (CommentReporterComplete.qntComentarios.containsKey(this.hash)) {
                CommentReporterComplete.qntComentarios.put(this.hash,
                        CommentReporterComplete.qntComentarios.get(this.hash) + 1);
            } else {
                CommentReporterComplete.qntComentarios.put(this.hash, 1);
            }

            if (CommentReporterComplete.qntSegmentos.containsKey(this.hash)) {
                CommentReporterComplete.qntSegmentos.put(this.hash,
                        CommentReporterComplete.qntSegmentos.get(this.hash) + this.segmentos);
            } else {
                CommentReporterComplete.qntSegmentos.put(this.hash, this.segmentos);
            }

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

        public List<String> getParentsHash() {
            return this.parentsHash;
        }

        public void setParentsHash(List<String> parentsHash) {
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

    }

    // ! Não analise os arquivo adicionado nessa versão, pois eles não existem no
    // pai
    public static class CommentsApenasArquivosExistenteNoPai {
        private String hash;
        private List<String> parentsHash;
        private String type;
        private int startLine;
        private int endLine;
        private int segmentos;
        private Path filePath;
        private Commit commit;

        public CommentsApenasArquivosExistenteNoPai(Commit commit, Path filePath, String type, int startNumber,
                int endNumber) {

            if (!commit.getParent().getFilesPath().contains(filePath)) {
                return;
            }

            this.commit = commit;
            this.hash = commit.getHash();
            this.filePath = filePath;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.segmentos = 1 + this.endLine - this.startLine;

            if (CommentReporterComplete.qntComentariosApenasArquivosExistenteNoPai.containsKey(this.hash)) {
                CommentReporterComplete.qntComentariosApenasArquivosExistenteNoPai.put(this.hash,
                        CommentReporterComplete.qntComentariosApenasArquivosExistenteNoPai.get(this.hash) + 1);
            } else {
                CommentReporterComplete.qntComentariosApenasArquivosExistenteNoPai.put(this.hash, 1);
            }

            if (CommentReporterComplete.qntSegmentosApenasArquivosExistenteNoPai.containsKey(this.hash)) {
                CommentReporterComplete.qntSegmentosApenasArquivosExistenteNoPai.put(this.hash,
                        CommentReporterComplete.qntSegmentosApenasArquivosExistenteNoPai.get(this.hash)
                                + this.segmentos);
            } else {
                CommentReporterComplete.qntSegmentosApenasArquivosExistenteNoPai.put(this.hash, this.segmentos);
            }

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

        public List<String> getParentsHash() {
            return this.parentsHash;
        }

        public void setParentsHash(List<String> parentsHash) {
            this.parentsHash = parentsHash;

        }

        public Path getFilePath() {
            return filePath;
        }

        public void setFilePath(Path filePath) {
            this.filePath = filePath;
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
                            filePath.toString()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // ! AQUI PODE HAVER ERRO SE O ARQVUIO TIVER O CODIGO QUEBRADO OU VERSAO ANTIGA
        }
    }

}
