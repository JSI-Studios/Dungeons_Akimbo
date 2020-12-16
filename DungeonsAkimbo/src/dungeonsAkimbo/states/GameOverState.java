package dungeonsAkimbo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;

public class GameOverState extends BasicGameState {

	private Rectangle gameOver, restart;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		gameOver = new Rectangle(520, 400, 120, 60);
		restart = new Rectangle(520, 580, 120, 20);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		g.draw(gameOver);
		g.draw(restart);

		g.drawString("Game Over!", 525, 420);
		g.drawString("Restart", 530, 580);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {		
		Input input = container.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		// enter play testing state
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (restart.contains(mouseX, mouseY)) {
				game.enterState(DungeonsAkimboGame.MULTIMENUSTATE);
			}
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 8;
	}

}
