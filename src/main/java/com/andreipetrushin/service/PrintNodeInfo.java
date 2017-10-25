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

    private static StringBuilder indent = new StringBuilder();

    public static void print(Node node) {
        if (node.getAttrList().isEmpty()) {
            System.out.print(String.format("%n%s%s ",indent, node.getName()));
            printNodeChilds(node);
            printNodeValue(node);
        } else {
            String attrLine = createAttrLine(node.getAttrList());
            System.out.print(String.format("%n%s%s (%s): ",
                    indent,node.getName(), attrLine));
            printNodeChilds(node);
            printNodeValue(node);
        }

    }

    private static String createAttrLine(List<? extends Element> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element attr : list) {
            stringBuilder.append(" ");
            Attr a1 = (Attr) attr;
            stringBuilder.append(a1.toString()).append(" ");
        }
        return String.valueOf(stringBuilder);
    }

    private static void printNodeValue(Node node) {
        if (node.getValue().isEmpty()) {
            System.out.println();
        } else {
            System.out.print(String.format("- %s", node.getValue()));
        }
    }

    private static void printNodeChilds(Node node) {
        if (!node.getChildList().isEmpty()) {
            for (Node element : node.getChildList()) {
                indent.append("-");
                print(element);
                indent.delete(indent.length() - 1, indent.length());
            }
        }
    }

}

