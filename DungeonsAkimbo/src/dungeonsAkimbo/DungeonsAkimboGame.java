package dungeonsAkimbo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;
import jig.ResourceManager;

public class DungeonsAkimboGame extends StateBasedGame {
	

	//Art Macros
	public static final String TEMP_PLAYER = "dungeonsAkimbo/resource/temp_player.png";
	public static final String TEMP_BULLET = "dungeonsAkimbo/resource/temp_bullet.png";

	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int PLAYTESTSTATE = 3;
	
	
	public static final String DA_TESTMAP_RSC = "/Resources/Maps/TestMap/DaTestMapSmall.tmx";
	public static final String DA_TESTMAP_TILESET_RSC = "/Resources/Maps/TestMap";
	
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .01;
	
	
	public final int screenWidth;
	public final int screenHeight;
	
	
	public Player player;
	
	private DaMap gameMap;
	private boolean mapReady = false;
	private TiledMap mapPlan;
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		addState(new PlayTestState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		
		ResourceManager.loadImage(TEMP_PLAYER);
		ResourceManager.loadImage(TEMP_BULLET);
		
		Entity.antiAliasing = false;
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);
		
		
		player = new Player(screenWidth / 2, screenHeight / 3);
		
	}
	
	public DungeonsAkimboGame(String title, int width, int height) {
		super(title);
		screenWidth = width;
		screenHeight = height;
	}

	
	
	public void loadNewTiledMap(int map) throws SlickException {
		if (map == 1) {
			mapPlan = new TiledMap(DA_TESTMAP_RSC, DA_TESTMAP_TILESET_RSC);
		}
		mapReady = true;
	}
	
	public void loadMap() {
		if(mapReady)
			gameMap = new DaMap(mapPlan);
	}
	
	public DaMap getCurrentMap() {
		return gameMap;		
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new DungeonsAkimboGame("Dungeons Akimbo " + VERSION, WIDTH, HEIGHT));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}

