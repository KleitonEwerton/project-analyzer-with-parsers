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

    @CsvBindByName(column = "11_HASH")
    private String hash;

    @CsvBindByName(column = "12_parentHash")
    private String parentHash;

    @CsvBindByName(column = "13_refactoringType")
    private String type;

    @CsvBindByName(column = "14_ocorrido")
    private String ocorrido;

    @CsvBindByName(column = "15_oldPathClass")
    private String oldPathClass;

    @CsvBindByName(column = "16_newPathClass")
    private String newPathClass;

    @CsvBindByName(column = "17_hash_oldPathClass")
    private String hash_oldPathClass;

    public RefactoringSave(String hash, String parentHash, String type, String ocorrido, String oldPathClass,
            String newPathClass) {

        this.hash = hash;
        this.parentHash = parentHash;
        this.type = type;
        this.ocorrido = ocorrido;
        this.newPathClass = newPathClass;
        this.oldPathClass = oldPathClass;

        this.hash_oldPathClass = hash + "/" + oldPathClass;

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

    /**
     * @return String return the oldPathClass
     */
    public String getOldPathClass() {
        return oldPathClass;
    }

    /**
     * @param oldPathClass the oldPathClass to set
     */
    public void setOldPathClass(String oldPathClass) {
        this.oldPathClass = oldPathClass;
    }

    /**
     * @return String return the newPathClass
     */
    public String getNewPathClass() {
        return newPathClass;
    }

    /**
     * @param newPathClass the newPathClass to set
     */
    public void setNewPathClass(String newPathClass) {
        this.newPathClass = newPathClass;
    }

    /**
     * @return String return the HASH_oldPathClass
     */
    public String getHASH_oldPathClass() {
        return hash_oldPathClass;
    }

    /**
     * @param HASH_oldPathClass the HASH_oldPathClass to set
     */
    public void setHASH_oldPathClass(String HASH_oldPathClass) {
        this.hash_oldPathClass = HASH_oldPathClass;
    }

    /**
     * @return String return the hash_oldPathClass
     */
    public String getHash_oldPathClass() {
        return hash_oldPathClass;
    }

    /**
     * @param hash_oldPathClass the hash_oldPathClass to set
     */
    public void setHash_oldPathClass(String hash_oldPathClass) {
        this.hash_oldPathClass = hash_oldPathClass;
    }

}
