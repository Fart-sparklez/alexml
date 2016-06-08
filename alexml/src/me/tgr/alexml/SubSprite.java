package me.tgr.alexml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gforcedev on 07/03/2016.
 */
public class SubSprite extends Actor {

    private HashMap<String, TextureAtlas> atlases;
    private HashMap<String, Vector2[]> drawCoords;
    private String currentKey;
    private Animation currentAnimation;
    private float et;


    public SubSprite(Element sub, NodeList subAnimations) {
        atlases = new HashMap<String, TextureAtlas>();
        drawCoords = new HashMap<String, Vector2[]>();


        String thisPath = sub.getAttribute("path");

        Texture texture = new Texture(thisPath);

        for (int anim = 0; anim < subAnimations.getLength(); anim++) {
            if (subAnimations.item(anim).getNodeType() == Node.ELEMENT_NODE) {
                Element thisAnimation = (Element) subAnimations.item(anim);
                NodeList frames = thisAnimation.getElementsByTagName("frame");

                TextureAtlas atlas = new TextureAtlas();
                Vector2[] thisDrawCoords = new Vector2[frames.getLength()];


                for (int i = 0; i < frames.getLength(); i++) {
                    Node currentFrame = frames.item(i);
                    if (currentFrame.getNodeType() == Node.ELEMENT_NODE) {
                        Element eframe = (Element) currentFrame;

                        String x = eframe.getAttribute("x");
                        String y = eframe.getAttribute("y");
                        String width = eframe.getAttribute("width");
                        String height = eframe.getAttribute("height");
                        String drawX = eframe.getAttribute("drawx");
                        String drawY = eframe.getAttribute("drawy");

                        thisDrawCoords[i] = (new Vector2(Integer.parseInt(drawX), Integer.parseInt(drawY)));
                        atlas.addRegion(Integer.toString(i), texture, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
                    }
                }
                atlases.put(thisAnimation.getAttribute("id"), atlas);
                drawCoords.put(thisAnimation.getAttribute("id"), thisDrawCoords);
            }
        }
        setOrigin(0, 0);
        setScale(1, 1);
        setRotation(0);
    }



    public void setAnimation(String newKey) { //generates animation on the fly from the key loookup and the atlas
        currentKey = newKey;
        currentAnimation = new Animation(1 / 10f, atlases.get(currentKey).getRegions());
        currentAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta) {
    }


    @Override
    public void draw(Batch batch, float alpha) {
        et += Gdx.graphics.getDeltaTime(); //increment animation frame
        int thisFrameIndex = currentAnimation.getKeyFrameIndex(et); //get frame for setting draw coords
        TextureRegion thisFrame = currentAnimation.getKeyFrame(et, true);

        setX(drawCoords.get(currentKey)[thisFrameIndex].x * getScaleX()); //set local coords for inside the alexsprite
        setY(drawCoords.get(currentKey)[thisFrameIndex].y * getScaleY());

        setWidth(thisFrame.getRegionWidth());
        setHeight(thisFrame.getRegionHeight());

        batch.draw(currentAnimation.getKeyFrame(et, true), getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation()
        );
    }
}
