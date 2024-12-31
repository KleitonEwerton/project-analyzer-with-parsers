package com.minerprojects;

import com.opencsv.bean.CsvBindByName;
import java.util.ArrayList;
import java.util.List;

public class ErrorReporter {

    @CsvBindByName(column = "10_HASH")
    private String hash;

    @CsvBindByName(column = "11_PARENTHASH")
    private String parentHash;

    @CsvBindByName(column = "MSG_ERRO")
    private String erroMSG;

    public static List<ErrorReporter> errosCheckout = new ArrayList<>();

    public ErrorReporter(String hash, String parentHash, String erroMSG) {
        this.hash = hash;
        this.parentHash = parentHash;
        this.erroMSG = erroMSG;
        errosCheckout.add(this);
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
     * @return String return the erroMSG
     */
    public String getErroMSG() {
        return erroMSG;
    }

    /**
     * @param erroMSG the erroMSG to set
     */
    public void setErroMSG(String erroMSG) {
        this.erroMSG = erroMSG;
    }

}
