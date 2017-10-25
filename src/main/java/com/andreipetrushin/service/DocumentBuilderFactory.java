package com.andreipetrushin.service;

import com.andreipetrushin.service.impl.DocumentBuilderImpl;

/**
 * The singleton class which
 * returns the DocumentBuilder instance
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */

public class DocumentBuilderFactory {
    private static DocumentBuilderFactory instance = new DocumentBuilderFactory();

    private DocumentBuilderFactory() {

    }

    public static DocumentBuilderFactory newInstance() {
        return instance;
    }


    public DocumentBuilderImpl newDocumentBuilder() {
        return DocumentBuilderImpl.getInstance();
    }
}
