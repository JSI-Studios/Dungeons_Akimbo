package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Iterator;

import dungeonsAkimbo.InputListeners.DaJoyconListener;
import dungeonsAkimbo.states.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.map.DaMap;
import dungeonsAkimbo.netcode.DaClient;
import dungeonsAkimbo.netcode.DaServer;
import jig.Entity;
import jig.ResourceManager;

public class DungeonsAkimboGame extends StateBasedGame {
	

	//Art Macros
	public static final String TEMP_PLAYER = "dungeonsAkimbo/resource/Projectiles/temp_player.png";
	public static final String TEMP_BULLET = "dungeonsAkimbo/resource/Projectiles/temp_bullet.png";
	public static final String MOB_ZERO = "dungeonsAkimbo/resource/Mobs/Spoopy.png";
	public static final String MOB_ONE = "dungeonsAkimbo/resource/Mobs/Mommy.png";
	public static final String MOB_TWO = "dungeonsAkimbo/resource/Mobs/Skully.png";
	public static final String MOB_THREE = "dungeonsAkimbo/resource/Mobs/Spoopy-Season-Two.png";
	public static final String MINI_BOSS = "dungeonsAkimbo/resource/Mobs/Mini-Boss.png";
	public static final String STALL_ONE = "dungeonsAkimbo/resource/Mobs/Cosmic_06.png";
	public static final String STALL_TWO = "dungeonsAkimbo/resource/Mobs/Cosmic_07.png";
	public static final String STALL_THREE = "dungeonsAkimbo/resource/Mobs/Cosmic_08.png";
	public static final String STALL_FOUR = "dungeonsAkimbo/resource/Mobs/Cosmic_09.png";
	public static final String STALL_FIVE = "dungeonsAkimbo/resource/Mobs/Cosmic_10.png";
	
	
	//Player Macros
	public static final String DA_MALE1_RSC = "dungeonsAkimbo/resource/Mobs/Male 01-1.png";
	public static final String DA_SCHOOL_GIRL1_RSC = "dungeonsAkimbo/resource/Mobs/su1 Student fmale 10.png";
	public static final String DA_MALE2_RSC = "dungeonsAkimbo/resource/Mobs/Male 01-2.png";
	public static final String DA_SCHOOL_GIRL2_RSC = "dungeonsAkimbo/resource/Mobs/su2 Student fmale 05.png";
	
	//Weapon Macros
	public static final String DA_SNIPER_RSC = "dungeonsAkimbo/resource/Weapons/Sniper/sniper.png";
	public static final String DA_SMG_RSC = "dungeonsAkimbo/resource/Weapons/SMG/smg.png";
	public static final String DA_PISTOL_RSC = "dungeonsAkimbo/resource/Weapons/Pistol/pistol3.png";
	public static final String DA_SHOTTY_RSC = "dungeonsAkimbo/resource/Weapons/Shotty/shotgun.png";
	public static final String DA_ASSAULT_RSC = "dungeonsAkimbo/resource/Weapons/Assault/assaultrifle.png";
	
	// Enemy Attack Macros
	public static final String BANG = "dungeonsAkimbo/resource/Projectiles/explosion.png";
	public static final String MAGIC_ONE = "dungeonsAkimbo/resource/Projectiles/Cosmic_01.png";
	public static final String MAGIC_TWO = "dungeonsAkimbo/resource/Projectiles/Cosmic_02.png";
	public static final String MAGIC_THREE = "dungeonsAkimbo/resource/Projectiles/Cosmic_03.png";
	public static final String MAGIC_FOUR = "dungeonsAkimbo/resource/Projectiles/Cosmic_04.png";
	public static final String MAGIC_FIVE = "dungeonsAkimbo/resource/Projectiles/Cosmic_05.png";
	
