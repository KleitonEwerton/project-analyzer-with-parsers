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

import com.minerprojects.badsmelldetector.pmd.TooManyFields;

public class SaxTooManyFields extends DefaultHandler {

    private String tagAtual;

    private final List<TooManyFields> tooManyFieldss;
    private TooManyFields tooManyFields;

    /**
     * construtor default
     */
    public SaxTooManyFields() {
        super();
        this.tooManyFieldss = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return TooManyFieldss
     */
    public List<TooManyFields> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return tooManyFieldss;

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
    // SaxTooManyFields
    /**
     * evento startDocument do SaxTooManyFields. Disparado antes do
     * processamento
     * da primeira linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxTooManyFields. Disparado depois do
     * processamento da
     * última linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxTooManyFields. disparado quando o
     * processador
     * SaxTooManyFields identifica a abertura de uma tag. Ele possibilita a
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
            tooManyFields = new TooManyFields();

            tooManyFields.getViolationPMD2().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            tooManyFields.getViolationPMD2().setEndLine(Integer.parseInt(atts.getValue("endline")));
            tooManyFields.getViolationPMD2().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            tooManyFields.getViolationPMD2().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            tooManyFields.getViolationPMD2().setClassPackage(atts.getValue("package"));
            tooManyFields.getViolationPMD2().setRule(atts.getValue("rule"));
            tooManyFields.getViolationPMD2().setClassFound(atts.getValue("class"));
            tooManyFields.getViolationPMD2().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            tooManyFields.getViolationPMD2().setPriority(Integer.parseInt(atts.getValue("priority")));

            tooManyFieldss.add(tooManyFields);
        }
    }

    /**
     * evento endElement do SaxTooManyFields. Disparado quando o processador
     * SaxTooManyFields identifica o fechamento de uma tag (ex: )
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
            tooManyFields = new TooManyFields();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxTooManyFields. É onde podemos recuperar as
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
            tooManyFields.getViolationPMD2().setContent(text);
        }
    }

}
