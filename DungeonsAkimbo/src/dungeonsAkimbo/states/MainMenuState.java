package dungeonsAkimbo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;

public class MainMenuState extends BasicGameState {

	Rectangle singlePlayer, multiPlayer;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		singlePlayer = new Rectangle(520, 400, 120, 20);
		multiPlayer = new Rectangle(520, 440, 120, 20);

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		g.draw(singlePlayer);
		g.draw(multiPlayer);

		g.drawString("SinglePlayer", 525, 400);
		g.drawString("MultiPlayer", 530, 440);
		// g.drawString("press space to begin", dag.screenWidth/2, dag.screenHeight/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		// enter play testing state
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (singlePlayer.contains(mouseX, mouseY)) {
				game.enterState(DungeonsAkimboGame.PLAYTESTSTATE);
			}
			if (multiPlayer.contains(mouseX, mouseY)) {
				game.enterState(DungeonsAkimboGame.MULTIMENUSTATE);
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