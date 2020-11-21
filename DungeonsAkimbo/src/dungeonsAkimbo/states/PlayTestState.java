package dungeonsAkimbo.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.gui.ChatGUI;
import dungeonsAkimbo.gui.DaCamera;
import dungeonsAkimbo.netcode.UniqueIdentifier;
import jig.Vector;

public class PlayTestState extends BasicGameState {

	public DaCamera gameView;
	private ChatGUI chat;
	
	private String chatMessage = "";
	
	private boolean chatting = false;
	
	private DungeonsAkimboGame dag;
	
	private int playerID;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		dag = (DungeonsAkimboGame) game;
		dag.loadNewTiledMap(1);
		dag.loadMap();
		gameView = new DaCamera(dag.getCurrentMap());
		playerID = dag.getClient().getClientID();
		dag.addPlayer(playerID);
		//Chat GUI handling
		chat = new ChatGUI(0, 768, 1024, 244, container);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
		
		//render the camera view and everything within it. layered in the order of calls.
		gameView.renderMap(g);
		gameView.renderPlayers(g);
		gameView.renderMobs(g);
		gameView.renderProjectiles(g);
		
		chat.getChatLog().render(container, g);
		chat.getChatBar().render(container, g);
		
		//Testing room
		g.setColor(Color.black);
		g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		// Simply names from dag
		ArrayList<DaMob> mobs = dag.getCurrentMap().getMobList();
		dag.collideCheck(delta);
		Vector new_velocity;
		Input input = container.getInput();

		// Chat controls
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			if (!chatting) {
				chat.activateChatBar();
				chatting = !chatting;
			} else {	
				String message = chat.getChatBarContents();
				if(message != null)	dag.getClient().sendMessage(message);
				chatting = !chatting;		
			}
		}
		// Temp movement control handling
		
		if (!chatting) {
			if (input.isKeyDown(Input.KEY_W)) {
				new_velocity = new Vector(0f, -0.5f * dag.getCurrentMap().getPlayerList().get(playerID).Get_Speed());
			} else if (input.isKeyDown(Input.KEY_A)) {
				new_velocity = new Vector(-0.5f * dag.getCurrentMap().getPlayerList().get(playerID).Get_Speed(), 0f);
			} else if (input.isKeyDown(Input.KEY_S)) {
				new_velocity = new Vector(0f, 0.5f * dag.getCurrentMap().getPlayerList().get(playerID).Get_Speed());
			} else if (input.isKeyDown(Input.KEY_D)) {
				new_velocity = new Vector(0.5f * dag.getCurrentMap().getPlayerList().get(playerID).Get_Speed(), 0f);
			} else {
				new_velocity = new Vector(0f, 0f);
			}
			
			if (input.isKeyPressed(Input.KEY_P)) {
				dag.getClient().console(Integer.toString(playerID));
			}

			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				Vector mouseVec = new Vector(input.getMouseX(), input.getMouseY());
				Vector playerPos = dag.getCurrentMap().getPlayerList().get(playerID).Get_Position();
				double shot_angle = playerPos.angleTo(mouseVec);
				dag.getCurrentMap().getPlayer_bullets().add(dag.getCurrentMap().getPlayerList().get(playerID).Shoot(shot_angle));
			
			}
			
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				dag.getCurrentMap().getPlayerList().get(playerID).Do_Dodge(delta, 1);
			}
			
			
			dag.getCurrentMap().getPlayerList().get(playerID).Set_Velocity(new_velocity);
		}

		

		// Mob attacking the player
		mobs.forEach((mob) -> mob.attack(dag.getCurrentMap().getPlayerList().get(playerID)));

		// Check for collision with mobs, and also update projectiles
		for (Projectile b : dag.getCurrentMap().getPlayer_bullets()) {
			b.update(delta);
		}

		// Update entities
		dag.getCurrentMap().getPlayerList().get(playerID).update(delta);
		mobs.forEach((mob) -> mob.update(delta));
		
		updateChatLog(dag);

	}

	private void updateChatLog(DungeonsAkimboGame dag) {
		// TODO Auto-generated method stub
		if(dag.getClient() != null) {
			if (!dag.getClient().getMessageLog().isEmpty()) {
				List<String> newChats = dag.getClient().getMessageLog();
				for(String message : newChats) {
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
