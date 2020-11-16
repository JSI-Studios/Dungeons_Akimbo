package dungeonsAkimbo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import jig.Vector;

public class PlayTestState extends BasicGameState {

	public DaCamera gameView;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		DungeonsAkimboGame dag = (DungeonsAkimboGame)game;
		dag.loadNewTiledMap(1);
		dag.loadMap();
		gameView = new DaCamera(dag.getCurrentMap());
		
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);
		
		gameView.renderMap();
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		dag.player.render(g);
		dag.mob.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		
		Vector new_velocity;
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_W)) {			
			new_velocity = new Vector(0f, -0.5f*dag.player.speed);	
		} else if (input.isKeyDown(Input.KEY_A)) {		
			new_velocity = new Vector(-0.5f*dag.player.speed, 0f);
		} else if (input.isKeyDown(Input.KEY_S)) {			
			new_velocity = new Vector(0f, 0.5f*dag.player.speed);
		} else if (input.isKeyDown(Input.KEY_D)){			
			new_velocity = new Vector(0.5f*dag.player.speed, 0f);
		} else {
			new_velocity = new Vector(0f,0f);
		}
		
		dag.player.Set_Velocity(new_velocity);
		
		dag.player.update(delta);
		
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
