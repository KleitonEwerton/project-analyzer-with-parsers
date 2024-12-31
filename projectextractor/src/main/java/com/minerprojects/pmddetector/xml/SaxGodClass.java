/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.pmddetector.xml;

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

import com.minerprojects.pmddetector.pmd.GodClass;

/**
 * classe SaxGodClass: processa o documento XML de países com o uso da API
 * SaxGodClass,
 * recuperando as informações de cada país e as imprimindo na tela (usando uma
 * linha para cada país).
 *
 * A classe "SaxGodClass" é derivada da classe "DefaultHandler" da biblioteca
 * org.xml.sax.helpers.DefaultHandler. Isso faz com que "SaxGodClass" “ganhe”
 * automaticamente um processador SaxGodClass com o comportamento default.
 */
public class SaxGodClass extends DefaultHandler {

    private String tagAtual;

    private final List<GodClass> gods;
    GodClass god;

    /**
     * construtor default
     */
    public SaxGodClass() {
        super();
        gods = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return gods
     */
    public List<GodClass> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return gods;

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
    // SaxGodClass

    /**
     * evento startDocument do SaxGodClass. Disparado antes do processamento da
     * primeira
     * linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxGodClass. Disparado depois do processamento da
     * última
     * linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxGodClass. disparado quando o processador
     * SaxGodClass identifica
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
            god = new GodClass();

            god.getViolationPMD().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            god.getViolationPMD().setEndLine(Integer.parseInt(atts.getValue("endline")));
            god.getViolationPMD().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            god.getViolationPMD().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            god.getViolationPMD().setClassPackage(atts.getValue("package"));
            god.getViolationPMD().setRule(atts.getValue("rule"));
            god.getViolationPMD().setClassFound(atts.getValue("class"));
            god.getViolationPMD().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            god.getViolationPMD().setPriority(Integer.parseInt(atts.getValue("priority")));

            gods.add(god);
        }

    }

    /**
     * evento endElement do SaxGodClass. Disparado quando o processador SaxGodClass
     * identifica o
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
            god = new GodClass();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxGodClass. É onde podemos recuperar as informações
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
            god.getViolationPMD().setContent(text);
        }
    }
}
