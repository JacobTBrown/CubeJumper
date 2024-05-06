package com.me.cubejumper.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseObject;

/**
 * A triangular shape which is deadly to the touch.
 * <p>
 * If the Player comes in contact, the Player dies.
 * 
 * @author Jacob
 */
public class Spikes extends BaseObject {
	private Sprite sprite;
	private Texture texture;
	private SpriteBatch batch;

	Vector2[] vertices = new Vector2[] { new Vector2(0, 0), new Vector2(5, 10),
			new Vector2(10, 0) };

	/**
	 * A static and non-moving Spike.
	 * 
	 * @param world
	 * @param x
	 *            - world x position
	 * @param y
	 *            - world y position
	 * 
	 * @author Jacob
	 */
	public Spikes(World world, float x, float y) {
		batch = new SpriteBatch();

		// Body definition
		initPoly(x, y);

		bodyDef.type = BodyType.StaticBody;

		// Spike points
		poly.set(vertices);

		// fixture definition
		fixDef.density = 1;
		fixDef.friction = 1;
		fixDef.restitution = 0;

		body = world.createBody(bodyDef);
		body.setUserData(2);
		body.createFixture(fixDef);

		texture = new Texture(Gdx.files.internal("ui/spike.png"));
		sprite = new Sprite(texture);
		sprite.setSize(10, 10);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	/**
	 * A dynamic Spike, which can move around.
	 * 
	 * @param world
	 * @param x
	 *            - world x position
	 * @param y
	 *            - world y position
	 * @param dens
	 *            - density
	 * @param frict
	 *            - friction
	 * @param rest
	 *            - restitution
	 * 
	 * @author Jacob
	 */
	public Spikes(World world, float x, float y, float dens, float frict,
			float rest) {
		batch = new SpriteBatch();

		// Body definition
		initPoly(x, y);

		bodyDef.type = BodyType.DynamicBody;

		// Player's rectangle
		poly.set(new Vector2[] { new Vector2(0, 0), new Vector2(5, 10),
				new Vector2(10, 0) });

		// fixture definition
		fixDef.density = dens;
		fixDef.friction = frict;
		fixDef.restitution = rest;

		body = world.createBody(bodyDef);
		body.setUserData(2);
		body.createFixture(fixDef);

		texture = new Texture(Gdx.files.internal("ui/spike.png"));
		sprite = new Sprite(texture);
		sprite.setSize(10, 10);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	public void render(Camera camera) {
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
				body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.draw(batch);
		batch.end();

		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}