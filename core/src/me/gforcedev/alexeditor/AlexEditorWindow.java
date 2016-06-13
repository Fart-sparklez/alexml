package me.gforcedev.alexeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.tgr.alexml.AlexSprite;


public class AlexEditorWindow extends ApplicationAdapter {
    private Stage stage;
    private String spritename;
    private String currentAnimation;

    private TextField inputArea;
    private Label info;
    private float infoReset;


    private AlexSprite sprite;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //ui
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();
        //just the top fifth
        vg.setBounds(20, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);

        TextButton openButton = new TextButton("Open File", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton setButton = new TextButton("Set Animation", skin);

        inputArea = new TextField("", skin);
        info = new Label("alexml", skin);
        infoReset = 0;


        openButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                try {
                    sprite.remove();
                    spritename = inputArea.getText();

                    sprite = new AlexSprite(spritename);
                    sprite.setAnimation(currentAnimation);
                    stage.addActor(sprite);
                } catch (Exception ex) {
                    info.setText(ex.getMessage());
                    infoReset = 0.8f;
                }
            }
        });
        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                try {
                    currentAnimation = inputArea.getText();

                    sprite.setAnimation(currentAnimation);
                } catch (Exception ex) {
                    info.setText(ex.getMessage());
                    infoReset = 0.8f;
                }
            }
        });
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                try {
                    sprite.remove();
                    sprite = new AlexSprite(spritename);
                    sprite.setAnimation(currentAnimation);
                    stage.addActor(sprite);
                } catch (Exception ex) {
                    info.setText(ex.getMessage());
                    infoReset = 0.8f;
                }
            }
        });

        vg.addActor(openButton);
        vg.addActor(setButton);
        vg.addActor(refreshButton);
        vg.addActor(inputArea);
        vg.addActor(info);

        stage.addActor(vg);

        spritename = "data/sprite.xml";
        sprite = new AlexSprite(spritename);

        currentAnimation = "test";
        sprite.setAnimation(currentAnimation);
        stage.addActor(sprite);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        infoReset -= Gdx.graphics.getDeltaTime();
        if (infoReset < 0 && !info.getText().toString().equals("alexml")) {
            info.setText("alexml");
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
