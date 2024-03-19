package com.mycompany.testerefactoringminer2.v;

import java.util.List;

public class SalvarDados {
    private String hash;

    private String data;

    private int qntRefatoracoes;

    private  int qntComentarios;
    private String userEmail;

    public SalvarDados(String hash, String data, int qntRefatoracoes, int qntComentarios, String userEmail){

        this.hash = hash;
        this.data = data;
        this.qntComentarios = qntComentarios;
        this.qntRefatoracoes = qntRefatoracoes;
        this.userEmail = userEmail;

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    @Override
    public String toString(){
        return this.hash + ", " +this.data + ", "+ this.qntRefatoracoes +", "+ this.qntComentarios +", "+ this.userEmail;
    }
}
