package com.andreipetrushin.main;


import com.andreipetrushin.dao.Document;
import com.andreipetrushin.service.DocumentBuilderFactory;
import com.andreipetrushin.entity.impl.Node;
import com.andreipetrushin.service.DocumentBuilder;
import com.andreipetrushin.service.PrintNodeInfo;
import com.andreipetrushin.service.exception.ServiceLayerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main  {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("./src/main/resources/task02.xml");
            Node node = document.getRootNode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Print tag product:");
            List<Node> nodes = document.getNodeByTagName(reader.readLine());
            for(Node n: nodes){
                PrintNodeInfo.print(n);
            }

        }catch (ServiceLayerException | IOException e) {
            e.printStackTrace();
        }
    }
}
