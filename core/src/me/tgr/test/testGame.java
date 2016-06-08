package me.tgr.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.tgr.alexml.AlexSprite;


public class testGame extends ApplicationAdapter implements EventListener {
	private Stage stage;
	private Skin skin;
	String spritename;
	String currentAnimation;
	//ui elements
	TextButton openButton;
	TextButton setButton;
	TextButton refreshButton;

	TextArea inputArea;


	private AlexSprite sprite;
	
	@Override
	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		//ui
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();
		//just the top fith
		vg.setBounds(20, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);

		openButton = new TextButton("Open File", skin);
		refreshButton = new TextButton("Refresh", skin);
		setButton = new TextButton("Set Animation", skin);

		inputArea = new TextArea("", skin);


		openButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				sprite.remove();
				spritename = inputArea.getText();

				sprite = new AlexSprite(spritename);
			}
		});
		setButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				currentAnimation = inputArea.getText();

				sprite.setAnimation(currentAnimation);
			}
		});
		refreshButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				sprite.remove();
				sprite = new AlexSprite(spritename);
				sprite.setAnimation(currentAnimation);
				stage.addActor(sprite);
			}
		});

		vg.addActor(openButton);
		vg.addActor(setButton);
		vg.addActor(refreshButton);
		vg.addActor(inputArea);

		stage.addActor(vg);

		spritename = "c:/artz/sprite.xml";
		sprite = new AlexSprite(spritename);

		currentAnimation = "test";
		sprite.setAnimation(currentAnimation);
		stage.addActor(sprite);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public boolean handle(Event event) {
		return false;
	}
}
