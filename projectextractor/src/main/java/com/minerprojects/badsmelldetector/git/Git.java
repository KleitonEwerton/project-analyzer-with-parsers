/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.git;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Git {
    public static List<String> gitCommitHash(Path directory) throws IOException, InterruptedException {
        // Get commits from oldest to most recent sorted by date
        List<String> commitHashList = runCommandHash(directory, "git", "log", "--reverse", "--pretty=%H",
                "--date-order", "--all");
        return commitHashList;
    }

    public static void gitCheckout(Path directory, String commitHash) throws IOException, InterruptedException {
        runCommandGit(directory, "git", "checkout", commitHash);
    }

    public static Date getDateByCommit(Path directory, String hash) throws IOException, InterruptedException {
        String dateCommit = runCommandCommitDate(directory, "git", "show", "-s", "--format=%ci", hash);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = formatter.parse(dateCommit);
            return d;
        } catch (ParseException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void runCommandGit(Path directory, String... command) throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
        }
        ProcessBuilder pb = new ProcessBuilder()
                .command(command)
                .directory(directory.toFile());
        Process p = pb.start();
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
        outputGobbler.start();
        errorGobbler.start();

        errorGobbler.join();
        outputGobbler.join();
    }

    public static List<String> runCommandHash(Path directory, String... command)
            throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
        }
        ProcessBuilder pb = new ProcessBuilder()
                .command(command)
                .directory(directory.toFile());
        Process p = pb.start();
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
        outputGobbler.start();
        errorGobbler.start();

        errorGobbler.join();
        outputGobbler.join();

        return outputGobbler.runCommitHash();
    }

    public static String runCommandCommitDate(Path directory, String... command)
            throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
        }
        ProcessBuilder pb = new ProcessBuilder()
                .command(command)
                .directory(directory.toFile());
        Process p = pb.start();
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
        outputGobbler.start();
        errorGobbler.start();
        int exit = p.waitFor();
        errorGobbler.join();
        outputGobbler.join();

        if (exit != 0) {
            throw new AssertionError(String.format("runCommand returned %d", exit));
        }
        return outputGobbler.runCommandCommitDate();
    }

    private static class StreamGobbler extends Thread {

        private final InputStream is;
        private final String type;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public List<String> runCommitHash() {
            List<String> result = new ArrayList();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
                String line;
                if (type.equals("OUTPUT")) {
                    while ((line = br.readLine()) != null) {
                        result.add(line);
                    }
                }
                return result;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return null;
        }

        public String runCommandCommitDate() {
            String result = null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
                String line;
                if (type.equals("OUTPUT")) {
                    while ((line = br.readLine()) != null) {
                        result = line;
                    }
                }
                return result;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return null;
        }
    }
}
