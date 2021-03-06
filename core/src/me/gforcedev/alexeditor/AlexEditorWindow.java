package me.gforcedev.alexeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    private final String labelDefault = "Alexml v0.4-beta.1";


    private AlexSprite sprite;

    @Override
    public void create() {
        //just openFile with sprite.remove and stage.addactor taken out
        spritename = "data/sprite.xml";

        sprite = new AlexSprite(spritename);
        currentAnimation = sprite.getAnimation();

        setupUI();
    }

    private void setupUI() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //make sure the sprite is still there after resize - where this function will be called
        stage.addActor(sprite);

        //skin and container table
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Table table = new Table().top().left();
        table.setFillParent(true);
        stage.addActor(table);

        //buttons
        TextButton openButton = new TextButton("Open File", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton setButton = new TextButton("Set Animation", skin);

        //button listeners
        openButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                openFile(inputArea.getText());
            }
        });
        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {setCurrentAnimation(inputArea.getText());
            }
        });
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                refresh();
            }
        });

        //input area and info text
        inputArea = new TextField("", skin);
        inputArea.setWidth(Gdx.graphics.getWidth());
        info = new Label(labelDefault, skin);
        infoReset = 0;

        //add everything to the table
        table.add(inputArea).row();
        table.add(openButton).row();
        table.add(setButton).row();
        table.add(refreshButton).row();
        table.add(info).row();

        //setup the zooming listener
        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (sprite.getScaleX() > 1 || amount < 0) {
                    sprite.setScale(sprite.getScaleX() - amount);
                }
                return false;
            }
        });
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        //info reset so error messages are only there a certain amout of time (usually set as 0.8 seconds)
        infoReset -= Gdx.graphics.getDeltaTime();
        if (infoReset < 0 && !info.getText().toString().equals(labelDefault)) {
            info.setText(labelDefault);
        }
    }

    @Override
    public void resize(int width, int height) {
        setupUI();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void openFile(String path) {
        try {
            sprite.remove();
            spritename = path;
            sprite = new AlexSprite(spritename);
            currentAnimation = sprite.getAnimation();
            stage.addActor(sprite);
            sprite.setZIndex(0);
        } catch (Exception ex) {
            info.setText(ex.getMessage());
            infoReset = 0.8f;
        }
    }

    private void refresh() {
        try {
            sprite.remove();
            sprite = new AlexSprite(spritename);
            sprite.setAnimation(currentAnimation);
            stage.addActor(sprite);
            sprite.setZIndex(0);
        } catch (Exception ex) {
            info.setText(ex.getMessage());
            infoReset = 0.8f;
        }
    }

    private void setCurrentAnimation(String key) {
        try {
            currentAnimation = key;

            sprite.setAnimation(currentAnimation);
        } catch (Exception ex) {
            info.setText(ex.getMessage());
            infoReset = 0.8f;
        }
    }
}
