package dungeonsAkimbo;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;
import jig.ResourceManager;

public class DungeonsAkimboGame extends StateBasedGame {
	

	//Art Macros
	public static final String TEMP_PLAYER = "dungeonsAkimbo/resource/Projectiles/temp_player.png";
	public static final String TEMP_BULLET = "dungeonsAkimbo/resource/Projectiles/temp_bullet.png";
	public static final String MOB_ONE = "dungeonsAkimbo/resource/Mobs/Spoopy.png";
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int PLAYTESTSTATE = 3;
	
	
	public static final String DA_TESTMAP_RSC = "dungeonsAkimbo/resource/Maps/testMap/DaTestMapSmall.tmx";
	public static final String DA_TESTMAP_TILESET_RSC = "dungeonsAkimbo/resource/Maps/testmap/";
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .01;
	
	
	public final int screenWidth;
	public final int screenHeight;
	
	private Thread server, client;
	
	public Player player;
	
	
	
	private DaMap gameMap;
	private boolean mapReady = false;
	private TiledMap mapPlan;
	
	// Keep track of mobs
	public ArrayList<DaMob> mobs;
	
	ArrayList<Projectile> player_bullets;
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		addState(new PlayTestState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		
		ResourceManager.loadImage(TEMP_PLAYER);
		ResourceManager.loadImage(TEMP_BULLET);
		
		// Load images for mobs
		ResourceManager.loadImage(MOB_ONE);
		
		Entity.antiAliasing = false;
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);
		
		player_bullets = new ArrayList<Projectile>();
		
		player = new Player(screenWidth / 2, screenHeight / 3);
		
		// Initialize mobs (currently start with one mob)
		mobs = new ArrayList<DaMob>();
		mobs.add(new DaMob(screenWidth / 2, screenHeight / 2, 0, true));
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
	
	public void startServer() {
		server = new DaServer(8989);		
	}
	
	public void startClient() {
		client = new DaClient("DaUser","localhost", 8989);		
	}
	
	public DaClient getClient() {
		return (DaClient) client;
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

