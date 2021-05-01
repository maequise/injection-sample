package com.maequise.context.parsers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public final class ContextParser {
    private static Document document;

    public ContextParser(String file){
        loadFile(file);
    }

    public ContextParser(File file){
        loadFile(file);
    }

    public ContextParser(InputStream inputStream){
        loadFile(inputStream);
    }


    static void loadFile(String filePath){
        loadFile(new File(filePath));
    }

    static void loadFile(File file){
        try {
            loadFile(new FileInputStream(file));
        }catch (FileNotFoundException e){
            throw new IllegalArgumentException("The file must exists", e);
        }
    }

    static void loadFile(InputStream inputStream){
        Document documentParsed = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //passing the XSD to validate the XML
            //@TODO activate the validation schema
            //factory.setSchema(SchemaFactory.newDefaultInstance().newSchema());

            documentParsed = factory.newDocumentBuilder().parse(inputStream);
        }catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        if(documentParsed != null){
            document = documentParsed;
        }
    }

    public Document getDocumentParsed(){
        return document;
    }

    public void clearDocument(){
        document = null;
    }
}
