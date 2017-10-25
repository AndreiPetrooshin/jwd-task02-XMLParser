package com.andreipetrushin.dao;

import com.andreipetrushin.dao.exception.DAOLayerException;
import com.andreipetrushin.entity.impl.Attr;
import com.andreipetrushin.entity.impl.Node;
import com.andreipetrushin.dao.impl.DocumentImpl;

import java.io.*;
import java.util.regex.Pattern;

/**
 * This class consist exclusively of static methods
 * that operate on or return Document.
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public final class XMLParserDAO {

    /**
     * This pattern {@link Pattern} checks if there is
     * such type construction <?xml version="1.0" encoding="UTF-8"?> like this
     */
    private final static Pattern XML_DECLARATION = Pattern.compile("(<\\?.*\\?>)");


    /**
     * This pattern {@link Pattern} checks if there is
     * such type construction <!-- Comments --> like this
     */
    private final static Pattern COMMENT_TAG = Pattern.compile("(<!--.*-->)");


    /**
     * This pattern {@link Pattern} checks if there is
     * such type construction </tagName> like this
     */
    private final static Pattern END_TAG = Pattern.compile("</.*>");


    /**
     * This pattern {@link Pattern} checks if there is
     * such type construction <tagName attribute="value"> like this
     */
    private final static Pattern SIMPLE_TAG = Pattern.compile("<[^/][\\s\\w\\-_=\"']+>");

    /**
     * This pattern {@link Pattern} checks if there is
     * such type construction <tagName attribute="value"/> like this
     */
    private final static Pattern CLOSED_TAG = Pattern.compile("<[\\s\\w]+[ \\w\\S]+/[\\s]*>");


    private static Document document;


    /**
     * Parses the XML file to {@link Document}
     *
     * @param path - Path to file
     * @return Document
     * @throws DAOLayerException - if the something go wrong
     */
    public static Document parseXML(String path) throws DAOLayerException {
        try (BufferedReader reader = new BufferedReader(getReaderByPath(path))) {
            String xmlStr = null;
            while (reader.ready()) {
                xmlStr += (char) reader.read();
            }
            String tagLine = null;
            while (!xmlStr.isEmpty()) {
                int bracket = xmlStr.indexOf("<");
                int nextBracket = xmlStr.indexOf("<", bracket + 1);
                if (nextBracket == -1) {
                    parseLine(tagLine);
                    break;
                }
                tagLine = xmlStr.substring(bracket, nextBracket).trim();
                xmlStr = xmlStr.substring(nextBracket).trim();
                parseLine(tagLine);
            }

        } catch (IOException e) {
            throw new DAOLayerException(e.fillInStackTrace());
        }
        return document;
    }




    /**
     * Parses xmlLine and creates the node Object
     * and put them to {@link StackDAO} list
     *
     * @param xmlLine - String line xml data
     */
    private static void parseLine(String xmlLine) {
        if (patternMatch(XML_DECLARATION, xmlLine)) {
            return;
        }
        if (patternMatch(COMMENT_TAG, xmlLine)) {
            return;
        }
        if (patternMatch(END_TAG, xmlLine)) {
            Node node = createNode(xmlLine);
            node.addToChildNodeList(node);
            node = StackDAO.popNode();
            if (!StackDAO.isEmpty()) {
                Node parent = StackDAO.peekNode();
                parent.addToChildNodeList(node);
            } else {
                createDocument(node);
            }
        } else if (patternMatch(SIMPLE_TAG, xmlLine)) {
            Node node = createNode(xmlLine);
            StackDAO.addNode(node);
        } else if (patternMatch(CLOSED_TAG, xmlLine)) {
            Node node = createNode(xmlLine);
            StackDAO.addToChild(node);
        }
    }




    /**
     * This method checks, whether there is RegEx in xmlLine
     *
     * @param pattern - Pattern with current RegEx
     * @param xmlLine - String line xml data
     * @return boolean - return true if RegEx was finds in String
     */
    private static boolean patternMatch(Pattern pattern, String xmlLine) {
        return pattern.matcher(xmlLine).find();
    }




    /**
     * Creates and init Node object by the line
     *
     * @param xmlLine String with xml data
     * @return {@link Node} - returns the created Node object
     */
    private static Node createNode(String xmlLine) {
        Node node = new Node();

        int openBracket = xmlLine.indexOf('<');
        int closeBracket = xmlLine.indexOf('>');
        int slashBracket = xmlLine.indexOf("/>");
        String tagName;

        if (slashBracket != -1) {
            tagName = xmlLine.substring(openBracket + 1, slashBracket);
        } else {
            tagName = xmlLine.substring(openBracket + 1, closeBracket);
        }

        String value = xmlLine.substring(closeBracket + 1);
        node.setValue(value);
        node.setName(tagName);
        setAttrs(node);

        return node;
    }





    /**
     * Creates the document {@link Document} and sets the root node
     *
     * @param node - the root node
     * @return Document object
     */
    private static Document createDocument(Node node) {
        document = new DocumentImpl();
        document.setRootNode(node);
        return document;
    }




    /**
     * This method takes the node object
     * split his Name and initializes the
     * Attributes at this object
     */
    private static void setAttrs(Node node) {
        String line = node.getName();

        if (line.contains(" ")) {

            String attrs = line.substring(line.indexOf(" ")).trim();
            String tagName = line.substring(0, line.indexOf(" "));
            node.setName(tagName);
            String[] attrArray = attrs.split("[\\s]+");

            for (String attr : attrArray) {
                String[] splitValues = attr.split("=");
                if (splitValues.length >= 2) {
                    String nameAttr = attr.split("=")[0];
                    String valueAttr = attr.split("=")[1];
                    Attr attribute = new Attr(nameAttr, valueAttr);
                    node.addToAttrList(attribute);
                }
            }
        }
    }




    /**
     * Creates and returns reader by Path
     *
     * @param path is relative Path to resource file;
     * @return {@link Reader}
     * @throws FileNotFoundException if file does not exist
     */
    private static Reader getReaderByPath(String path) throws FileNotFoundException {
        try {
            return new FileReader(new File(path));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

}