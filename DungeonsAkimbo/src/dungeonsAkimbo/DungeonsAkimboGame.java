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
	
	//Weapon Macros
	public static final String DA_SNIPER_RSC = "dungeonsAkimbo/resource/Weapons/Sniper/sniper.png";
	public static final String DA_SMG_RSC = "dungeonsAkimbo/resource/Weapons/SMG/smg.png";
	public static final String DA_PISTOL_RSC = "dungeonsAkimbo/resource/Weapons/Pistol/pistol3.png";
	public static final String DA_SHOTTY_RSC = "dungeonsAkimbo/resource/Weapons/Shotty/shotgun.png";
	public static final String DA_ASSAULT_RSC = "dungeonsAkimbo/resource/Weapons/Assault/assaultrifle.png";
	
	//item macros
	public static final String DA_HEALTH_RSC = "dungeonsAkimbo/resource/items/ammobox.png";
	public static final String DA_AMMO_RSC = "dungeonsAkimbo/resource/items/candy_02g.png";
	public static final String DA_CANDYRELOAD_RSC = "dungeonsAkimbo/resource/items/lever2.png";
	public static final String DA_SWITCH_RSC = "dungeonsAkimbo/resource/items/potion_03a.png";
	
	public static final String DA_PLAYER_RSC = "dungeonsAkimbo/resource/Mobs/Male 01-1.png";
	
	
	//State Identitifiers
	public static final int SPLASHSCREENSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int PLAYTESTSTATE = 3;
	public static final int NETMENUSTATE = 4;
	public static final int MULTIMENUSTATE = 5;
	public static final int LOCALSETUPSTATE = 6;
	
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
	
	private DaCollisions masterCollider;
	private DaMap gameMap;
	private boolean mapReady = false;
	private TiledMap mapPlan;
	private DaLogic gameLogic;
	private DaJoyconListener[] activeJoycons;
	private ArrayList<DaJoyconListener> inactiveJoycons;
	private int keyboardMouseIndex;
	// Keep track of mobs
	
	
	
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartSplashState());
		addState(new MainMenuState());
		addState(new PlayingState());
		addState(new PlayTestState());
		addState(new NetMenuState());
		addState(new MultiMenuState());
		addState(new LocalSetupState());
		ResourceManager.setFilterMethod(ResourceManager.FILTER_LINEAR);
		
		ResourceManager.loadImage(TEMP_PLAYER);
		ResourceManager.loadImage(TEMP_BULLET);
		ResourceManager.loadImage(DA_PLAYER_RSC);
		
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
		
		// Load item sprites
		ResourceManager.loadImage(DA_HEALTH_RSC);
		ResourceManager.loadImage(DA_AMMO_RSC);
		ResourceManager.loadImage(DA_CANDYRELOAD_RSC);
		ResourceManager.loadImage(DA_SWITCH_RSC);
	
		
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
				if (listener.isButtonPressed(4) && listener.isButtonPressed(5)) {
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

