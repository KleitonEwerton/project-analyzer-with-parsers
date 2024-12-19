/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.minerprojects.badsmelldetector.pmd.LongParameterList;

/**
 * classe SaxLongParamList: processa o documento XML de países com o uso da API
 * SaxLongParamList,
 * recuperando as informações de cada país e as imprimindo na tela (usando uma
 * linha para cada país).
 *
 * A classe "SaxLongParamList" é derivada da classe "DefaultHandler" da
 * biblioteca
 * org.xml.sax.helpers.DefaultHandler. Isso faz com que "SaxLongParamList"
 * “ganhe”
 * automaticamente um processador SaxLongParamList com o comportamento default.
 */

public class SaxLongParamList extends DefaultHandler {

    private String tagAtual;

    private final List<LongParameterList> paramsList;
    LongParameterList paramList;

    /**
     * construtor default
     */
    public SaxLongParamList() {
        super();
        paramsList = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return paramsList
     */
    public List<LongParameterList> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return paramsList;

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
    // SaxLongParamList

    /**
     * evento startDocument do SaxLongParamList. Disparado antes do processamento da
     * primeira
     * linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxLongParamList. Disparado depois do processamento da
     * última
     * linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxLongParamList. disparado quando o processador
     * SaxLongParamList identifica
     * a abertura de uma tag. Ele possibilita a captura do nome da tag e dos
     * nomes e valores de todos os atributos associados a esta tag, caso eles
     * existam.
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

        if (qName.compareTo("violation") == 0) {
            paramList = new LongParameterList();

            paramList.getViolationPMD2().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            paramList.getViolationPMD2().setEndLine(Integer.parseInt(atts.getValue("endline")));
            paramList.getViolationPMD2().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            paramList.getViolationPMD2().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            paramList.getViolationPMD2().setClassPackage(atts.getValue("package"));
            paramList.getViolationPMD2().setRule(atts.getValue("rule"));
            paramList.getViolationPMD2().setClassFound(atts.getValue("class"));
            paramList.getViolationPMD2().setMethodName(atts.getValue("method"));
            paramList.getViolationPMD2().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            paramList.getViolationPMD2().setPriority(Integer.parseInt(atts.getValue("priority")));

            paramsList.add(paramList);
        }
    }

    /**
     * evento endElement do SaxLongParamList. Disparado quando o processador
     * SaxLongParamList identifica o
     * fechamento de uma tag (ex: )
     * 
     * @param uri
     * @param localName
     * @param qName
     * @throws org.xml.sax.SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equals("violation")) {
            paramList = new LongParameterList();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxLongParamList. É onde podemos recuperar as
     * informações texto
     * contidas no documento XML (textos contidos entre tags). Neste exemplo,
     * recuperamos os nomes dos países, a população e a moeda
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

        if (tagAtual.compareTo("violation") == 0) {
            paramList.getViolationPMD2().setContent(text);
        }
    }
}