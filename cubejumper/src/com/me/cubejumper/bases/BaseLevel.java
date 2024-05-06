package com.me.cubejumper.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.Player;
import com.me.cubejumper.levels.Level1;
import com.me.cubejumper.objects.Cubes;
import com.me.cubejumper.objects.Spikes;
import com.me.cubejumper.screens.MainMenu;
import com.me.cubejumper.utilities.ContactHandler;
import com.me.cubejumper.utilities.InputHandler;

/**
 * Basis for all level classes, it contains:
 * <p>
 * 
 * @param Player player
 * @param ContactHandler conHandler
 * @param World world
 * @param OrthographicCamera camera<p>
 * @param TIMESTEP (float) - (1 / 60f)
 * @param VELOCITYIT (int) - 8
 * @param POSITIONIT (int) - 3
 * @param startTime - beginning of the highscore count
 * @param endTime - end of the highscore count
 * @param highScore - takes (endTime - startTime)<p>
 * 
 * @author Jacob
 */
public class BaseLevel implements Screen {
	protected static final float TIMESTEP = 1 / 60f;
	protected static final int VELOCITYIT = 8;
	protected static final int POSITIONIT = 3;
	private static final int TEN = 10;

	// private float currentBgx;
	// private long lastTimeBg;

	public int width, height;
	public static float startTime, endTime;
	public static float highScore = 0;
	public static boolean isPaused = false;
	public static boolean isSlowMotion = false;
	public static boolean isSuperJump = false;
	public static boolean playing = true;
	protected static boolean ifPause = false;
	protected Spikes[] spikeArray = new Spikes[100];
	protected Cubes[] cubeArray = new Cubes[100];
	protected int spikePos = 0;
	protected int cubePos = 0;

	protected CubeJumper game;

	protected Player player;
	
	private ContactHandler conHandler;
	private InputHandler inputHandler;

	protected World world;

	private Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	public ShapeRenderer shapeRender;

	private Body groundBody;
	private BodyDef groundBodyDef;
	private PolygonShape groundBox;

	protected SpriteBatch batch;
	protected ButtonStyle buttonStyle, buttonStyle2;
	protected Button pauseButton;
	protected TextButton mainButton, restartButton, exitButton;
	protected Stage stage;
	protected TextureAtlas atlas;
	protected Texture background;
	protected Sprite bg;
	protected Skin skin;
	protected Table table;
	protected LabelStyle headingStyle;
	protected BitmapFont white, black;
	protected Label heading;
	protected InputMultiplexer multiIn;
	private TextureAtlas atlas2;
	private Skin skin2;

	float x, y;
	int renderTime = 0;

	public BaseLevel() {
	}

	public BaseLevel(int n) {
		resume();
	}

	@Override
	public void show() {
		width = Gdx.graphics.getWidth() / 5;
		height = Gdx.graphics.getHeight() / 5;

		isSlowMotion = false;
		isSuperJump = false;
		BasePowerUp.count = 0;

		batch = new SpriteBatch();
		// background = new Texture(Gdx.files.internal("ui/background.png"));
		// bg = new Sprite(background);
		atlas = new TextureAtlas("ui/menuatlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

		atlas2 = new TextureAtlas("ui/pausebutton.pack");
		skin2 = new Skin();
		skin2.addRegions(atlas2);

		// currentBgx = 800;
		// lastTimeBg = TimeUtils.nanoTime();

		stage = new Stage();

		table = new Table(skin);
		table.setBounds(0, 0, width, height);
		table.setFillParent(true);

		// buttons
		buttonStyle = new ButtonStyle();
		buttonStyle.up = skin2.getDrawable("uiskin_button_up");
		buttonStyle.down = skin2.getDrawable("uiskin_button_down");
		buttonStyle.over = skin2.getDrawable("uiskin_button_down");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		pauseButton = new Button(buttonStyle);
		pauseButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (ifPause)
					resume();
				else
					pause();
				return true;
			}
		});
		pauseButton.pad(1);

		mainButton = new TextButton("Main Menu", skin);
		mainButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				return true;
			}
		});
		mainButton.pad(5);
		mainButton.setVisible(false);

		restartButton = new TextButton("Restart", skin);
		restartButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// CubeJumper.levelSel();
				player.setPositionAndAngVelocity(new Vector2(-50, 10), 0);
				player.setVelocity(new Vector2(0, 0));
				Player.setCanJump("true");
