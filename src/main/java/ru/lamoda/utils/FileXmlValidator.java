package ru.lamoda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class FileXmlValidator {
    private static final Logger log = LoggerFactory.getLogger(FileXmlValidator.class);

    public static boolean isValid(InputStream inputStream) {
        if (inputStream == null) return false;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("File xml validator's configuration error ", e);
            return false;
        }
        try {
            dBuilder.parse(inputStream);
        } catch (SAXException e) {
            log.error("Xml is not valid ", e);
            return false;
        } catch (IOException e) {
            log.error("File error - ", e);
        }
        return true;
    }

}
