package dungeonsAkimbo;

import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.Player;

public class PlayingState extends BasicGameState {
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
		
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		DungeonsAkimboGame dg = (DungeonsAkimboGame) game;
		
		
		dg.player.render(g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
		DungeonsAkimboGame dg = (DungeonsAkimboGame) game;
		
		Vector new_velocity;
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_W)) {			
			new_velocity = new Vector(0f, -0.5f*dg.player.speed);	
		} else if (input.isKeyDown(Input.KEY_A)) {		
			new_velocity = new Vector(-0.5f*dg.player.speed, 0f);
		} else if (input.isKeyDown(Input.KEY_S)) {			
			new_velocity = new Vector(0f, 0.5f*dg.player.speed);
		} else if (input.isKeyDown(Input.KEY_D)){			
			new_velocity = new Vector(0.5f*dg.player.speed, 0f);
		} else {
			new_velocity = new Vector(0f,0f);
		}
		
		dg.player.Set_Velocity(new_velocity);
		
		dg.player.update(delta);
	}

	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return DungeonsAkimboGame.PLAYINGSTATE;
	}


}