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

    static String hash = "null";
    static List<CommentReportEntry> todosOsComentarios = new ArrayList<>();

    static int qntComentarios = 0;
    static int qntSegmentos = 0;

    public static void walkToRepositorySeachComment(String projectPath, String parHash)
            throws Exception {

        hash = parHash;
        String command = "git checkout " + parHash;

        CLIExecute.execute(command, projectPath);

        Files.walk(FileSystems.getDefault().getPath(projectPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(CommentReporterComplete::processJavaFile);

    }

    public static class CommentReportEntry {

        @CsvBindByName(column = "1_hash")
        private String hash;

        @CsvBindByName(column = "2_type")
        private String type;

        @CsvBindByName(column = "3_startLine")
        private int startLine;

        @CsvBindByName(column = "4_endLine")
        private int endLine;

        @CsvBindByName(column = "5_segmentos")
        private int segmentos;

        /**
         * Cria uma entrada de relatório de comentário.
         *
         * @param hash        O hash associado à entrada.
         * @param type        O tipo de entrada.
         * @param startNumber O número da linha de início.
         * @param endNumber   O número da linha de término.
         */
        CommentReportEntry(String hash, String type, int startNumber, int endNumber) {

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

        @Override
        public String toString() {
            return "CommentReportEntry [endLine=" + endLine + ", hash=" + hash + ", startLine=" + startLine + ", type="
                    + type + "]";
        }
    }

    /*
     * Teste de comentario de bloco
     */
    public static void processJavaFile(Path filePath) {

        try {

            CompilationUnit cu = StaticJavaParser
                    .parse(filePath);

            List<CommentReportEntry> lsc = cu.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReportEntry(CommentReporterComplete.hash, p.getClass().getSimpleName(),
                            p.getRange().map(r -> r.begin.line).orElse(-1),
                            p.getRange().map(r -> r.end.line).orElse(-1)))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
