package com.andreipetrushin.dao;

import com.andreipetrushin.entity.impl.Node;

import java.util.LinkedList;

/**
 * This class implements the LIFO Queue
 * with static methods and used only in ParseDAO
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public final class StackDAO {

    private StackDAO() {

    }

    private static LinkedList<Node> list = new LinkedList<>();

    public static boolean addNode(Node node) {
        list.push(node);
        return true;
    }

    public static Node peekNode() {
        return list.peek();
    }

    public static Node popNode() {

        return list.pop();
    }

    public static boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Takes the First Node object from list and adds the
     * child in to him
     */
    public static boolean addToChild(Node node) {
        Node parent = peekNode();
        if (!isEmpty() && parent != null) {
            parent.addToChildNodeList(node);
            return true;
        }
        return false;

    }


}
