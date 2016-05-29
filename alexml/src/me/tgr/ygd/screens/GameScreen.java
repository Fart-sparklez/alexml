package me.tgr.ygd.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import me.tgr.alexml.AlexSprite;

/**
 * Created by gforcedev on 07/04/2016.
 */
public class GameScreen implements Screen {

    private Stage stage;

    private AlexSprite testa;

    @Override
    public void show() {
        stage = new Stage();
        testa = new AlexSprite("ray95.xml");
        testa.setAnimation("still");
        testa.setScale(2, 2);

        stage.addActor(testa);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
