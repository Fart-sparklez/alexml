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
 *
 * Subsprite takes the relevant subsprite element as it's constructor argument, parsing it and setting up all of the
 * animation things relating to it (lines 26-29)
 *
 * It also contains the setAnimation method, which actually generates the animation on the fly for that key,
 * slightly decreasing performance but freeing up lots more memory than statically storing every singly animation
 * used for that particular subsprite
 */
class SubSprite extends Actor {

    private final HashMap<String, TextureAtlas> atlases;
    private final HashMap<String, Vector2[]> drawCoords;
    private final HashMap<String, Float> speeds;
    private final ArrayList<String> keys;
    private String currentKey;

    private Animation currentAnimation;
    private float et;


    SubSprite(Element sub, NodeList subAnimations) {
        atlases = new HashMap<String, TextureAtlas>();
        drawCoords = new HashMap<String, Vector2[]>();
        speeds = new HashMap<String, Float>();
        keys = new ArrayList<String>();


        String thisPath = sub.getAttribute("path");

        Texture texture = new Texture(Gdx.files.internal(thisPath));

        for (int anim = 0; anim < subAnimations.getLength(); anim++) {
            if (subAnimations.item(anim).getNodeType() == Node.ELEMENT_NODE) {
                Element thisAnimation = (Element) subAnimations.item(anim);
                NodeList frames = thisAnimation.getElementsByTagName("frame");

                TextureAtlas atlas = new TextureAtlas();
                Vector2[] thisDrawCoords = new Vector2[frames.getLength()];

                //define speed here rather than line 100 so it is in the right scope for access later
                String speed = "0.1";
                for (int i = 0; i < frames.getLength(); i++) {
                    Node currentFrame = frames.item(i);
                    if (currentFrame.getNodeType() == Node.ELEMENT_NODE) {
                        Element eframe = (Element) currentFrame;


                        //If there is no value in the xml file, getAttribute returns a blank string.
                        //Therefore, if it does return a blank string, then we set it to a default value.
                        String x = eframe.getAttribute("x");
                        if (x.equals("")) {
                            x = "0";
                        }

                        String y = eframe.getAttribute("y");
                        if (y.equals("")) {
                            y = "0";
                        }

                        String width = eframe.getAttribute("width");
                        if (width.equals("")) {
                            width = "10";
                        }

                        String height = eframe.getAttribute("height");
                        if (height.equals("")) {
                            height = "10";
                        }

                        String drawX = eframe.getAttribute("drawx");
                        if (drawX.equals("")) {
                            drawX = "0";
                        }

                        String drawY = eframe.getAttribute("drawy");
                        if (drawY.equals("")) {
                            drawY = "0";
                        }

                        //No need to initialise speed;
                        speed = eframe.getAttribute("speed");
                        if (speed.equals("")) {
                            speed = "0.1";
                        }

                        thisDrawCoords[i] = (new Vector2(Integer.parseInt(drawX), Integer.parseInt(drawY)));
                        atlas.addRegion(Integer.toString(i), texture, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
                    }
                }
                keys.add(thisAnimation.getAttribute("id"));
                atlases.put(thisAnimation.getAttribute("id"), atlas);
                drawCoords.put(thisAnimation.getAttribute("id"), thisDrawCoords);
                speeds.put(thisAnimation.getAttribute("id"), Float.parseFloat(speed));
            }
        }
        setOrigin(0, 0);
        setScale(1, 1);
        setRotation(0);
    }


    void setAnimation(String newKey) { //generates animation on the fly from the key loookup and the atlas
        if (!keys.contains(newKey)) {
            throw new NullPointerException();
        }
        currentKey = newKey;
        currentAnimation = new Animation(speeds.get(currentKey), atlases.get(currentKey).getRegions());
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
