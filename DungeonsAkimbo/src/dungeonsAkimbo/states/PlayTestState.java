package dungeonsAkimbo.states;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.entities.DaAssault;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.gui.ChatGUI;
import dungeonsAkimbo.gui.DaCamera;
import dungeonsAkimbo.netcode.UniqueIdentifier;
import dungeonsAkimbo.netcode.UpdateHandler;
import dungeonsAkimbo.netcode.UpdatePacket;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class PlayTestState extends BasicGameState {

	public DaCamera gameView;
	private ChatGUI chat;

	private String chatMessage = "";

	private boolean chatting = false;

	private DungeonsAkimboGame dag;

	private int playerID;
	
	private Sound currentSound;
	private int[] cameraDirection;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		playerID = -1;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		dag = (DungeonsAkimboGame) game;
		dag.loadNewTiledMap(1);
		dag.loadMap();
		gameView = new DaCamera(dag.getCurrentMap());
		if(dag.getClient() != null)	playerID = dag.getClient().getClientID();
		dag.addPlayer(playerID);
		// Chat GUI handling
		chat = new ChatGUI(0, 768, 1024, 244, container);
		
		// Handle BGM
		container.setSoundOn(true);
		currentSound = ResourceManager.getSound(DungeonsAkimboGame.BGM);
		currentSound.loop(1f, 0.2f);
		cameraDirection = new int[]{0, 0, 0, 0};
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub

		// render the camera view and everything within it. layered in the order of
		// calls.
		gameView.renderMap(g);
		gameView.renderItems(g);
		gameView.renderPlayerGui(g);
		gameView.renderPlayers(g);
		g.flush();
		gameView.renderMobs(g);
		gameView.renderProjectiles(g);

		chat.getChatLog().render(container, g);
		chat.getChatBar().render(container, g);

		// Testing room
		g.setColor(Color.black);
		g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		// Simply names from dag
		Vector new_velocity;
		Input input = container.getInput();
		Vector mouseVec = new Vector(input.getMouseX(), input.getMouseY()).add(new Vector(gameView.getCameraX(), gameView.getCameraY()));
		Vector playerPos = dag.getCurrentMap().getPlayerList().get(playerID).getPosition();
		double shotAngle = playerPos.angleTo(mouseVec);

		// Chat controls
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			if (!chatting) {
				chat.activateChatBar();
				chatting = !chatting;
			} else {
				String message = chat.getChatBarContents();
				if (message != null)
					dag.getClient().sendMessage(message);
				chatting = !chatting;
			}
		}
		// Temp movement control handling

		if (!chatting) {
			if (input.isKeyDown(Input.KEY_W)) {
				new_velocity = new Vector(0f, -0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed());
				dag.getCurrentMap().getPlayerList().get(playerID).setRest(false);
			} else if (input.isKeyDown(Input.KEY_A)) {
				new_velocity = new Vector(-0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f);
				dag.getCurrentMap().getPlayerList().get(playerID).setRest(false);
			} else if (input.isKeyDown(Input.KEY_S)) {
				new_velocity = new Vector(0f, 0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed());
				dag.getCurrentMap().getPlayerList().get(playerID).setRest(false);
			} else if (input.isKeyDown(Input.KEY_D)) {
				new_velocity = new Vector(0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f);
				dag.getCurrentMap().getPlayerList().get(playerID).setRest(false);
			} else {
				new_velocity = new Vector(0f, 0f);
				if(!dag.getCurrentMap().getPlayerList().get(playerID).isRest()) {
					dag.getLogic().resetPath(playerID);
					dag.getCurrentMap().getPlayerList().get(playerID).setRest(true);
				}
			}

			if (input.isKeyPressed(Input.KEY_P)) {
				dag.getClient().console(Integer.toString(playerID));
			}
			
			/* if (input.isKeyPressed(Input.KEY_1)) {
				dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(0);
			} else if (input.isKeyPressed(Input.KEY_2)) {
				dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(1);
			} else if (input.isKeyPressed(Input.KEY_3)) {
				dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(2);
			} else if (input.isKeyPressed(Input.KEY_4)) {
				dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(3);
			} else if (input.isKeyPressed(Input.KEY_5)) {
				dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(4);
			} */
				
			if (input.isKeyPressed(Input.KEY_Q)) {
				dag.getCurrentMap().getPlayerList().get(playerID).getNextGun();
			}

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

				//if player can shoot and primary weapon is no assault
				if (dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon().isCan_shoot() && dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon() instanceof DaAssault == false ) {
					Object bulletReturn = dag.getCurrentMap().getPlayerList().get(playerID).Shoot(shotAngle);
					
					//if arraylist is returned from shotty
					if (bulletReturn instanceof ArrayList) {
						for (Object b : (ArrayList<Projectile>) bulletReturn) {
							//System.out.println("itering ;LKJA:LKJDF:LKJ'");
							dag.getCurrentMap().getPlayer_bullets().add((Projectile) b);
						}
					//Case for every other gun
					} else if (bulletReturn instanceof Projectile) {
						dag.getCurrentMap().getPlayer_bullets().add((Projectile) bulletReturn);
					}
					
				// if player can shoot and primary weapon is assault rifle
				} else if ( dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon().isCan_shoot() && dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon() instanceof DaAssault == true) {
					dag.getCurrentMap().getPlayerList().get(playerID).Shoot(shotAngle);
				}
				
				
			}

			if (input.isKeyPressed(Input.KEY_SPACE)) {
				dag.getCurrentMap().getPlayerList().get(playerID).doDodge(delta, 1);
			}
			
			if (input.isKeyPressed(Input.KEY_U)) {
				Vector playerNetPos = dag.getCurrentMap().getPlayerList().get(playerID).getPosition();
				Vector playerVel = dag.getCurrentMap().getPlayerList().get(playerID).getVelocity();
				int thisID = playerID;
				int frameCount = dag.getFramecount();
				byte[] updateBytes = null;
				UpdatePacket update = new UpdatePacket(thisID, playerNetPos, playerVel, frameCount);
				try {
					updateBytes = dag.getClientUpdater().sendUpdate(update);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(updateBytes != null) dag.getClient().sendData(updateBytes);
				
			}

			dag.getCurrentMap().getPlayerList().get(playerID).setVelocity(new_velocity);
			((Entity) dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon()).setRotation(shotAngle);
			
        	// Handle camera movement based on number of players
            Player player = dag.getCurrentMap().getPlayerList().get(playerID);
            if(player.getVelocity().length() > 0) {
	            if(player.getY() - (gameView.getCameraY() * 64) < 512 && gameView.getCameraY() > 0) {
	            		this.cameraDirection[0]++;
	            }
	            if(player.getY() - (gameView.getCameraY() * 64) > 512 && gameView.getCameraY() < dag.getCurrentMap().getHeightInTiles()) {
	            		this.cameraDirection[1]++;
	            }
	            if(player.getX() - (gameView.getCameraX() * 64) < 512 && gameView.getCameraX() > 0) {
	            		this.cameraDirection[2]++;
	            }
	            if(player.getX() - (gameView.getCameraX() * 64) > 512 && gameView.getCameraX() < dag.getCurrentMap().getWidthInTiles()) {
	            		this.cameraDirection[3]++;
	            }
            }
		}

        /* Handle camera movement */
        
        //  Handle y axis
        if(cameraDirection[0] == 1) {
        	gameView.moveCameraY(-1, delta);
        }
        if(cameraDirection[1] == 1) {
        	gameView.moveCameraY(1, delta);
        }
        // Handle x axis
        if(cameraDirection[2] == 1) {
        	gameView.moveCameraX(-1, delta);
        }
        if(cameraDirection[3] == 1) {
        	gameView.moveCameraX(1, delta);
        }
        // Reset camera direction
        this.cameraDirection = new int[] {0, 0, 0, 0};
        
		//update Logic
		if (dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon() instanceof DaAssault) {
			dag.getLogic().assaultBurst(dag, playerID, shotAngle);
		}
		dag.getLogic().localUpdate(delta);
		updateChatLog(dag);
		dag.updateFramecount();
		
		// Check player hp, if 0 go to game over state
		if(dag.getCurrentMap().getPlayerList().get(playerID).getCurrent_health() <= 0) {
			// Clear inputs, stop sound and transition to gameover state
			input.clearKeyPressedRecord();
			currentSound.stop();
			// Go back to splash state for now
			game.enterState(DungeonsAkimboGame.GAMEOVERSTATE);
		}

	}

	private void updateChatLog(DungeonsAkimboGame dag) {
		// TODO Auto-generated method stub
		if (dag.getClient() != null) {
			if (!dag.getClient().getMessageLog().isEmpty()) {
				List<String> newChats = dag.getClient().getMessageLog();
				for (String message : newChats) {
					chat.addNewChat(message);
				}
				chat.updateChatLog();
				dag.getClient().getMessageLog().clear();
			}
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return DungeonsAkimboGame.PLAYTESTSTATE;
	}

}
