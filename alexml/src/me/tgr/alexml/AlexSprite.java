package me.tgr.alexml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;


/**
 * Created by gforcedev on 12/04/2016.
 *
 * AlexSprite houses the subsprites within it. At some point, this class will need a slight rework to be able to only
 * draw subsprites which are part of the current animation, as part of the upcoming change of not every subsprite needing
 * to have every animation.
 */
public class AlexSprite extends Group {
    private final ArrayList<SubSprite> subSprites;
    private final ArrayList<String> keys;
    private String currentKey;
    private Texture texture;

    public AlexSprite(String path) {
        subSprites = new ArrayList<SubSprite>();
        keys = new ArrayList<String>();
        String texturePath;

        if (path.contains("\\")) {
            String[] folders = path.split("\\\\");
            texturePath = "";
            for (int i = 0; i < folders.length - 1; i++) {
                texturePath += folders[i];
                texturePath += "\\";
            }
        } else {
            String[] folders = path.split("/");
            texturePath = "";
            for (int i = 0; i < folders.length - 1; i++) {
                texturePath += folders[i];
                texturePath += "/";
            }
        }

        Document doc = Alexml.getDocument(path);

        {
            //noinspection ConstantConditions
            //this is an api, if their document is wrong its their fault
            int length = doc.getDocumentElement().getElementsByTagName("subsprite").getLength();
            NodeList subsprites = doc.getDocumentElement().getElementsByTagName("subsprite");

            for (int i = 0; i < length; i++) {
                Element sub = (Element) subsprites.item(i);

                subSprites.add(new SubSprite(sub, sub.getElementsByTagName("animation"), texturePath));
            }
        }

        for (SubSprite sub : subSprites) {
            addActor(sub);

            for (String key : sub.getKeys()) {
                addAnimation(key);
            }
        }
        setAnimation(keys.get(0));
    }

    public void setAnimation(String id) {
        for (SubSprite sub : subSprites) {
            sub.setAnimation(id);
        }
        currentKey = id;
    }

    public String getAnimation() {
        return currentKey;
    }

    protected void addAnimation(String id) {
        if (!keys.contains(id)) {
            keys.add(id);
        }
    }
}
