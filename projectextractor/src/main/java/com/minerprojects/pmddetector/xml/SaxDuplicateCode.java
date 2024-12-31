/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.pmddetector.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.minerprojects.pmddetector.cpd.DuplicateCode;
import com.minerprojects.pmddetector.utils.File;

import java.util.List;

/**
 * classe SaxDuplicatedCode: processa o documento XML de países com o uso da API
 * SaxDuplicatedCode, recuperando as informações de cada país e as imprimindo na
 * tela (usando uma linha para cada país).
 *
 * A classe "SaxDuplicatedCode" é derivada da classe "DefaultHandler" da
 * biblioteca org.xml.sax.helpers.DefaultHandler. Isso faz com que
 * "SaxDuplicatedCode" “ganhe” automaticamente um processador SaxDuplicatedCode
 * com o comportamento default.
 */
public class SaxDuplicateCode extends DefaultHandler {

    private String tagAtual;

    private final List<DuplicateCode> duplicateCodes;
    DuplicateCode duplicateCode;

    File file;
    Integer numLines = null;
    Integer tokens = null;
    Integer numFiles = 0;

    /**
     * construtor default
     */
    public SaxDuplicateCode() {
        super();
        duplicateCodes = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return duplicatedCodes
     */
    public List<DuplicateCode> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return duplicateCodes;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            StringBuffer msg = new StringBuffer();
            msg.append("Erro:\n");
            msg.append(e.getMessage()).append("\n");
            msg.append(e.toString());
            System.out.println(msg);
        }
        return null;
    }

    // os métodos startDocument, endDocument, startElement, endElement e
    // characters, listados a seguir, representam os métodos de call-back da API
    // SaxDuplicatedCode
    /**
     * evento startDocument do SaxDuplicatedCode. Disparado antes do
     * processamento da primeira linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxDuplicatedCode. Disparado depois do
     * processamento da última linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxDuplicatedCode. disparado quando o processador
     * SaxDuplicatedCode identifica a abertura de uma tag. Ele possibilita a
     * captura do nome da tag e dos nomes e valores de todos os atributos
     * associados a esta tag, caso eles existam.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param atts
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) {

        // recupera o nome da tag atual
        tagAtual = qName;

        if (qName.equals("file")) {
            duplicateCode = new DuplicateCode();
            file = new File();

            file.getViolation().setBeginLine(Integer.parseInt(atts.getValue("line")));
            file.getViolation().setEndLine(Integer.parseInt(atts.getValue("endline")));
            file.getViolation().setBeginColumn(Integer.parseInt(atts.getValue("column")));
            file.getViolation().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            file.setPath(atts.getValue("path"));

            duplicateCode.getViolationCPD().setFile(file);
            duplicateCode.getViolationCPD().setNumLines(numLines);
            duplicateCode.getViolationCPD().setTokens(tokens);

            duplicateCodes.add(duplicateCode);
            numFiles++;
        }

        if (qName.equals("duplication")) {
            numLines = Integer.parseInt(atts.getValue("lines"));
            tokens = Integer.parseInt(atts.getValue("tokens"));
        }

    }

    /**
     * evento endElement do SaxDuplicatedCode. Disparado quando o processador
     * SaxDuplicatedCode identifica o fechamento de uma tag (ex: )
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws org.xml.sax.SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equals("duplication")) {
            numLines = null;
            tokens = null;
            numFiles = 0;
        }

        tagAtual = "";
    }

    /**
     * evento characters do SaxDuplicatedCode. É onde podemos recuperar as
     * informações texto contidas no documento XML (textos contidos entre tags).
     * Neste exemplo, recuperamos os nomes dos países, a população e a moeda
     *
     * @param ch
     * @param start
     * @param length
     * @throws org.xml.sax.SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String text = new String(ch, start, length);

        if (tagAtual.compareTo("codefragment") == 0) {
            int duplicateCodesSize = duplicateCodes.size();
            for (int i = 1; i <= numFiles; i++) {
                duplicateCodes.get(duplicateCodesSize - i).getViolationCPD().setCodeDuplicateText(text);
            }
        }
    }
}
