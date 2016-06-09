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
	Label infos;
	float infoReset;


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
		infos = new Label("alexml", skin);
		infoReset = 0;


		openButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				try {
					sprite.remove();
					spritename = inputArea.getText();

					sprite = new AlexSprite(spritename);
					sprite.setAnimation(currentAnimation);
					stage.addActor(sprite);
				} catch (Exception ex) {
					infos.setText("Couldn't open file");
					infoReset = 0.8f;
				}
			}
		});
		setButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				try {
					currentAnimation = inputArea.getText();

					sprite.setAnimation(currentAnimation);
				} catch (Exception ex) {
					infos.setText("Animation does not exist");
					infoReset = 0.8f;
				}
			}
		});
		refreshButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				try {
					sprite.remove();
					sprite = new AlexSprite(spritename);
					sprite.setAnimation(currentAnimation);
					stage.addActor(sprite);
				} catch (Exception ex) {
					infos.setText("Did you delete the file?");
					infoReset = 0.8f;
				}
			}
		});

		vg.addActor(openButton);
		vg.addActor(setButton);
		vg.addActor(refreshButton);
		vg.addActor(inputArea);
		vg.addActor(infos);

		stage.addActor(vg);

		spritename = "sprite.xml";
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

		infoReset -= Gdx.graphics.getDeltaTime();
		if (infoReset < 0 && infos.getText().toString() != "alexml") {
			infos.setText("alexml");
		}
	}

	@Override
	public boolean handle(Event event) {
		return false;
	}
}
