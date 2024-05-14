package com.mycompany.testerefactoringminer2.v;

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

    public static HashMap<String, Integer> mapHashQntRefatoracoes = new HashMap<>();

    @CsvBindByName(column = "1_hash")
    private String hash;

    @CsvBindByName(column = "2_parentHash")
    private String parentHash;

    @CsvBindByName(column = "3_refactoringType")
    private String type;

    @CsvBindByName(column = "4_ocorrido")
    private String ocorrido;

    public RefactoringSave(String hash, String parentHash, String type, String ocorrido) {

        this.hash = hash;
        this.parentHash = parentHash;
        this.type = type;
        this.ocorrido = ocorrido;

        if (mapHashQntRefatoracoes.containsKey(hash)) {
            mapHashQntRefatoracoes.put(hash, mapHashQntRefatoracoes.get(hash) + 1);
        } else {
            mapHashQntRefatoracoes.put(hash, 1);
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
}
