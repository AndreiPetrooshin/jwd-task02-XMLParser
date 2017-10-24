package com.andreipetrushin.dao.impl;

import com.andreipetrushin.entity.impl.Node;
import com.andreipetrushin.dao.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The DocumentImpl class wich implements {@link Document}
 * interface and contains the rootNode object
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */

public class DocumentImpl implements Document {

    private Node rootNode;

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }


    /**
     * This method returns List of Nodes by
     * tagName if the tag name is not contained
     * returns empty list
     */
    public List<Node> getNodeByTagName(String tagName) {
        List<Node> nodeList = new ArrayList<>();
        Node node = searchInNodes(tagName, rootNode, nodeList);
        if(node !=null){
            nodeList.add(node);
        }
        return nodeList;

    }

    /**
     * Recursively searches the tag in the Node.
     *
     * @param tagName  - the Name of the Tag
     * @param root     - the Root element of Document
     * @param nodeList - the List for adding the Nodes
     * @return - Node can return null if tag doesn't exist
     */
    private Node searchInNodes(String tagName, Node root, List<Node> nodeList) {
        if (root.getName().equals(tagName)) {
            return root;
        }
        List<Node> list = root.getChildList();
        for (Node node : list) {
            if (node.getName().equals(tagName)) {
                nodeList.add(node);
                continue;
            }
            if (!node.getChildList().isEmpty()) {
                Node curr = searchInNodes(tagName, node, nodeList);
                if (curr != null) {
                    nodeList.add(curr);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DocumentImpl{" +
                "rootNode=" + rootNode +
                '}';
    }
}
