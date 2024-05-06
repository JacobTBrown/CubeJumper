package com.me.cubejumper.objects.powerups;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.bases.BasePowerUp;

public class PUSloMo extends BasePowerUp {
	private static final int VELOCITYIT = 8;
	private static final int POSITIONIT = 3;

	public static float slow = 120f;

	private static final float SLOWMOTION = 1 / 120f;

	@SuppressWarnings("unused")
	private Vector2 pos;

	/**
	 * The slo-mo power up, which slows down the timestep of the world to
	 * simulate slo-mo.
	 * 
	 * @param world
	 * @param x
	 *            - world x position
	 * @param y
	 *            - world y position
	 * 
	 * @author Jacob
	 */
	public PUSloMo(World world) {
		this.world = world;
	}

	public PUSloMo(World world, float x, float y) {
		this.world = world;

		init(x, y);

		body = world.createBody(bodyDef);
		body.setUserData(5);
		body.createFixture(fixDef);
	}

	@Override
	public void activate(float delta) {
		super.activate(delta);
		if (time <= WAIT_TIME)
			world.step(SLOWMOTION, VELOCITYIT, POSITIONIT);
		else
			BaseLevel.isSlowMotion = false;
	}

	@Override
	public void color(float x, float y, Color color, Camera cam) {
		super.color(x, y, color, cam);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}