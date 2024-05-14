package com.mycompany.testerefactoringminer2.v;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class Usuario {

    public static List<Usuario> usuariosList = new ArrayList<>();

    @CsvBindByName(column = "1_hash")
    private String hash;

    @CsvBindByName(column = "2_parentHash")
    private String parentHash;

    @CsvBindByName(column = "3_email")
    private String email;

    @CsvBindByName(column = "4_dataHora")
    private String dataHora;

    public Usuario(String hash, String parentHash, String email, String dataHora) {
        this.hash = hash;
        this.parentHash = parentHash;
        this.email = email;
        this.dataHora = dataHora;
    }

    /**
     * @return String return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the dataHora
     */
    public String getDataHora() {
        return dataHora;
    }

    /**
     * @param dataHora the dataHora to set
     */
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * @return String return the parentHash
     */
    public String getParentHash() {
        return parentHash;
    }

    /**
     * @param parentHash the parentHash to set
     */
    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public static void saveUserCommitsCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<Usuario> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(Usuario.usuariosList);
        writer.flush();
        writer.close();
        Usuario.usuariosList.clear();

    }

}
