package com.andreipetrushin.dao;

import com.andreipetrushin.dao.exception.DAOLayerException;
import com.andreipetrushin.entity.impl.Attr;
import com.andreipetrushin.entity.impl.Node;
import com.andreipetrushin.dao.impl.DocumentImpl;

import java.io.*;
import java.util.regex.Pattern;

/**
 * This class consist exclusively of static methods that operate on or return
 * Document.
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public final class ParseDAO {

    /**
     * Constant {@link Pattern} to checks if there is
     * such type construction <?xml version="1.0" encoding="UTF-8"?> like this
     */
    private final static Pattern XML_DECLARATION = Pattern.compile("(<\\?.*\\?>)");


    /**
     * Constant {@link Pattern} to checks if there is
     * such type construction <!-- Comments --> like this
     */
    private final static Pattern COMMENT_TAG = Pattern.compile("(<!--.*-->)");


    /**
     * Constant {@link Pattern} to checks if there is
     * such type construction </tagName> like this
     */
    private final static Pattern END_TAG = Pattern.compile("</.*>");


    /**
     * Constant {@link Pattern} to checks if there is
     * such type construction <tagName attribute="value"> like this
     */
    private final static Pattern SIMPLE_TAG = Pattern.compile("<[^/][\\s\\w\\-_=\"']+>");

    /**
     * Constant {@link Pattern} to checks if there is
     * such type construction <tagName attribute="value"> like this
     */
    private final static Pattern CLOSED_TAG= Pattern.compile("<[\\s\\w]+[ \\w\\S]+/[\\s]*>");




    private static Document document;


    /**
     * Parses the XML file to {@link Document}
     *
     * @param path - Path to file
     * @return Document
     * @throws DAOLayerException
     */
    public static Document parseXML(String path) throws DAOLayerException {
        try (BufferedReader lineReader = new BufferedReader(getReaderByPath(path))) {
            String xmlLine;
            while (lineReader.ready()) {
                xmlLine = lineReader.readLine();
                parseLine(xmlLine);
            }
        } catch (IOException e) {
            throw new DAOLayerException(e.fillInStackTrace());
        }
        return document;
    }

    /**
     * Creates and init Node object by the line in tag
     * with end brackets </ >
     *
     * @param xmlLine String with xml data
     * @return {@link Node} - returns the created Node object
     */
    private static Node createNodeForEndTag(String xmlLine) {
        Node node = new Node();
        String name = xmlLine.substring(xmlLine.indexOf('<') + 1, xmlLine.indexOf('>'));
        String text = xmlLine.substring(xmlLine.indexOf('>') + 1, xmlLine.indexOf("</"));
        node.setName(name);
        node.setValue(text);
        setAttrs(node);
        StackDAO.addToChild(node);
        return node;
    }

    /**
     * Creates and init Node object by the line in tag
     * with simple brackets "<>"
     *
     * @param xmlLine String with xml data
     * @return {@link Node} - returns the created Node object
     */
    private static Node createNodeForSimpleTag(String xmlLine) {
        Node node = new Node();
        String name = xmlLine.substring(xmlLine.indexOf('<') + 1, xmlLine.indexOf('>'));
        node.setName(name);
        setAttrs(node);
        return node;
    }

    /**
     * Creates and init Node object by the line in tag
     * with simple brackets "< />"
     *
     * @param xmlLine String with xml data
     * @return {@link Node} - returns the created Node object
     */
    private static Node createNodeForClosedTag(String xmlLine) {
        Node node = new Node();
        String name = xmlLine.substring(xmlLine.indexOf('<') + 1, xmlLine.indexOf("/>"));
        node.setName(name);
        setAttrs(node);
        StackDAO.addToChild(node);
        return node;
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
     * Parses xmlLine and creates the node Object
     * and put them to {@link StackDAO}
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
            if (patternMatch(SIMPLE_TAG, xmlLine)) {
                createNodeForEndTag(xmlLine);
            } else {
                Node node = StackDAO.popNode();
                if (!StackDAO.isEmpty()) {
                    Node parent = StackDAO.peekNode();
                    parent.addToChildNodeList(node);
                } else {
                    createDocument(node);
                }
            }

        } else if (patternMatch(SIMPLE_TAG, xmlLine)) {
            Node node = createNodeForSimpleTag(xmlLine);
            StackDAO.addNode(node);
        } else if(patternMatch(CLOSED_TAG,xmlLine)){
            createNodeForClosedTag(xmlLine);
        }
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
     * This method takes and split
     * in node object the name String and initializes the
     * Attributes in this object
     */
    private static void setAttrs(Node node) {
        String line = node.getName();
        if (line.contains(" ")) {
            String attrs = line.substring(line.indexOf(" "));
            String tagName = line.substring(0, line.indexOf(" "));
            node.setName(tagName);
            String[] attrArray = attrs.split("[\\s]+");
            for (int i = 0; i < attrArray.length; i++) {
                String[] splitValues = attrArray[i].split("=");
                if (splitValues.length >= 2) {
                    String nameAttr = attrArray[i].split("=")[0];
                    String valueAttr = attrArray[i].split("=")[1];
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
     *
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
