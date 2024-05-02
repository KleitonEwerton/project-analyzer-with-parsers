package com.mycompany.testerefactoringminer2.v;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class SalvarDados {

    @CsvBindByName(column = "1_HASH")
    private String hash;

    @CsvBindByName(column = "2_EMAIL")
    private String userEmail;

    @CsvBindByName(column = "3_QUANTIDADEREFATORACOES")
    private int qntRefatoracoes;

    @CsvBindByName(column = "4_QUANTIDADECOMENTARIOS")
    private int qntComentarios;

    @CsvBindByName(column = "4_QUANTIDADESEGMENTOS")
    private int qntSegmentos;

    public static List<SalvarDados> salvarDadosList = new ArrayList<>();

    public SalvarDados(String hash, int qntRefatoracoes, int qntComentarios, int qntSegmentos) {

        this.hash = hash;
        this.qntComentarios = qntComentarios;
        this.qntRefatoracoes = qntRefatoracoes;
        this.qntSegmentos = qntSegmentos;
        this.userEmail = ExecRefactoringMiner240v.mapHashEmail.get(hash);

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getQntRefatoracoes() {
        return qntRefatoracoes;
    }

    public void setQntRefatoracoes(int qntRefatoracoes) {
        this.qntRefatoracoes = qntRefatoracoes;
    }

    public int getQntComentarios() {
        return qntComentarios;
    }

    public void setQntComentarios(int qntComentarios) {
        this.qntComentarios = qntComentarios;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getQntSegmentos() {
        return qntSegmentos;
    }

    public void setQntSegmentos(int qntSegmentos) {
        this.qntSegmentos = qntSegmentos;
    }

}
