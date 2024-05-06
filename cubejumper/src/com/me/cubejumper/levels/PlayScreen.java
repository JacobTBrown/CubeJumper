package com.me.cubejumper.levels;

import java.util.ArrayList;
import java.util.Random;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.Spikes;

/**
 * Currently just level 2, just miss-named at the moment.
 * 
 * @author Jacob
 */
public class PlayScreen extends BaseLevel {
	private static final int TEN = 10;

	ArrayList<Spikes> spikes = new ArrayList<Spikes>();
	Random random;
	
	private RayHandler handler;
	private static ConeLight light;

	public PlayScreen(CubeJumper game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		light.setPosition(player.getPosition().x,
				((int) player.getPosition().y) + 50);

		handler.updateAndRender();
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(.1f);

		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {
		super.show();
		
		handler = new RayHandler(world);
		
		light = new ConeLight(handler, 500, Color.WHITE, 500, 0, 0, 270, 60);
		light.setSoft(true);
		light.setSoftnessLength(100f);

		random = new Random();

		for (int x = 0, y = 0, z = 0, offset = 0; x < 100; x++, y++) {
			int xPosi;
			if (y == 4) {
				z = TEN * random.nextInt(5) + 20; // 30
				if (z == 0)
					z = TEN;

				xPosi = x * TEN + z;

				if (xPosi < offset) {// x = 4, 70
					z = 0;
				} else {
					offset = z;
				}
				y = 0;
			} else {
				z = 0;
				xPosi = x * TEN;
			}

			spikeArray[x] = new Spikes(world, xPosi + offset, 1.5f);
		}
	}

	public World getWorld() {
		return world;
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
		super.resume();

		CubeJumper.currentLevel = 2;
	}

	@Override
	public void dispose() {
		super.dispose();

		for (int x = 0; x < 100; x++) {
			spikeArray[x].dispose();
		}
		handler.dispose();
	}
}