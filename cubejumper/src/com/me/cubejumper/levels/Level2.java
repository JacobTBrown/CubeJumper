package com.me.cubejumper.levels;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.FinishFlag;

/**
 * Level 2 - climb the wall.
 * 
 * @author Jacob
 */
public class Level2 extends BaseLevel {
	private RayHandler handler;
	private static ConeLight light;

	public Level2(CubeJumper game) {
		this.game = game;
	}

	public Level2(CubeJumper game, int x) {
		super.show();
	}

	@Override
	public void show() {
		super.show();

		handler = new RayHandler(world);

		light = new ConeLight(handler, 500, Color.WHITE, 500, 0, 0, 270, 60);
		light.setSoft(true);
		light.setSoftnessLength(100f);

		genCubesUP(10, 50, 1.5f, 0);

		new FinishFlag(world, 50, 1.5f);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		world.step(TIMESTEP, VELOCITYIT, POSITIONIT);

		light.setPosition(player.getPosition().x,
				((int) player.getPosition().y) + 50);

		handler.updateAndRender();
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(.1f);

		batch.begin();
		stage.draw();
		batch.end();
	}

	public static Color getLightColor() {
		return light.getColor();
	}

	public static void setLightColor(Color color) {
		light.setColor(color);
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

//		ifPause = true;
		CubeJumper.currentLevel = 1;
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();

		handler.dispose();
	}
}