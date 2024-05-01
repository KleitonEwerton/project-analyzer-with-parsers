package com.mycompany.testerefactoringminer2.v;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecution;
import jdk.dynalink.beans.StaticClass;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class CommentReporterComplete {

    static List<CommentReportEntry> listCommentReportEntry = new ArrayList<>();
    static String hash = "null";

    static String lastData;
    public static List<CommentReportEntry> walkToRepositorySeachComment(String projectPath, String hash) throws Exception {

        String command = "git checkout " + hash;
        CLIExecute.execute(command, projectPath);

        Files.walk(FileSystems.getDefault().getPath(projectPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(CommentReporterComplete::processJavaFile);


        return listCommentReportEntry;

    }
    public static class CommentReportEntry {
        private String hash;
        private String type;
        private int startLine;
        private int endLine;
        private boolean isOrphan;
        private String data;

        /**
         * Cria uma entrada de relatório de comentário.
         *
         * @param hash O hash associado à entrada.
         * @param type O tipo de entrada.
         * @param text O texto associado à entrada.
         * @param startNumber O número da linha de início.
         * @param endNumber O número da linha de término.
         * @param isOrphan Indica se a entrada é órfã.
         * @param data Os dados associados à entrada.
         */
        CommentReportEntry(String hash, String type, String text, int startNumber, int endNumber, boolean isOrphan, String data) {
            this.hash = hash;
            this.type = type;
            this.startLine = startNumber;
            this.endLine = endNumber;
            this.isOrphan = isOrphan;
            this.data = data;

        }

        //Teste comentario linha
        @Override
        public String toString() {
            return startLine + " to " + endLine + " | " + type + " | " + isOrphan + " | " + this.hash + " | " + this.data + " | ";
        }
    }
    /*
    Teste de comentario de bloco
    * */
    public static void processJavaFile(Path filePath) {

        try{

            CompilationUnit cu = StaticJavaParser
                    .parse(filePath);


            List<CommentReportEntry> lsc = cu.getAllContainedComments()
                    .stream()
                    .map(p -> new CommentReportEntry(hash, p.getClass().getSimpleName() + p.getComment(),
                            p.getContent(),
                            p.getRange().map(r -> r.begin.line).orElse(-1),
                            p.getRange().map(r -> r.end.line).orElse(-1),
                            !p.getCommentedNode().isPresent(), lastData))
                    .collect(Collectors.toList());

            listCommentReportEntry = lsc;

        }catch(Exception e){
            e.printStackTrace();
        }
    }




}
