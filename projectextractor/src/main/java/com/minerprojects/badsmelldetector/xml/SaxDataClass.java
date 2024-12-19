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

import com.minerprojects.badsmelldetector.pmd.DataClass;

/**
 * classe SaxDataClass: processa o documento XML de países com o uso da API
 * SaxDataClass,
 * recuperando as informações de cada país e as imprimindo na tela (usando uma
 * linha para cada país).
 *
 * A classe "SaxDataClass" é derivada da classe "DefaultHandler" da biblioteca
 * org.xml.sax.helpers.DefaultHandler. Isso faz com que "SaxDataClass" “ganhe”
 * automaticamente um processador SaxDataClass com o comportamento default.
 */
public class SaxDataClass extends DefaultHandler {

    private String tagAtual;

    private final List<DataClass> datas;
    DataClass data;

    /**
     * construtor default
     */
    public SaxDataClass() {
        super();
        datas = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return datas
     */
    public List<DataClass> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return datas;

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
    // SaxDataClass

    /**
     * evento startDocument do SaxDataClass. Disparado antes do processamento da
     * primeira
     * linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxDataClass. Disparado depois do processamento da
     * última
     * linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxDataClass. disparado quando o processador
     * SaxDataClass identifica
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
            data = new DataClass();

            data.getViolationPMD().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            data.getViolationPMD().setEndLine(Integer.parseInt(atts.getValue("endline")));
            data.getViolationPMD().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            data.getViolationPMD().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            data.getViolationPMD().setClassPackage(atts.getValue("package"));
            data.getViolationPMD().setRule(atts.getValue("rule"));
            data.getViolationPMD().setClassFound(atts.getValue("class"));
            data.getViolationPMD().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            data.getViolationPMD().setPriority(Integer.parseInt(atts.getValue("priority")));

            datas.add(data);
        }
    }

    /**
     * evento endElement do SaxDataClass. Disparado quando o processador
     * SaxDataClass identifica o
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
            data = new DataClass();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxDataClass. É onde podemos recuperar as informações
     * texto
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
            data.getViolationPMD().setContent(text);
        }
    }
}