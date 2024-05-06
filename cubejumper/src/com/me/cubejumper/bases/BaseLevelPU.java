package com.me.cubejumper.bases;

import com.me.cubejumper.Player;
import com.me.cubejumper.objects.powerups.PUSloMo;
import com.me.cubejumper.objects.powerups.PUSuperJump;

/**
 * Basis for levels with PowerUps, it contains:
 * <p>
 * 
 * @author Jacob
 */
public class BaseLevelPU extends BaseLevel {
	PUSloMo sloMo;
	PUSuperJump spJump;

	public BaseLevelPU() {
	}

	@Override
	public void show() {
		super.show();

		sloMo = new PUSloMo(world);
		spJump = new PUSuperJump(world);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (isSlowMotion) {
			if (ifPause)
				world.step(0, VELOCITYIT, POSITIONIT);
			else
				sloMo.activate(delta);
		} else {
			if (ifPause)
				world.step(0, VELOCITYIT, POSITIONIT);
			else
				world.step(TIMESTEP, VELOCITYIT, POSITIONIT);
		}

		if (isSuperJump) {
			spJump.activate(delta);
		} else {
			Player.superJump = 1;
			Player.yLimit = 55;
			BasePowerUp.count = 0;
		}
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
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
