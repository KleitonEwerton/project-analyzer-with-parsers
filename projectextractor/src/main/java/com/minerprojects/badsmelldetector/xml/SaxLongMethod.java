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

import com.minerprojects.badsmelldetector.pmd.LongMethod;

public class SaxLongMethod extends DefaultHandler {

    private String tagAtual;

    private final List<LongMethod> longMethods;
    private LongMethod longMethod;

    /**
     * construtor default
     */
    public SaxLongMethod() {
        super();
        this.longMethods = new ArrayList();
    }

    /**
     * Método que executa o parsing: laço automático que varre o documento de
     * início ao fim, disparando eventos relevantes
     *
     * @param xml
     * @return longMethods
     */
    public List<LongMethod> fazerParsing(String xml) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();

            saxParser.parse(new InputSource(new StringReader(xml)), this);
            return longMethods;

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
    // SaxLongMethod
    /**
     * evento startDocument do SaxLongMethod. Disparado antes do processamento
     * da primeira linha
     */
    @Override
    public void startDocument() {
        // System.out.println("\nIniciando o Parsing...\n");

    }

    /**
     * evento endDocument do SaxLongMethod. Disparado depois do processamento da
     * última linha
     */
    @Override
    public void endDocument() {
        // System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SaxLongMethod. disparado quando o processador
     * SaxLongMethod identifica a abertura de uma tag. Ele possibilita a captura
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
            longMethod = new LongMethod();

            longMethod.getViolationPMD2().setBeginLine(Integer.parseInt(atts.getValue("beginline")));
            longMethod.getViolationPMD2().setEndLine(Integer.parseInt(atts.getValue("endline")));
            longMethod.getViolationPMD2().setBeginColumn(Integer.parseInt(atts.getValue("begincolumn")));
            longMethod.getViolationPMD2().setEndColumn(Integer.parseInt(atts.getValue("endcolumn")));
            longMethod.getViolationPMD2().setClassPackage(atts.getValue("package"));
            longMethod.getViolationPMD2().setRule(atts.getValue("rule"));
            longMethod.getViolationPMD2().setClassFound(atts.getValue("class"));
            longMethod.getViolationPMD2().setMethodName(atts.getValue("method"));
            longMethod.getViolationPMD2().setExternalInfoUrl(atts.getValue("externalInfoUrl"));
            longMethod.getViolationPMD2().setPriority(Integer.parseInt(atts.getValue("priority")));

            longMethods.add(longMethod);
        }
    }

    /**
     * evento endElement do SaxLongMethod. Disparado quando o processador
     * SaxLongMethod identifica o fechamento de uma tag (ex: )
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
            longMethod = new LongMethod();
        }
        tagAtual = "";
    }

    /**
     * evento characters do SaxLongMethod. É onde podemos recuperar as
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
            longMethod.getViolationPMD2().setContent(text);
        }
    }

}
