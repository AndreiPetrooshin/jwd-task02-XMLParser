package com.andreipetrushin.entity.impl;


import com.andreipetrushin.entity.Element;

import java.io.Serializable;

/**The simple javaBean class
 * which stores the name and value
 * and implements the {@link Element} interface
 *
 * @author AndreiPetrushin
 * @version  1.0.0
 * */
public class Attr implements Element, Serializable {

    private String name;
    private String value;

    public Attr() {
    }

    public Attr(String name, String value) {
        this.name = name;
        this.value = value;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attr attr = (Attr) o;

        if (name != null ? !name.equals(attr.name) : attr.name != null) return false;
        return value != null ? value.equals(attr.value) : attr.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return name + "=" + value;
    }
}
