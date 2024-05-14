package com.mycompany.testerefactoringminer2.v;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.Writer;

public class SalvarDados {

    @CsvBindByName(column = "10_HASH")
    private String hash;

    @CsvBindByName(column = "11_PARENT_HASH")
    private String parentHash;

    @CsvBindByName(column = "12_HASH_QUANTIDADEREFATORACOES")
    private int qntRefatoracoes;

    @CsvBindByName(column = "13_PARENT_HASH_QUANTIDADEREFATORACOES")
    private int parentqntRefatoracoes;

    @CsvBindByName(column = "14_DIFERENCA_QUANTIDADECOMENTARIOS")
    private int diferencaQntRefatoracoes;

    @CsvBindByName(column = "15_HASH_QUANTIDADECOMENTARIOS")
    private int qntComentarios;

    @CsvBindByName(column = "16_PARENT_HASH_QUANTIDADECOMENTARIOS")
    private int parentqntComentarios;

    @CsvBindByName(column = "17_DIFERENCA_QUANTIDADECOMENTARIOS")
    private int diferencaQntComentarios;

    @CsvBindByName(column = "18_HASH_QUANTIDADESEGMENTOS")
    private int qntSegmentos;

    @CsvBindByName(column = "19_PARENT_HASH_QUANTIDADESEGMENTOS")
    private int parentqntSegmentos;

    @CsvBindByName(column = "20_DIFERENCA_QUANTIDADESEGMENTOS")
    private int diferencaQntSegmentos;

    private String userEmail;
    private String userParentEmail;

    public static List<SalvarDados> salvarDadosList = new ArrayList<>();

    public SalvarDados(String hash, int qntRefatoracoes, int qntComentarios, int qntSegmentos, String parentHash,
            int parentqntRefatoracoes,
            int parentqntComentarios, int parentqntSegmentos) {

        this.hash = hash;
        this.qntComentarios = qntComentarios;
        this.qntRefatoracoes = qntRefatoracoes;
        this.qntSegmentos = qntSegmentos;
        this.parentHash = parentHash;
        this.parentqntRefatoracoes = parentqntRefatoracoes;
        this.parentqntComentarios = parentqntComentarios;
        this.parentqntSegmentos = parentqntSegmentos;

        this.userEmail = ExecRefactoringMiner240v.mapHashEmail.get(hash);
        this.userParentEmail = ExecRefactoringMiner240v.mapHashEmail.get(parentHash);

        calcularDiferencas();

        salvarDadosList.add(this);

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
        calcularDiferencas();

    }

    public int getQntComentarios() {
        return qntComentarios;
    }

    public void setQntComentarios(int qntComentarios) {
        this.qntComentarios = qntComentarios;
        calcularDiferencas();

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
        calcularDiferencas();

    }

    /**
     * @return int return the parentqntRefatoracoes
     */
    public int getParentqntRefatoracoes() {
        return parentqntRefatoracoes;
    }

    /**
     * @param parentqntRefatoracoes the parentqntRefatoracoes to set
     */
    public void setParentqntRefatoracoes(int parentqntRefatoracoes) {
        this.parentqntRefatoracoes = parentqntRefatoracoes;
        calcularDiferencas();

    }

    /**
     * @return int return the parentqntComentarios
     */
    public int getParentqntComentarios() {
        return parentqntComentarios;
    }

    /**
     * @param parentqntComentarios the parentqntComentarios to set
     */
    public void setParentqntComentarios(int parentqntComentarios) {
        this.parentqntComentarios = parentqntComentarios;
        calcularDiferencas();

    }

    /**
     * @return int return the parentqntSegmentos
     */
    public int getParentqntSegmentos() {
        return parentqntSegmentos;
    }

    /**
     * @param parentqntSegmentos the parentqntSegmentos to set
     */
    public void setParentqntSegmentos(int parentqntSegmentos) {
        this.parentqntSegmentos = parentqntSegmentos;
        calcularDiferencas();

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

    /**
     * @return int return the diferencaQntRefatoracoes
     */
    public int getDiferencaQntRefatoracoes() {
        return diferencaQntRefatoracoes;
    }

    /**
     * @return int return the diferencaQntComentarios
     */
    public int getDiferencaQntComentarios() {
        return diferencaQntComentarios;
    }

    /**
     * @return int return the diferencaQntSegmentos
     */
    public int getDiferencaQntSegmentos() {
        return diferencaQntSegmentos;
    }

    /**
     * @return String return the userParentEmail
     */
    public String getUserParentEmail() {
        return userParentEmail;
    }

    /**
     * @param userParentEmail the userParentEmail to set
     */
    public void setUserParentEmail(String userParentEmail) {
        this.userParentEmail = userParentEmail;
    }

    public void calcularDiferencas() {
        this.diferencaQntRefatoracoes = qntRefatoracoes - parentqntRefatoracoes;
        this.diferencaQntComentarios = qntComentarios - parentqntComentarios;
        this.diferencaQntSegmentos = qntSegmentos - parentqntSegmentos;
    }

    public static void saveDadosCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<SalvarDados> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(SalvarDados.salvarDadosList);
        writer.flush();
        writer.close();
        SalvarDados.salvarDadosList.clear();

    }
}
