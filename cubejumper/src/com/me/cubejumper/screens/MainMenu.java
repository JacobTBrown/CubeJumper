package com.me.cubejumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseScreen;
import com.me.cubejumper.levels.Level1;
import com.me.cubejumper.levels.Level2;
import com.me.cubejumper.levels.PlayScreen;
import com.me.cubejumper.levels.TestLevel;
import com.me.cubejumper.utilities.ActorAccessor;

/**
 * Creates a menu, with the title, and 2 buttons for starting the game and
 * exiting.
 * 
 * @author Jacob
 */
public class MainMenu extends BaseScreen {
	private Label version;
	private TextButton buttonPlay, buttonExit, buttonSettings,
			buttonLevelSelect;

	public MainMenu(CubeJumper game) {
		this.game = game;
	}

	@Override
	public void show() {
		super.show();

		// button instantiation
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.pad(5);
		buttonPlay.setColor(1, 1, 1, 0);
		buttonPlay.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//game.setScreen(new PlayScreen(game));
				game.setScreen(new Level2(game));
				return true;
			}
		});

		buttonSettings = new TextButton("Settings", skin);
		buttonSettings.pad(5);
		buttonSettings.setColor(1, 1, 1, 0);
		buttonSettings.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new SettingsScreen(game));
				return true;
			}
		});

		buttonLevelSelect = new TextButton("Level Select", skin);
		buttonLevelSelect.pad(5);
		buttonLevelSelect.setColor(1, 1, 1, 0);
		buttonLevelSelect.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new LevelScreen(game));
				return true;
			}
		});

		buttonExit = new TextButton("EXIT", skin);
		buttonExit.pad(5);
		buttonExit.setColor(1, 1, 1, 0);
		buttonExit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});

		heading = new Label(CubeJumper.TITLE, skin);
		heading.setFontScale(2.5f);
		version = new Label(CubeJumper.VERSION, skin);
		version.setFontScale(.5f);

		table.add(heading);
		table.getCell(heading).padBottom(75);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).padBottom(10);
		table.row();
		table.add(buttonLevelSelect);
		table.getCell(buttonLevelSelect).padBottom(10);
		table.row();
		table.add(buttonSettings);
		table.getCell(buttonSettings).padBottom(10);
		table.row();
		table.add(buttonExit);
		table.getCell(buttonExit).padLeft(25).padBottom(10);
		table.row();
		table.add(version);

		stage.addActor(table);

		// table fade in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tween);
		Tween.from(table, ActorAccessor.Y, .75f)
				.target(Gdx.graphics.getHeight() / 8).start(tween);

		// Timeline.createParallel().beginParallel()
		// .pushPause(1)
		// .push(Tween.from(buttonPlay, ActorAccessor.ALPHA, .5f).target(0))
		// .push(Tween.from(buttonPlay, ActorAccessor.X,
		// 1f).target(Gdx.graphics.getWidth() + 100)).end().start(tween);

		Timeline.createSequence()
				.beginParallel()
				.pushPause(1)
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .5f).target(1))
				.push(Tween.to(buttonPlay, ActorAccessor.X, 2f).target(
						Gdx.graphics.getWidth() / 2.76f)).end().start(tween);

		Timeline.createParallel()
				.beginParallel()
				.pushPause(3)
				.push(Tween.to(buttonLevelSelect, ActorAccessor.ALPHA, .5f)
						.target(1))
				.push(Tween.to(buttonLevelSelect, ActorAccessor.X, 2f).target(
						Gdx.graphics.getWidth() / 2.68f)).end().start(tween);

		Timeline.createParallel()
				.beginParallel()
				.pushPause(3)
				.push(Tween.to(buttonSettings, ActorAccessor.ALPHA, .5f)
						.target(1))
				.push(Tween.to(buttonSettings, ActorAccessor.X, 2f).target(
						Gdx.graphics.getWidth() / 2.60f)).end().start(tween);

		Timeline.createParallel()
				.beginParallel()
				.pushPause(3)
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .5f).target(1))
				.push(Tween.to(buttonExit, ActorAccessor.X, 2f).target(
						Gdx.graphics.getWidth() / 2.52f)).end().start(tween);

		// heading fade in
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
				.end().repeat(Tween.INFINITY, 0).start(tween);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.pause();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}