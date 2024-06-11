package com.mycompany.testerefactoringminer2.v;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
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

import org.checkerframework.checker.units.qual.C;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class CommentReporterComplete {

    static String atualHash = "null";
    static String parentHash = "null";

    static List<CommentReportEntry> todosOsComentarios = new ArrayList<>();

    static Set<String> todosHashErro = new HashSet<>();

    public static HashMap<String, Integer> mapHashQntComentarios = new HashMap<>();
    public static HashMap<String, Integer> mapHashQntSegmentos = new HashMap<>();

    public static void walkToRepositorySeachComment(String projectPath, String hash, String parenHash)
            throws Exception {

        atualHash = hash;
        parentHash = parenHash;
        String command = "git checkout " + atualHash;

        CLIExecute.execute(command, projectPath);

        Files.walk(FileSystems.getDefault().getPath(projectPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(CommentReporterComplete::processJavaFile);

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

        public CommentReportEntry(String hash, String parentsHash, String type, int startNumber, int endNumber) {

            // ! Se precisar de pegar os dados sem levar em conta as documentações geradas
            // !pelo javadoc
            if (type.equals("JavadocComment")) {
                return;
            }

            this.hash = hash;
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
                            CommentReporterComplete.atualHash,
                            CommentReporterComplete.parentHash,
                            p.getClass().getSimpleName(),
                            p.getRange().map(r -> r.begin.line).orElse(0),
                            p.getRange().map(r -> r.end.line).orElse(0)))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // ! AQUI PODE HAVER ERRO SE O ARQVUIO TIVER O CODIGO QUEBRADO OU VERSAO ANTIGA
            // DO JAVA

            System.out.println("\n\nERROR: " + filePath.toString() + " - " +
                    CommentReporterComplete.atualHash);
            e.printStackTrace();
            CommentReporterComplete.todosHashErro.add(CommentReporterComplete.atualHash);

        }
    }

}
