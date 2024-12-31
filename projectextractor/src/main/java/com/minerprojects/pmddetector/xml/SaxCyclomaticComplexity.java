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

import com.minerprojects.pmddetector.pmd.CyclomaticComplexity;

public class SaxCyclomaticComplexity extends DefaultHandler {

    private String tagAtual;

    private final List<CyclomaticComplexity> CyclomaticComplexitys;
    private CyclomaticComplexity CyclomaticComplexity;

    /**
     * construtor default
     */
    public SaxCyclomaticComplexity() {
        super();
        this.CyclomaticComplexitys = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return CyclomaticComplexitys
     */
    public List<CyclomaticComplexity> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return CyclomaticComplexitys;

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
    // SaxCyclomaticComplexity
    /**
     * evento startDocument do SaxCyclomaticComplexity. Disparado antes do
     * processamento
     * da primeira linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxCyclomaticComplexity. Disparado depois do
     * processamento da
     * última linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxCyclomaticComplexity. disparado quando o
     * processador
     * SaxCyclomaticComplexity identifica a abertura de uma tag. Ele possibilita a
     * captura
     * do nome da tag e dos nomes e valores de todos os atributos associados a
     * esta tag, caso eles existam.
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

        if (qName.equals("violation")) {
            CyclomaticComplexity = new CyclomaticComplexity();
            CyclomaticComplexity.getViolationPMD2().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            CyclomaticComplexity.getViolationPMD2().setEndLine(Integer.parseInt(atts.getValue("endline")));
            CyclomaticComplexity.getViolationPMD2().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            CyclomaticComplexity.getViolationPMD2().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            CyclomaticComplexity.getViolationPMD2().setClassPackage(atts.getValue("package"));
            CyclomaticComplexity.getViolationPMD2().setRule(atts.getValue("rule"));
            CyclomaticComplexity.getViolationPMD2().setClassFound(atts.getValue("class"));
            CyclomaticComplexity.getViolationPMD2().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            CyclomaticComplexity.getViolationPMD2().setPriority(Integer.parseInt(atts.getValue("priority")));
            CyclomaticComplexitys.add(CyclomaticComplexity);
        }
    }

    /**
     * evento endElement do SaxCyclomaticComplexity. Disparado quando o processador
     * SaxCyclomaticComplexity identifica o fechamento de uma tag (ex: )
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
            CyclomaticComplexity = new CyclomaticComplexity();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxCyclomaticComplexity. É onde podemos recuperar as
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

        if (tagAtual.compareTo("violation") == 0) {
            CyclomaticComplexity.getViolationPMD2().setContent(text);
        }
    }

}
