package me.tgr.ygd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import me.tgr.ygd.screens.GameScreen;

public class YgdMain extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getScreen().render(Gdx.graphics.getDeltaTime());
	}
}
