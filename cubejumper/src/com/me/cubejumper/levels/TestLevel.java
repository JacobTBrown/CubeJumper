package com.me.cubejumper.levels;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.bases.BaseLevelPU;
import com.me.cubejumper.objects.powerups.PUSloMo;

public class TestLevel extends BaseLevelPU{
	private PUSloMo sloMo;
	private RayHandler handler;
	private static ConeLight light;
	
	public TestLevel(CubeJumper game){
		this.game = game;
	}

	@Override
	public void show() {
		super.show();
		
		handler = new RayHandler(world);
		
		sloMo = new PUSloMo(world, 75, 37.5f);

		light = new ConeLight(handler, 500, Color.WHITE, 500, 0, 0, 270, 60);
		light.setSoft(true);
		light.setSoftnessLength(100f);
		
		genCubesSW(3, 50, 11.6f, 0);
		genCubesSW(6, 60, 11.6f, 3);
		genCubesSW(9, 70, 11.6f, 6);
		genCubesSW(12, 80, 11.6f, 9);
		genCubesSW(15, 90, 11.6f, 12);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		light.setPosition(player.getPosition().x,
				((int) player.getPosition().y) + 50);

		sloMo.color(75, 37.5f, Color.BLUE, camera);
		
		handler.updateAndRender();
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(.1f);

		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void pause() {
		super.pause();

		CubeJumper.currentLevel = 3;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
		
		handler.dispose();
	}
	
}