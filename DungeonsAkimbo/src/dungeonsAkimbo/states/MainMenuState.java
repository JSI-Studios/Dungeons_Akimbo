package dungeonsAkimbo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;

public class MainMenuState extends BasicGameState {

	


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
		DungeonsAkimboGame dag = (DungeonsAkimboGame)game;
		
		g.drawString("press space to begin", dag.screenWidth/2, dag.screenHeight/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		DungeonsAkimboGame dag = (DungeonsAkimboGame)game;
		Input input = container.getInput();
		//enter playing state
		if(input.isKeyDown(Input.KEY_SPACE)) {
			game.enterState(DungeonsAkimboGame.PLAYINGSTATE);
		}
		//enter play testing state
		if(input.isKeyDown(Input.KEY_LCONTROL)) {
			if(input.isKeyDown(Input.KEY_T)) {
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
			}
		}
		
		if(input.isKeyDown(Input.KEY_LCONTROL)) {
			if(input.isKeyPressed(Input.KEY_S)) {
				dag.startServer();
				dag.startClient();
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
			}
		}
		
		if(input.isKeyDown(Input.KEY_LCONTROL)) {
			if(input.isKeyPressed(Input.KEY_C)) {
				dag.startClient();
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
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
		return DungeonsAkimboGame.MAINMENUSTATE;
	}


}