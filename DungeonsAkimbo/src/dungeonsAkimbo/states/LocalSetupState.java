package dungeonsAkimbo.states;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.InputListeners.DaJoyconListener;
import org.joyconLib.Joycon;
import org.joyconLib.JoyconConstant;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class LocalSetupState extends BasicGameState {

    private int pollCooldown;
    private Rectangle p1, p2, p3, p4, start;

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
        p1 = new Rectangle(150, 820, 100, 30);
        p2 = new Rectangle(350, 820, 100, 30);
        p3 = new Rectangle(550, 820, 100, 30);
        p4 = new Rectangle(750, 820, 100, 30);
        start = new Rectangle(100, 100, 100, 30);
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
            DaJoyconListener newListener = new DaJoyconListener(false);
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
        DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
        DaJoyconListener[] activeListeners = dag.getActiveJoycons();
        g.setColor(Color.lightGray);
        g.fill(start);
        g.setColor(Color.black);
        g.drawString("Start", 127, 107.5f);
        g.setColor(Color.lightGray);
        switch (dag.getKeyboardMouseIndex()) {
            case 0: {
                g.fill(p1);
                g.setColor(Color.red);
                g.drawString("P1", 190, 828);
                break;
            }
            case 1: {
                g.fill(p2);
                g.setColor(Color.blue);
                g.drawString("P2", 390, 828);
                break;
            }
            case 2: {
                g.fill(p3);
                g.setColor(Color.yellow);
                g.drawString("P3", 590, 828);
                break;
            }
            case 3: {
                g.fill(p4);
                g.setColor(Color.green);
                g.drawString("P4", 790, 828);
                break;
            }
            default: break;
        }
        for (int i = 0; i < activeListeners.length; i++) {
            g.setColor(Color.lightGray);
            if (activeListeners[i] != null) {
                switch (i) {
                    case 0: {
                        g.fill(p1);
                        g.setColor(Color.red);
                        g.drawString("P1", 190, 828);
                        break;
                    }
                    case 1: {
                        g.fill(p2);
                        g.setColor(Color.blue);
                        g.drawString("P2", 390, 828);
                        break;
                    }
                    case 2: {
                        g.fill(p3);
                        g.setColor(Color.yellow);
                        g.drawString("P3", 590, 828);
                        break;
                    }
                    case 3: {
                        g.fill(p4);
                        g.setColor(Color.green);
                        g.drawString("P4", 790, 828);
                        break;
                    }
                    default: break;
                }
            }
        }

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
        if (pollCooldown - delta <= 0) {
            DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
            pollCooldown += 200 - delta;
            if (input.isKeyPressed(Input.KEY_SPACE)) dag.updateKeyboardMouseIndex();
            dag.updateJoyconLists();
        }
        else pollCooldown -= delta;
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            if (start.contains(input.getMouseX(), input.getMouseY())) {
                game.enterState(DungeonsAkimboGame.MULTIPLAYTESTSTATE);
            }
        }
    }
}
