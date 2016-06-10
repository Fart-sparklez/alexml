package me.tgr.alexml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Gforcedev on 15/02/2016.
 *
 * Useful Methods used by AlexSprite and SubSprite
 */
class Alexml {
    public static Document getDocument(String filename) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(filename);
        } catch (Exception e) {
            System.out.println("Error reading configuration file: \n");
            System.out.println(e.getMessage());
        }
        return null;
    }

}
