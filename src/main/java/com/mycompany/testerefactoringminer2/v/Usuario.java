package com.mycompany.testerefactoringminer2.v;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class Usuario {

    public static List<Usuario> usuariosList = new ArrayList<>();

    @CsvBindByName(column = "1_hash")
    private String hash;

    @CsvBindByName(column = "2_email")
    private String email;

    @CsvBindByName(column = "3_dataHora")
    private String dataHora;

    public Usuario(String hash, String email, String dataHora) {
        this.hash = hash;
        this.email = email;
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Usuario [dataHora=" + dataHora + ", email=" + email + ", hash=" + hash + "]";
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

}
