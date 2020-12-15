package dungeonsAkimbo.states;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.InputListeners.DaJoyconListener;
import org.joyconLib.Joycon;
import org.joyconLib.JoyconConstant;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class LocalSetupState extends BasicGameState {

    private int pollCooldown;


    /**
     * @see GameState#getID()
     */
    @Override
    public int getID() {
        return DungeonsAkimboGame.LOCALSETUPSTATE;
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
        pollCooldown = 200;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
        dag.unassignKeyboardMouse();

        ArrayList<DaJoyconListener> inactiveListeners = dag.getInactiveJoycons();
        inactiveListeners.clear();
        DaJoyconListener[] activeListeners = dag.getActiveJoycons();
        for (int h = 0; h < 4; h++) {
            // clear all active joy-con listeners
            activeListeners[h] = null;
        }
        for (int i = 0; i < 4; i++) {
            // connect up to four left joy-cons (some of the listeners will be set to empty joycons if less are actually connected)
            Joycon newJoycon = new Joycon(JoyconConstant.JOYCON_LEFT);
            DaJoyconListener newListener = new DaJoyconListener(true);
            newJoycon.setListener(newListener);
            inactiveListeners.add(newListener);
        }
        for (int j = 0; j < 4; j++) {
            // connect up to four right joy-cons (some of the listeners will be set to empty joycons if less are actually connected)
            Joycon newJoycon = new Joycon(JoyconConstant.JOYCON_RIGHT);
            DaJoyconListener newListener = new DaJoyconListener(true);
            newJoycon.setListener(newListener);
            inactiveListeners.add(newListener);
        }
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
        if (pollCooldown - delta <= 0) {
            DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
            pollCooldown += 200;
            pollCooldown -= delta;
            dag.updateKeyboardMouseIndex();
            dag.updateJoyconLists();
        }
        else pollCooldown -= delta;
    }
}
