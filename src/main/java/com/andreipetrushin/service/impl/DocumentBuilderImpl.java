package com.andreipetrushin.service.impl;

import com.andreipetrushin.dao.Document;
import com.andreipetrushin.dao.exception.DAOLayerException;
import com.andreipetrushin.service.DocumentBuilder;
import com.andreipetrushin.dao.ParseDAO;
import com.andreipetrushin.service.exception.ServiceLayerException;

import java.io.*;

/**
 * The singleton class wich implements {@link DocumentBuilder} interface
 * Parses the file by path
 * Used the ParseDAO {@link ParseDAO}
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public class DocumentBuilderImpl implements DocumentBuilder {


    private static DocumentBuilderImpl ourInstance = new DocumentBuilderImpl();

    public static DocumentBuilderImpl getInstance() {
        return ourInstance;
    }

    private DocumentBuilderImpl() {
    }

    /**
     * Calls the parseXML method from {@link ParseDAO}
     * @param path - Path to file
     * @return Document
     * @throws ServiceLayerException
     */
    public Document parse(String path) throws ServiceLayerException {
        try {
            return ParseDAO.parseXML(path);
        } catch (DAOLayerException e) {
            throw new ServiceLayerException(e.fillInStackTrace());
        }
    }


}
