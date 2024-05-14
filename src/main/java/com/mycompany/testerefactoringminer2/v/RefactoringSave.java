package com.mycompany.testerefactoringminer2.v;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class RefactoringSave {

    public static List<RefactoringSave> refactoringList = new ArrayList<>();

    public static int qntRefatoracoes = 0;

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

        RefactoringSave.qntRefatoracoes++;
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

}