	//item macros
	public static final String DA_HEALTH_RSC = "dungeonsAkimbo/resource/items/potion_03a.png";
	public static final String DA_AMMO_RSC = "dungeonsAkimbo/resource/items/ammobox.png";
	public static final String DA_CANDYRELOAD_RSC = "dungeonsAkimbo/resource/items/candy_02g.png";
	public static final String DA_SWITCH_RSC = "dungeonsAkimbo/resource/items/lever2.png";
	public static final String DA_MISCSHEET_RSC = "dungeonsAkimbo/resource/Maps/testMap/[Base]BaseChip_pipo.png";
	
	
	// BGM macros
	public static final String TEMP_BGM = "dungeonsAkimbo/resource/bgm/Final Boss Battle 6 V2.WAV";
	public static final String BGM = "dungeonsAkimbo/resource/bgm/Theme Song 8-bit V1 _looping.WAV";
	public static final String BOSS_BGM = "dungeonsAkimbo/resource/bgm/Boss Battle #2 V1.WAV";
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int PLAYTESTSTATE = 3;
	public static final int NETMENUSTATE = 4;
	public static final int MULTIMENUSTATE = 5;
	public static final int LOCALSETUPSTATE = 6;
	public static final int MULTIPLAYTESTSTATE = 7;
	public static final int GAMEOVERSTATE = 8;
	
	public static final String DA_TESTMAP_RSC = "dungeonsAkimbo/resource/Maps/testMap/DaTestMapSmall.tmx";
	public static final String DA_TESTMAP_TILESET_RSC = "dungeonsAkimbo/resource/Maps/testMap/";

	public static final String DA_MAINMAP_TILESET_RSC = "dungeonsAkimbo/resource/Maps/mainMaps/";
	public static final String DA_BIG_VERTICAL_MAP_RSC = "dungeonsAkimbo/resource/Maps/mainMaps/DaBigVerticalMap.tmx";
	public static final String DA_BIG_HORIZONTAL_MAP_RSC = "dungeonsAkimbo/resource/Maps/mainMaps/DaBigHorizontalMap.tmx";
	public static final String DA_COLLESIUM_MAP_RSC = "dungeonsAkimbo/resource/Maps/mainMaps/DaCollesiumMap.tmx";
	//App properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	public static final int FPS = 60;
	public static final double VERSION = .01;
	
	
	
	public final int screenWidth;
	public final int screenHeight;
	
	private Thread server, client;
	
	private DaCollisions masterCollider;
	private DaMap gameMap;
	private boolean mapReady = false;
	private TiledMap mapPlan;
	private DaLogic gameLogic;
	private DaJoyconListener[] activeJoycons;
	private ArrayList<DaJoyconListener> inactiveJoycons;
	private int keyboardMouseIndex;
	private int currentMap;
	// Keep track of mobs
	
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		addState(new PlayTestState());
		addState(new NetMenuState());
		addState(new MultiMenuState());
		addState(new LocalSetupState());
		addState(new MultiPlayTestState());
		addState(new GameOverState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		
		// Load projectiles
		ResourceManager.loadImage(TEMP_PLAYER);
		ResourceManager.loadImage(TEMP_BULLET);
		ResourceManager.loadImage(BANG);
		
		// Load player resources
		ResourceManager.loadImage(DA_MALE1_RSC);
		ResourceManager.loadImage(DA_MALE2_RSC);
		ResourceManager.loadImage(DA_SCHOOL_GIRL1_RSC);
		ResourceManager.loadImage(DA_SCHOOL_GIRL2_RSC);
		
		
		// Load weapon sprites
		ResourceManager.loadImage(DA_SNIPER_RSC);
		ResourceManager.loadImage(DA_SMG_RSC);
		ResourceManager.loadImage(DA_PISTOL_RSC);
		ResourceManager.loadImage(DA_SHOTTY_RSC);
		ResourceManager.loadImage(DA_ASSAULT_RSC);
		
		// Load images for mobs
		ResourceManager.loadImage(MOB_ZERO);
		ResourceManager.loadImage(MOB_ONE);
		ResourceManager.loadImage(MOB_TWO);
		ResourceManager.loadImage(MOB_THREE);
		
		// Load boss assets
		ResourceManager.loadImage(MINI_BOSS);
		ResourceManager.loadImage(STALL_ONE);
		ResourceManager.loadImage(STALL_TWO);
		ResourceManager.loadImage(STALL_THREE);
		ResourceManager.loadImage(STALL_FOUR);
		ResourceManager.loadImage(STALL_FIVE);
		ResourceManager.loadImage(MAGIC_ONE);
		ResourceManager.loadImage(MAGIC_TWO);
		ResourceManager.loadImage(MAGIC_THREE);
		ResourceManager.loadImage(MAGIC_FOUR);
		ResourceManager.loadImage(MAGIC_FIVE);
		
		// Load item sprites
		ResourceManager.loadImage(DA_HEALTH_RSC);
		ResourceManager.loadImage(DA_AMMO_RSC);
		ResourceManager.loadImage(DA_CANDYRELOAD_RSC);
		ResourceManager.loadImage(DA_SWITCH_RSC);
		ResourceManager.loadImage(DA_MISCSHEET_RSC);
	
		// Load bgm
		ResourceManager.loadSound(BGM);
		ResourceManager.loadSound(BOSS_BGM);
		
		Entity.antiAliasing = false;
		Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);

