package com.mycompany.testerefactoringminer2.v;

import java.util.List;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Paths;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class SalvarDadosByClass {

    public static HashMap<String, SalvarDadosByClass> mapSalvarDadosByClass = new HashMap<>();
    public static List<SalvarDadosByClass> listaSalvarDadosByClass = new ArrayList<>();

    @CsvBindByName(column = "11_HASH")
    private String hash;

    @CsvBindByName(column = "12_PARENT_HASH")
    private String parentHash;

    @CsvBindByName(column = "13_HASH_CLASSPATH")
    private String hash_classPath;

    @CsvBindByName(column = "14_QUANTIDADEREFATORACOES")
    private int qntRefatoracoes;

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
     * @return String return the hash_classPath
     */
    public String getHash_classPath() {
        return hash_classPath;
    }

    /**
     * @param hash_classPath the hash_classPath to set
     */
    public void setHash_classPath(String hash_classPath) {
        this.hash_classPath = hash_classPath;
    }

    /**
     * @return int return the qntRefatoracoes
     */
    public int getQntRefatoracoes() {
        return qntRefatoracoes;
    }

    /**
     * @param qntRefatoracoes the qntRefatoracoes to set
     */
    public void setQntRefatoracoes(int qntRefatoracoes) {
        this.qntRefatoracoes = qntRefatoracoes;
    }

    /**
     * @return int return the qntComentarios
     */
    public int getQntComentarios() {
        return qntComentarios;
    }

    /**
     * @param qntComentarios the qntComentarios to set
     */
    public void setQntComentarios(int qntComentarios) {
        this.qntComentarios = qntComentarios;
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
    }

    /**
     * @return int return the diferencaQntComentarios
     */
    public int getDiferencaQntComentarios() {
        return diferencaQntComentarios;
    }

    /**
     * @param diferencaQntComentarios the diferencaQntComentarios to set
     */
    public void setDiferencaQntComentarios(int diferencaQntComentarios) {
        this.diferencaQntComentarios = diferencaQntComentarios;
    }

    /**
     * @return int return the qntSegmentos
     */
    public int getQntSegmentos() {
        return qntSegmentos;
    }

    /**
     * @param qntSegmentos the qntSegmentos to set
     */
    public void setQntSegmentos(int qntSegmentos) {
        this.qntSegmentos = qntSegmentos;
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
    }

    /**
     * @return int return the diferencaQntSegmentos
     */
    public int getDiferencaQntSegmentos() {
        return diferencaQntSegmentos;
    }

    /**
     * @param diferencaQntSegmentos the diferencaQntSegmentos to set
     */
    public void setDiferencaQntSegmentos(int diferencaQntSegmentos) {
        this.diferencaQntSegmentos = diferencaQntSegmentos;
    }

    public static void saveDadosByClassCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<SalvarDadosByClass> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(SalvarDadosByClass.listaSalvarDadosByClass);
        writer.flush();
        writer.close();
        SalvarDadosByClass.listaSalvarDadosByClass.clear();

    }

}
