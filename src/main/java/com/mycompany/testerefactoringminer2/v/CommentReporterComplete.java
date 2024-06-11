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
import java.util.HashSet;
import java.util.Set;

public class CommentReporterComplete {

    static Commit atualCommit = null;

    static List<CommentReportEntry> todosOsComentarios = new ArrayList<>();

    static Set<String> todosHashErro = new HashSet<>();

    public static HashMap<String, Integer> mapHashQntComentarios = new HashMap<>();
    public static HashMap<String, Integer> mapHashQntSegmentos = new HashMap<>();

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
        StatefulBeanToCsv<CommentReporterComplete.CommentReportEntry> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(CommentReporterComplete.todosOsComentarios);
        writer.flush();
        writer.close();
        CommentReporterComplete.todosOsComentarios.clear();

    }

    public static class CommentReportEntry {

        @CsvBindByName(column = "1_hash")
        private String hash;

        @CsvBindByName(column = "2_parentsHash")
        private List<String> parentsHash;

        @CsvBindByName(column = "3_type")
        private String type;

        @CsvBindByName(column = "4_startLine")
        private int startLine;

        @CsvBindByName(column = "5_endLine")
        private int endLine;

        @CsvBindByName(column = "6_segmentos")
        private int segmentos;

        private Path filePath;

        private Commit commit;

        public CommentReportEntry(Commit commit, Path filePath, String type, int startNumber,
                int endNumber) {

            // ! Se precisar de pegar os dados sem levar em conta as documentações geradas
            if (type.equals("JavadocComment")) {
                return;
            }
            this.commit = commit;
            this.hash = commit.getHash();
            this.filePath = filePath;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.segmentos = 1 + this.endLine - this.startLine;

            todosOsComentarios.add(this);

            if (CommentReporterComplete.mapHashQntComentarios.containsKey(this.hash)) {
                CommentReporterComplete.mapHashQntComentarios.put(this.hash,
                        CommentReporterComplete.mapHashQntComentarios.get(this.hash) + 1);
            } else {
                CommentReporterComplete.mapHashQntComentarios.put(this.hash, 1);
            }

            if (CommentReporterComplete.mapHashQntSegmentos.containsKey(this.hash)) {
                CommentReporterComplete.mapHashQntSegmentos.put(this.hash,
                        CommentReporterComplete.mapHashQntSegmentos.get(this.hash) + this.segmentos);
            } else {
                CommentReporterComplete.mapHashQntSegmentos.put(this.hash, this.segmentos);
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

    /*
     * Teste de comentario de bloco
     */
    public static void processJavaFile(Path filePath) {

        try {

            CompilationUnit staticJavaParser = StaticJavaParser
                    .parse(filePath);

            staticJavaParser.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReportEntry(
                            CommentReporterComplete.atualCommit,
                            filePath,
                            p.getClass().getSimpleName(),
                            p.getRange().map(r -> r.begin.line).orElse(0),
                            p.getRange().map(r -> r.end.line).orElse(0)))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // ! AQUI PODE HAVER ERRO SE O ARQVUIO TIVER O CODIGO QUEBRADO OU VERSAO ANTIGA
        }
    }

}
