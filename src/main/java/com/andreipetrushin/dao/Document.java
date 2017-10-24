package com.andreipetrushin.dao;

import com.andreipetrushin.entity.impl.Node;

import java.util.List;

public interface Document {

    void setRootNode(Node node);
    List<Node> getNodeByTagName(String name);
    Node getRootNode();
}
