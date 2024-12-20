
package com.minerprojects.badsmelldetector.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CMD {

    public static CMDOutput cmd(String path, String command) {

        CMDOutput result = new CMDOutput();

        try {

            Process exec;

            if (path != null) {
                exec = Runtime.getRuntime().exec(command, null, new File(path));

            } else {

                exec = Runtime.getRuntime().exec(command);
            }

            String s;

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(exec.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                result.addOutput(s);
            }

            while ((s = stdError.readLine()) != null) {
                result.addErrors(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(CMD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static CMDOutput cmdArray(String path, String[] command) {

        CMDOutput result = new CMDOutput();

        try {

            Process exec;

            if (path != null) {
                exec = Runtime.getRuntime().exec(command, null, new File(path));
            } else {
                exec = Runtime.getRuntime().exec(command);
            }

            String s;

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(exec.getErrorStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                result.addOutput(s);
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                result.addErrors(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(CMD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static CMDOutput cmd(String path, String command, boolean showProgress) {

        CMDOutput result = new CMDOutput();

        try {

            Process exec;

            if (path != null) {
                exec = Runtime.getRuntime().exec(command, null, new File(path));
            } else {
                exec = Runtime.getRuntime().exec(command);
            }

            String s;

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            if (showProgress) {
                System.out.println("-----------------------------------------------------------");
                System.out.println("                 Output");
                System.out.println("-----------------------------------------------------------");
            }
            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                if (showProgress) {
                    System.out.println(s);
                }
                result.addOutput(s);
            }

            if (showProgress) {
                System.out.println("-----------------------------------------------------------");
                System.out.println("                 Error");
                System.out.println("-----------------------------------------------------------");
            }
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                if (showProgress) {
                    System.out.println(s);
                }
                result.addErrors(s);
            }

            System.out.println("-----------------------------------------------------------");
        } catch (IOException ex) {
            Logger.getLogger(CMD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
