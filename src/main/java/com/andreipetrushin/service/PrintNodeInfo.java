package com.andreipetrushin.service;

import com.andreipetrushin.entity.Element;
import com.andreipetrushin.entity.impl.Attr;
import com.andreipetrushin.entity.impl.Node;

import java.util.List;

/**
 * This class prints to console
 * the DOM of Node element
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public class PrintNodeInfo {

    public static void print(Node node) {
        if (node.getAttrList().isEmpty()) {
            System.out.print(String.format("<%s>", node.getName()));
            printNodeChilds(node);
            printCloseTag(node);
        } else {
            String attrLine = getAttributesInLine(node.getAttrList());
            System.out.print(String.format("<%s %s>",
                    node.getName(), attrLine));
            printNodeChilds(node);
            printCloseTag(node);
        }

    }

    private static String getAttributesInLine(List<? extends Element> list) {
        String line = null;
        for (Element attr : list) {
            Attr a1 = (Attr) attr;
            line += a1.toString();
        }
        return line;
    }

    private static void printCloseTag(Node node) {
        if (node.getValue() == null) {
            System.out.print(String.format("</%s>%n", node.getName()));
        } else {
            System.out.print(String.format("%s</%s>%n", node.getValue(), node.getName()));
        }
    }

    private static void printNodeChilds(Node node) {
        if (!node.getChildList().isEmpty()) {
            System.out.println();
            for (Element element : node.getChildList()) {
                Node n = (Node) element;
                print(n);
            }
        }
    }

}
