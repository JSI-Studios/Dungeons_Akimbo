package dungeonsAkimbo;

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

import jig.Vector;

public class PlayTestState extends BasicGameState {

	public DaCamera gameView;
	private TextField chatBar;
	private TextField chatLog;
	
	private String chatMessage = "";
	
	private boolean chatting = false;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub


	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		dag.loadNewTiledMap(1);
		dag.loadMap();
		gameView = new DaCamera(dag.getCurrentMap());
		
		//Chat GUI handling
		chatLog = new TextField(container, container.getDefaultFont(), 1025, 580, 246, 400);
		chatLog.setBackgroundColor(Color.transparent);
		chatLog.setBorderColor(Color.gray);
		chatLog.setTextColor(Color.white);
		chatBar = new TextField(container, container.getDefaultFont(), 1025, 980, 246, 20);
		chatBar.setBackgroundColor(Color.transparent);
		chatBar.setBorderColor(Color.gray);
		chatBar.setCursorVisible(true);
		chatBar.setTextColor(Color.white);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);

		gameView.renderMap();

		// Simply names from dag
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		ArrayList<DaMob> mobs = dag.mobs;

		dag.player.render(g);

		// Render mob if health exists
		mobs.forEach((mob) -> mob.render(g));

		// Render projectile
		for (Projectile b : dag.player_bullets) {
			b.render(g);
		}
		chatLog.render(container, g);
		chatBar.render(container, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		// Simply names from dag
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		ArrayList<DaMob> mobs = dag.mobs;

		Vector new_velocity;
		Input input = container.getInput();

		// Chat controls
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (!chatting) {
				chatBar.setFocus(true);
				chatBar.setConsumeEvents(true);
				chatting = !chatting;
			} else {
				
				chatBar.setFocus(false);
				chatBar.setConsumeEvents(false);
				chatMessage = chatBar.getText();
				dag.getClient().sendMessage(chatMessage);
				chatBar.setText("");
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

			if (input.isKeyPressed(Input.KEY_J)) {
				dag.player.Shoot(dag);
			}
			
			dag.player.Set_Velocity(new_velocity);
		}

		

		// Mob attacking the player
		mobs.forEach((mob) -> mob.attack(dag.player));

		// Mob collision handling
		mobs.forEach((mob) -> mob.checkCollision(dag.player));
		for (Iterator<DaMob> i = mobs.iterator(); i.hasNext();) {
			// Check if any mobs lost all their health
			DaMob mob = i.next();
			if (mob.getHealth() <= 0) {
				i.remove();
			}
		}

		//
		for (Projectile b : dag.player_bullets) {
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
					chatLog.setText(chatLog.getText() + "\n" + message);
				}
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