		activeJoycons = new DaJoyconListener[] {null, null, null, null};
		inactiveJoycons = new ArrayList<>();
		keyboardMouseIndex = 0;
		
		
		// Initialize mobs (currently start with one mob)
		
	}
	
	public DungeonsAkimboGame(String title, int width, int height) {
		super(title);
		screenWidth = width;
		screenHeight = height;
		currentMap = 2;
	}

	
	
	public void loadNewTiledMap(int map) throws SlickException {
		if (map == 1) {
			mapPlan = new TiledMap(DA_TESTMAP_RSC, DA_TESTMAP_TILESET_RSC);
		}
		else if (map == 2) {
			mapPlan = new TiledMap(DA_BIG_VERTICAL_MAP_RSC, DA_TESTMAP_TILESET_RSC);
		}
		else if (map == 3) {
			mapPlan = new TiledMap(DA_BIG_HORIZONTAL_MAP_RSC, DA_TESTMAP_TILESET_RSC);
		}
		else if (map == 4) {
			mapPlan = new TiledMap(DA_COLLESIUM_MAP_RSC, DA_TESTMAP_TILESET_RSC);
		}
		currentMap = map;
		mapReady = true;
	}
	
	public void loadMap() {
		if(mapReady)
			gameMap = new DaMap(mapPlan);
			gameMap.loadMap();
		startLogic();
	}
	
	private void startLogic() {
		gameLogic = new DaLogic(gameMap);
	}

	public DaMap getCurrentMap() {
		return gameMap;		
	}
	
	public void startServer() {
		server = new DaServer(8989);		
	}
	
	public void startClient(String userName, String address, int port) {
		client = new DaClient(userName,address, 8989);		
	}
	
	public DaClient getClient() {
		return (DaClient) client;
	}
	
	public DaLogic getLogic() {
		return gameLogic;
	}

	public DaJoyconListener[] getActiveJoycons() {return activeJoycons;}

	public ArrayList<DaJoyconListener> getInactiveJoycons() {return inactiveJoycons;}

	public int getKeyboardMouseIndex() {return keyboardMouseIndex;}

	public void updateJoyconLists() {
		for (int i = 0; i < 4; i++) {
			if (activeJoycons[i] != null && (activeJoycons[i].isButtonPressed(8) || activeJoycons[i].isButtonPressed(9))) {
				// if the +/- button is pressed, "disconnect" joy-con from the active list
				inactiveJoycons.add(activeJoycons[i]);
				activeJoycons[i] = null;
			}
		}
		for (Iterator<DaJoyconListener> jls = inactiveJoycons.iterator(); jls.hasNext();) {
			if (activeJoycons[0] == null || activeJoycons[1] == null || activeJoycons[2] == null || activeJoycons[3] == null) {
				DaJoyconListener listener = jls.next();
				if (listener.isButtonDown(4) && listener.isButtonDown(5)) {
					if (activeJoycons[0] == null) {
						activeJoycons[0] = listener;
						jls.remove();
					}
					else if (activeJoycons[1] == null) {
						activeJoycons[1] = listener;
						jls.remove();
					}
					else if (activeJoycons[2] == null) {
						activeJoycons[2] = listener;
						jls.remove();
					}
					else if (activeJoycons[3] == null) {
						activeJoycons[3] = listener;
						jls.remove();
					}
				}
			} else break;
		}
	}

	public void unassignKeyboardMouse() {keyboardMouseIndex = -1;}

	public void updateKeyboardMouseIndex() {
		if (keyboardMouseIndex == -1) {
			for (int i = 0; i < 4; i++) {
				if (activeJoycons[i] == null) {
					keyboardMouseIndex = i;
					break;
				}
			}
		}
	}

	public void addPlayer(int playerID) {
		// TODO Auto-generated method stub
		if(gameMap.getPlayerList().containsKey(playerID)) return;
		
		gameMap.addNewPlayer(playerID);
	}

	public int getCurrentMapNum() {
		return currentMap;
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

