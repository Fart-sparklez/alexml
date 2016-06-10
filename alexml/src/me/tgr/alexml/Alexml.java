package me.tgr.alexml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gforcedev on 15/02/2016.
 * MainClass
 */
public class Alexml {
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

    public static HashMap<String, TextureAtlas> playerAtlasXml(String filename) throws NullPointerException {
        HashMap<String, TextureAtlas> atlases = new HashMap<String, TextureAtlas>();
        Document doc = getDocument(filename);
        Texture texture = new Texture(doc.getDocumentElement().getAttribute("path"));
        NodeList animations = doc.getElementsByTagName("animation");

        for (int anim = 0; anim < animations.getLength(); anim++) {
            if (animations.item(anim).getNodeType() == Node.ELEMENT_NODE) {
                Element thisAnimation = (Element) animations.item(anim);
                TextureAtlas atlas = new TextureAtlas();
                NodeList frames = thisAnimation.getElementsByTagName("animationFrame");

                for (int i = 0; i < frames.getLength(); i++) {
                    Node currentFrame = frames.item(i);
                    if (currentFrame.getNodeType() == Node.ELEMENT_NODE) {
                        Element eframe = (Element) currentFrame;

                        String x = eframe.getAttribute("x");
                        String y = eframe.getAttribute("y");
                        String width = eframe.getAttribute("width");
                        String height = eframe.getAttribute("height");

                        atlas.addRegion(Integer.toString(i), texture, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
                    }
                }

                atlases.put(thisAnimation.getAttribute("animationid"), atlas);
            }
        }
        return atlases;
    }

    public static HashMap<String, ArrayList<int[]>> playerPosXml(String filename) throws NullPointerException {
        Document doc = getDocument(filename);

        HashMap<String, ArrayList<int[]>> drawCoords = new HashMap<String, ArrayList<int[]>>();
        NodeList animations = doc.getElementsByTagName("animation");
        for (int anim = 0; anim < animations.getLength(); anim++) {
            if (animations.item(anim).getNodeType() == Node.ELEMENT_NODE) {
                Element thisAnimation = (Element) animations.item(anim);
                drawCoords.put(thisAnimation.getElementsByTagName("animationid").item(0).getTextContent(), new ArrayList<int[]>());
                NodeList frames = thisAnimation.getElementsByTagName("animationFrame");
                for (int i = 0; i < frames.getLength(); i++) {
                    Node currentFrame = frames.item(i);
                    if (currentFrame.getNodeType() == Node.ELEMENT_NODE) {
                        Element eframe = (Element) currentFrame;

                        String drawX = eframe.getAttribute("drawX");
                        String drawY = eframe.getAttribute("drawY");

                        int[] theseCoords = new int[2];
                        theseCoords[0] = Integer.parseInt(drawX);
                        theseCoords[1] = Integer.parseInt(drawY);

                        drawCoords.get(thisAnimation.getAttribute("id")).add(theseCoords);
                    }
                }
            }
        }
        return drawCoords;
    }
}
