package dungeonsAkimbo;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.map.DaMap;
import dungeonsAkimbo.netcode.DaClient;
import dungeonsAkimbo.netcode.DaServer;
import dungeonsAkimbo.states.MainMenuState;
import dungeonsAkimbo.states.PlayTestState;
import dungeonsAkimbo.states.PlayingState;
import dungeonsAkimbo.states.StartSplashState;
import jig.Entity;
import jig.ResourceManager;

public class DungeonsAkimboGame extends StateBasedGame {
	

	//Art Macros
	public static final String TEMP_PLAYER = "dungeonsAkimbo/resource/Projectiles/temp_player.png";
	public static final String TEMP_BULLET = "dungeonsAkimbo/resource/Projectiles/temp_bullet.png";
	public static final String MOB_ZERO = "dungeonsAkimbo/resource/Mobs/Spoopy.png";
	public static final String MOB_ONE = "dungeonsAkimbo/resource/Mobs/Mommy.png";
	public static final String DA_SNIPER_RSC = "dungeonsAkimbo/resource/Weapons/Sniper/sniper.png";
	
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
	
	
	private DaCollisions masterCollider;
	private DaMap gameMap;
	private boolean mapReady = false;
	private TiledMap mapPlan;
	
	// Keep track of mobs
	
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		addState(new PlayTestState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		
		ResourceManager.loadImage(TEMP_PLAYER);
		ResourceManager.loadImage(TEMP_BULLET);
		
		// Load weapon sprites
		ResourceManager.loadImage(DA_SNIPER_RSC);
		
		// Load images for mobs
		ResourceManager.loadImage(MOB_ZERO);
		ResourceManager.loadImage(MOB_ONE);
		
		Entity.antiAliasing = false;
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);
		
		
		
		
		
		// Initialize mobs (currently start with one mob)
		
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
			gameMap.loadMap();
		startCollisions();
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
	
	public void startCollisions() {
		masterCollider = new DaCollisions(gameMap);
	}
	
	public void collideCheck(int delta) {
		masterCollider.playerCollsionsTest(delta);
		masterCollider.enemyCollisionsTest(delta);
	}
	
	public void addPlayer(int playerID) {
		// TODO Auto-generated method stub
		if(gameMap.getPlayerList().containsKey(playerID)) return;
		
		gameMap.addNewPlayer(playerID);
	}

	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new DungeonsAkimboGame("Dungeons Akimbo " + VERSION, WIDTH, HEIGHT));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.setUpdateOnlyWhenVisible(false);
			app.setAlwaysRender(true);
			app.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}


}

