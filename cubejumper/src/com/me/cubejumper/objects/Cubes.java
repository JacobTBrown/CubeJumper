package com.me.cubejumper.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseObject;

/**
 * Cubes, for placing spikes on or being used as platforms.
 * 
 * @author Jacob
 */
public class Cubes extends BaseObject {
	private Sprite sprite;
	private Texture texture;
	private SpriteBatch batch;

	/**
	 * A static and non-moving Cube.
	 * 
	 * @param world
	 * @param x
	 *            - world x position
	 * @param y
	 *            - world y position
	 * 
	 * @author Jacob
	 */
	public Cubes(World world, float x, float y) {
		GLTexture.setEnforcePotImages(false);
		
		batch = new SpriteBatch();

		initPoly(x, y);

		// Rectangle
		// shape.setAsBox(5, 5);
		poly.set(new Vector2[] { new Vector2(0, 0), new Vector2(0, 10),
				new Vector2(10, 0), new Vector2(10, 10) });

		// Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;

		body = world.createBody(bodyDef);
		body.setUserData(4);
		body.createFixture(fixDef);

		texture = new Texture(Gdx.files.internal("ui/cube.png"));
		sprite = new Sprite(texture);
		sprite.setSize(10, 10);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	public void render(Camera camera) {
		Vector2 bodyPos = body.getPosition();
		
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		sprite.setPosition(bodyPos.x - sprite.getWidth() / 2,
				bodyPos.y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}