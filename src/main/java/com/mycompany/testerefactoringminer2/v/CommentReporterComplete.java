package com.mycompany.testerefactoringminer2.v;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.opencsv.bean.CsvBindByName;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class CommentReporterComplete {

    static String atualHash = "null";
    static String parentHash = "null";
    static List<CommentReportEntry> todosOsComentarios = new ArrayList<>();

    static int qntComentarios = 0;
    static int qntSegmentos = 0;

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

            this.hash = hash;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.segmentos = 1 + this.endLine - this.startLine;

            todosOsComentarios.add(this);

            CommentReporterComplete.qntComentarios++;
            CommentReporterComplete.qntSegmentos += this.segmentos;
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
            e.printStackTrace();
        }
    }

}
