package me.tgr.alexml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;


/**
 * Created by gforcedev on 12/04/2016.
 */
public class AlexSprite extends Group {
    private ArrayList<SubSprite> subSprites;

    private String currentKey;

    public AlexSprite(String path) {
        subSprites = new ArrayList<SubSprite>();

        Document doc = Alexml.getDocument(path);

        {
            int length = doc.getDocumentElement().getElementsByTagName("subsprite").getLength();
            NodeList subsprites = doc.getDocumentElement().getElementsByTagName("subsprite");

            for (int i = 0; i < length; i++) {
                Element sub = (Element) subsprites.item(i);

                subSprites.add(new SubSprite(sub, sub.getElementsByTagName("animation")));
            }
        }

        for (SubSprite sub : subSprites) {
            addActor(sub);
        }
    }

    public void setAnimation(String id) {
        for (SubSprite sub : subSprites) {
            sub.setAnimation(id);
        }
    }

    public String getAnimation() {
        return currentKey;
    }
}
