package com.andreipetrushin.entity.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.andreipetrushin.entity.Element;

/**The simple javaBean class
 * which stores the name, value,
 * childList(the List with child Elements) and
 * attrList(the List with Attributes )
 * and implements the {@link Element} interface
 *
 * @author AndreiPetrushin
 * @version  1.0.0
 * */
public class Node implements Element, Serializable {


    private String name;
    private String value;
    private LinkedList<Element> childList = new LinkedList<>();
    private List<Element> attrList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<? extends Element> getChildList() {
        return childList;
    }

    public void addToChildNodeList(Node node) {
        childList.add(node);
    }

    public void setChildList(LinkedList<Element> childList) {
        this.childList = childList;
    }

    public List<Element> getAttrList() {
        return attrList;
    }

    public void addToAttrList(Attr attr) {
        attrList.add(attr);
    }

    public void setAttrList(List<Element> attrList) {
        this.attrList = attrList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (childList != null ? !childList.equals(node.childList) : node.childList != null) return false;
        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        if (value != null ? !value.equals(node.value) : node.value != null) return false;
        return attrList != null ? attrList.equals(node.attrList) : node.attrList == null;
    }

    @Override
    public int hashCode() {
        int result = childList != null ? childList.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (attrList != null ? attrList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "childList=" + childList +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", attrList=" + attrList +
                '}' + "\n";
    }
}