//				Level1.setLightColor(Color.WHITE);
				return true;
			}
		});
		restartButton.pad(5);
		restartButton.setVisible(false);

		exitButton = new TextButton("Exit", skin);
		exitButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});
		exitButton.pad(5);
		exitButton.setVisible(false);

		// The Labels
		heading = new Label("PAUSED", skin);
		heading.setFontScale(2f);
		heading.setVisible(false);

		table.add(heading).expandY().row();
		table.getCell(heading).padTop(65);
		table.add(mainButton).pad(3).row();
		table.add(restartButton).pad(3).row();
		table.add(exitButton).pad(3).row();
		table.add(pauseButton).expand().bottom().left();
		table.getCell(pauseButton).padLeft(25).padBottom(25);

		stage.addActor(table);

		camera = new OrthographicCamera(width, height);

		world = new World(new Vector2(15f, -100f), true);
		debugRenderer = new Box2DDebugRenderer();
		conHandler = new ContactHandler(game, world);

		player = new Player(world);
		inputHandler = new InputHandler(world, player);

		multiIn = new InputMultiplexer();
		multiIn.addProcessor(stage);
		multiIn.addProcessor(inputHandler);
		Gdx.input.setInputProcessor(multiIn);

		world.setContactListener(conHandler);

		startTime = System.nanoTime();

		groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0, 1);

		groundBody = world.createBody(groundBodyDef);
		groundBody.setUserData(0);

		groundBox = new PolygonShape();
		groundBox.setAsBox((camera.viewportWidth) * 100, .5f);

		groundBody.createFixture(groundBox, 0.0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// if(TimeUtils.nanoTime() - lastTimeBg > 100000000){
		// // move the separator 50px
		// currentBgx -= 50;
		// // set the current time to lastTimeBg
		// lastTimeBg = TimeUtils.nanoTime();
		// }
		
		camera.update();
		
		player.render(camera, delta);

		stage.act(delta);

		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		// camera.viewportHeight = height;
		// camera.viewportWidth = width;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
		ifPause = true;
		heading.setVisible(true);
		mainButton.setVisible(true);
		restartButton.setVisible(true);
		exitButton.setVisible(true);

		// new FileManager("savedata", "savedata/pause.json"
		// , "savedata/encrypted.json", 0);
		// FileManager.deleteFile("savedata/pause.json");

		// game.setScreen(new PauseScreen(game));
	}

	@Override
	public void resume() {
		ifPause = false;
		heading.setVisible(false);
		mainButton.setVisible(false);
		restartButton.setVisible(false);
		exitButton.setVisible(false);
	}

	@Override
	public void dispose() {
		groundBox.dispose();
		debugRenderer.dispose();
		world.dispose();
		for (int x = 0; x < 100; x++) {
			spikeArray[x].dispose();
		}
		for (int x = 0; x < 100; x++) {
			cubeArray[x].dispose();
		}
	}

	/**
	 * @param times - number of times the loop will run
	 * @param offset - starting x position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop<p>
	 * 
	 * @author Jacob
	 */
	public void genSpikesSW(int times, int offset, float y, int pos) {
		for (int x = 0 + pos; x < times; x++, spikePos++) {
			spikeArray[spikePos] = new Spikes(world, (x * TEN) + offset, y);
		}
	}

	/**
	 * @param times - number of times the loop will run
	 * @param x - x position
	 * @param offset - starting y position in the world
	 * @param pos - position in the array to start the loop<p>
	 * 
	 * @author Jacob
	 */
	public void genSpikesUP(int times, int x, float offset, int pos) {
		for (int y = 0 + pos; y < times; y++, spikePos++) {
			spikeArray[spikePos] = new Spikes(world, x, (y * TEN) + offset);
		}
	}

	/**
	 * @param times - number of times the loop will run
	 * @param offset - starting x position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop<p>
	 * 
	 * @author Jacob
	 */
	public void genCubesSW(int times, int offset, float y, int pos) {
		for (int x = 0 + pos; x < times; x++, cubePos++) {
			cubeArray[cubePos] = new Cubes(world, (x * TEN) + offset, y);
		}
	}

	/**
	 * @param times - number of times the loop will run
	 * @param x - x position
	 * @param offset - starting y position in the world
	 * @param pos - position in the array to start the loop<p>
	 * 
	 * @author Jacob
	 */
	public void genCubesUP(int times, int x, float offset, int pos) {
		for (int y = 0 + pos; y < times; y++, cubePos++) {
			cubeArray[cubePos] = new Cubes(world, x, (y * TEN) + offset);
		}
	}
}