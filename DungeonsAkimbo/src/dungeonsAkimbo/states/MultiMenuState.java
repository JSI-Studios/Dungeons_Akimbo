package dungeonsAkimbo.states;

import dungeonsAkimbo.DungeonsAkimboGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MultiMenuState extends BasicGameState {

    Rectangle local, network;

    /**
     * @see GameState#getID()
     */
    @Override
    public int getID() {
        return DungeonsAkimboGame.MULTIMENUSTATE;
    }

    /**
     * Initialise the state. It should load any resources it needs at this stage
     *
     * @param container The container holding the game
     * @param game      The game holding this state
     * @throws SlickException Indicates a failure to initialise a resource for this state
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        local = new Rectangle(520, 400, 120, 20);
        network = new Rectangle(520, 440, 120, 20);
    }

    /**
     * Render this state to the game's graphics context
     *
     * @param container The container holding the game
     * @param game      The game holding this state
     * @param g         The graphics context to render to
     * @throws SlickException Indicates a failure to render an artifact
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.draw(local);
        g.draw(network);

        g.drawString("Local", 525, 400);
        g.drawString("Network", 530, 440);
    }

    /**
     * Update the state's logic based on the amount of time thats passed
     *
     * @param container The container holding the game
     * @param game      The game holding this state
     * @param delta     The amount of time thats passed in millisecond since last update
     * @throws SlickException Indicates an internal error that will be reported through the
     *                        standard framework mechanism
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        float mouseX = input.getMouseX();
        float mouseY = input.getMouseY();
        // enter play testing state for multiplayer play
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (local.contains(mouseX, mouseY)) {
                game.enterState(DungeonsAkimboGame.LOCALSETUPSTATE);
            }
            if (network.contains(mouseX, mouseY)) {
                game.enterState(DungeonsAkimboGame.NETMENUSTATE);
            }
        }
    }
}
