package com.mycompany.testerefactoringminer2.version;

import java.nio.file.Paths;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

public class ErroCheckout {

    @CsvBindByName(column = "10_HASH")
    private String hash;

    @CsvBindByName(column = "11_PARENTHASH")
    private String parentHash;

    @CsvBindByName(column = "MSG_ERRO")
    private String erroMSG;

    public static List<ErroCheckout> errosCheckout = new ArrayList<>();

    public ErroCheckout(String hash, String parentHash, String erroMSG) {
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

    public static void saveErrosCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<ErroCheckout> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(ErroCheckout.errosCheckout);
        writer.flush();
        writer.close();
        ErroCheckout.errosCheckout.clear();

    }

}
