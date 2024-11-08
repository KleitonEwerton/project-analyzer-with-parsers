package com.mycompany.testerefactoringminer2.version;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.Writer;

public class RefactoringSave {

    public static List<RefactoringSave> refactoringList = new ArrayList<>();

    public static HashMap<String, Integer> qntRefatoracoes = new HashMap<>();

    private String parentHash;

    private String pathClass;

    @CsvBindByName(column = "10_HASH")
    private String hash;

    @CsvBindByName(column = "11_HASH_CLASSPATH")
    private String hash_pathClass;

    private String movedToPathClass;

    @CsvBindByName(column = "13_refactoringType")
    private String type;

    private String ocorrido;

    public RefactoringSave(String hash, String parentHash, String type, String ocorrido, String pathClass,
            String movedToPathClass) {

        this.hash = hash;
        this.parentHash = parentHash;
        this.type = type;
        this.ocorrido = ocorrido;
        this.movedToPathClass = movedToPathClass;
        this.pathClass = pathClass;

        this.hash_pathClass = hash + "/" + pathClass;

        if (qntRefatoracoes.containsKey(hash)) {
            qntRefatoracoes.put(hash, qntRefatoracoes.get(hash) + 1);
        } else {
            qntRefatoracoes.put(hash, 1);
        }

        refactoringList.add(this);
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
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String return the ocorrido
     */
    public String getOcorrido() {
        return ocorrido;
    }

    /**
     * @param ocorrido the ocorrido to set
     */
    public void setOcorrido(String ocorrido) {
        this.ocorrido = ocorrido;
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
     * @param hash_pathClass the hash_pathClass to set
     */
    public void setHash_pathClass(String hash_pathClass) {
        this.hash_pathClass = hash_pathClass;
    }

    /**
     * @return String return the pathClass
     */
    public String getPathClass() {
        return pathClass;
    }

    /**
     * @param pathClass the pathClass to set
     */
    public void setPathClass(String pathClass) {
        this.pathClass = pathClass;
    }

    /**
     * @return String return the movedToPathClass
     */
    public String getMovedToPathClass() {
        return movedToPathClass;
    }

    /**
     * @param movedToPathClass the movedToPathClass to set
     */
    public void setMovedToPathClass(String movedToPathClass) {
        this.movedToPathClass = movedToPathClass;
    }

    /**
     * @return String return the hash_pathClass
     */
    public String getHash_pathClass() {
        return hash_pathClass;
    }

    public static void saveRefactoringCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<RefactoringSave> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(RefactoringSave.refactoringList);
        writer.flush();
        writer.close();
        RefactoringSave.refactoringList.clear();

    }

}
