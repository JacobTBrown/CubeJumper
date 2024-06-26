package com.me.cubejumper.objects.powerups;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.Player;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.bases.BasePowerUp;

/**
 * The super jump power up, which just about doubles the jump height.
 * 
 * @param world
 * @param x
 *            - world x position
 * @param y
 *            - world y position
 * 
 * @author Jacob
 */
public class PUSuperJump extends BasePowerUp {
	public PUSuperJump(World world) {
		this.world = world;
	}

	/**
	 * The super jump power up, which just about doubles the jump height.
	 * 
	 * @param world
	 * @param x
	 *            - world x position
	 * @param y
	 *            - world y position
	 * 
	 * @author Jacob
	 */
	public PUSuperJump(World world, float x, float y) {
		this.world = world;

		init(x, y);

		body = world.createBody(bodyDef);
		body.setUserData(6);
		body.createFixture(fixDef);
	}

	@Override
	public void activate(float delta) {
		super.activate(delta);
		if (BasePowerUp.count <= 1) {
			Player.superJump = 2;
			Player.yLimit = 100;
		} else {
			BaseLevel.isSuperJump = false;
		}
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