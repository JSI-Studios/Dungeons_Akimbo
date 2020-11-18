package dungeonsAkimbo.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.gui.ChatGUI;
import dungeonsAkimbo.gui.DaCamera;
import jig.Vector;

public class PlayTestState extends BasicGameState {

	public DaCamera gameView;
	private ChatGUI chat;
	
	private String chatMessage = "";
	
	private boolean chatting = false;
	
	private DungeonsAkimboGame dag; 

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
		
		//Chat GUI handling
		chat = new ChatGUI(0, 768, 1024, 244, container);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);

		gameView.renderMap(g);

		// Simply names from dag
		ArrayList<DaMob> mobs = dag.getCurrentMap().mobs;

		dag.player.render(g);

		// Render mob if health exists
		mobs.forEach((mob) -> mob.render(g));

		// Render projectile
		for (Projectile b : dag.getCurrentMap().getPlayer_bullets()) {
			b.render(g);
		}
		
		chat.getChatLog().render(container, g);
		chat.getChatBar().render(container, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		// Simply names from dag
		ArrayList<DaMob> mobs = dag.getCurrentMap().mobs;

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
				new_velocity = new Vector(0f, -0.5f * dag.player.speed);
			} else if (input.isKeyDown(Input.KEY_A)) {
				new_velocity = new Vector(-0.5f * dag.player.speed, 0f);
			} else if (input.isKeyDown(Input.KEY_S)) {
				new_velocity = new Vector(0f, 0.5f * dag.player.speed);
			} else if (input.isKeyDown(Input.KEY_D)) {
				new_velocity = new Vector(0.5f * dag.player.speed, 0f);
			} else {
				new_velocity = new Vector(0f, 0f);
			}

			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				Vector mouseVec = new Vector(input.getMouseX(), input.getMouseY());
				Vector playerPos = dag.player.Get_Position();
				double shot_angle = playerPos.angleTo(mouseVec);
				Projectile bullet = dag.player.Shoot(shot_angle);
				dag.getCurrentMap().getPlayer_bullets().add(bullet);
			
			}
			
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				dag.player.Do_Dodge(delta, 1);
			}
			
			
			dag.player.Set_Velocity(new_velocity);
		}

		

		// Mob attacking the player
		mobs.forEach((mob) -> mob.attack(dag.player));

		// Mob collision handling
		mobs.forEach((mob) -> mob.checkCollision(dag.player, true));
		for (Iterator<DaMob> i = mobs.iterator(); i.hasNext();) {
			// Check if any mobs lost all their health
			DaMob mob = i.next();
			if (mob.getHealth() <= 0) {
				i.remove();
			}
		}

		// Check for collision with mobs, and also update projectiles
		for (Projectile b : dag.getCurrentMap().getPlayer_bullets()) {
			mobs.forEach((mob)->mob.checkCollision(b, false));
			b.update(delta);
		}

		// Update entities
		dag.player.update(delta);
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
