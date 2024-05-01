package com.mycompany.testerefactoringminer2.v;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class CommentReporterComplete {

    static String hash = "null";

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

        private String type;
        private int startLine;
        private int endLine;

        private int tamanho;

        static String hashpublic;
        private String hash;

        /**
         * Cria uma entrada de relatório de comentário.
         *
         * @param hash        O hash associado à entrada.
         * @param type        O tipo de entrada.
         * @param text        O texto associado à entrada.
         * @param startNumber O número da linha de início.
         * @param endNumber   O número da linha de término.
         */
        CommentReportEntry(String hash, String type, String text, int startNumber, int endNumber) {
            this.hash = hash;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.tamanho = 1 + this.endLine - this.startLine;

            System.out.println(this);

        }

        // Teste comentario linha
        @Override
        public String toString() {
            return this.hash + "," + this.type + "," + this.startLine + "," + this.endLine + "," + this.tamanho + ";";
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
                            p.getContent(),
                            p.getRange().map(r -> r.begin.line).orElse(-1),
                            p.getRange().map(r -> r.end.line).orElse(-1)))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
