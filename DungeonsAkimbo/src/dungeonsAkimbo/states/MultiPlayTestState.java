package dungeonsAkimbo.states;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.InputListeners.DaJoyconListener;
import dungeonsAkimbo.entities.DaAssault;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.gui.ChatGUI;
import dungeonsAkimbo.gui.DaCamera;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayTestState extends BasicGameState {
    public DaCamera gameView;
    private ChatGUI chat;

    private String chatMessage = "";

    private boolean chatting = false;

    private DungeonsAkimboGame dag;

    private ArrayList<Integer> playerIDs;
    
	private Sound currentSound;

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
        DaJoyconListener[] activeListeners = dag.getActiveJoycons();
        for (int i = 0; i < activeListeners.length; i++) {
            if (activeListeners[i] != null) {
                dag.addPlayer(i);
            }
        }
        int keyboardIndex = dag.getKeyboardMouseIndex();
        if (keyboardIndex != -1) {
            dag.addPlayer(keyboardIndex);
        }
        // Chat GUI handling
        chat = new ChatGUI(0, 768, 1024, 244, container);;
		
		// Handle BGM
		container.setSoundOn(true);
		currentSound = ResourceManager.getSound(DungeonsAkimboGame.BGM);
		currentSound.loop(1f, 0.2f);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // TODO Auto-generated method stub

        // render the camera view and everything within it. layered in the order of
        // calls.
        gameView.renderMap(g);
        gameView.renderPlayers(g);
        g.flush();
        gameView.renderMobs(g);
        gameView.renderProjectiles(g);

        chat.getChatLog().render(container, g);
        chat.getChatBar().render(container, g);

        // Testing room
        g.setColor(Color.black);
        g.drawString("DUNGEONS AKIMBO TESTING AREA, ITS A MESS, WE KNOW....", 400, 10);
        g.setColor(Color.white);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        // Simply names from dag
        Input input = container.getInput();
        DaJoyconListener[] activeListeners = dag.getActiveJoycons();

//        // Chat controls
//        if (input.isKeyPressed(Input.KEY_ENTER)) {
//            input.clearKeyPressedRecord();
//            if (!chatting) {
//                chat.activateChatBar();
//                chatting = !chatting;
//            } else {
//                String message = chat.getChatBarContents();
//                if (message != null)
//                    dag.getClient().sendMessage(message);
//                chatting = !chatting;
//            }
//        }
        // Temp movement control handling

        for (int playerID : dag.getCurrentMap().getPlayerList().keySet()) {
            double shotAngle;
            if (playerID == dag.getKeyboardMouseIndex()) {
                // if this player is controlled via a keyboard and mouse
                Vector new_velocity;
                Vector mouseVec = new Vector(input.getMouseX(), input.getMouseY());
                Vector playerPos = dag.getCurrentMap().getPlayerList().get(playerID).getPosition();
                shotAngle = playerPos.angleTo(mouseVec);

                if (input.isKeyDown(Input.KEY_W)) {
                    new_velocity = new Vector(0f, -0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed());
                } else if (input.isKeyDown(Input.KEY_A)) {
                    new_velocity = new Vector(-0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f);
                } else if (input.isKeyDown(Input.KEY_S)) {
                    new_velocity = new Vector(0f, 0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed());
                } else if (input.isKeyDown(Input.KEY_D)) {
                    new_velocity = new Vector(0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f);
                } else {
                    new_velocity = new Vector(0f, 0f);
                }

//                if (input.isKeyPressed(Input.KEY_P)) {
//                    dag.getClient().console(Integer.toString(playerID));
//                }

                if (input.isKeyPressed(Input.KEY_1)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(0);
                } else if (input.isKeyPressed(Input.KEY_2)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(1);
                } else if (input.isKeyPressed(Input.KEY_3)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(2);
                } else if (input.isKeyPressed(Input.KEY_4)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).gunSelect(3);
                } else if (input.isKeyPressed(Input.KEY_Q)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).getNextGun();
                }

                if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

                    if (dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon().isCan_shoot()) {
                        Object bulletReturn = dag.getCurrentMap().getPlayerList().get(playerID).Shoot(shotAngle);

                        if (bulletReturn instanceof ArrayList) {
                            for (Object b : (ArrayList<Projectile>) bulletReturn) {
                                //System.out.println("itering ;LKJA:LKJDF:LKJ'");
                                dag.getCurrentMap().getPlayer_bullets().add((Projectile) b);
                            }
                        } else if (bulletReturn instanceof Projectile) {
                            dag.getCurrentMap().getPlayer_bullets().add((Projectile) bulletReturn);
                        }

                    }


                }

                if (input.isKeyPressed(Input.KEY_SPACE)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).doDodge(delta, 1);
                }

                dag.getCurrentMap().getPlayerList().get(playerID).setVelocity(new_velocity);
                ((Entity) dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon()).setRotation(shotAngle);
            }
            else {
                // if the player is being controlled by a joy-con
                if (activeListeners[playerID] == null) {
                    // if player was registered without a listener... issue a warning then remove the player in question
                    System.out.println("Warning! Player registered when player should not have been registered!");
                    dag.getCurrentMap().getPlayerList().remove(playerID);
                    continue;
                }
                Vector shot_dir = new Vector(activeListeners[playerID].getX(), activeListeners[playerID].getY());
                shotAngle = shot_dir.getRotation();
                Vector new_velocity = new Vector(0, 0);

                if (activeListeners[playerID].isButtonDown(3)) {
                    new_velocity = new_velocity.add(new Vector(0f, -0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed()));
                }
                if (activeListeners[playerID].isButtonDown(2)) {
                    new_velocity = new_velocity.add(new Vector(-0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f));
                }
                if (activeListeners[playerID].isButtonDown(0)) {
                    new_velocity = new_velocity.add(new Vector(0f, 0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed()));
                }
                if (activeListeners[playerID].isButtonDown(1)) {
                    new_velocity = new_velocity.add(new Vector(0.5f * dag.getCurrentMap().getPlayerList().get(playerID).getSpeed(), 0f));
                }

                if (activeListeners[playerID].isButtonPressed(15)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).getNextGun();
                }

                if (activeListeners[playerID].isButtonDown(5)) {
                    if (dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon().isCan_shoot()) {
                        Object bulletReturn = dag.getCurrentMap().getPlayerList().get(playerID).Shoot(shotAngle);

                        if (bulletReturn instanceof ArrayList) {
                            for (Object b : (ArrayList<Projectile>) bulletReturn) {
                                //System.out.println("itering ;LKJA:LKJDF:LKJ'");
                                dag.getCurrentMap().getPlayer_bullets().add((Projectile) b);
                            }
                        } else if (bulletReturn instanceof Projectile) {
                            dag.getCurrentMap().getPlayer_bullets().add((Projectile) bulletReturn);
                        }

                    }
                }
                dag.getCurrentMap().getPlayerList().get(playerID).setVelocity(new_velocity);
                ((Entity) dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon()).setRotation(shotAngle);
                if (activeListeners[playerID].isButtonPressed(4)) {
                    dag.getCurrentMap().getPlayerList().get(playerID).doDodge(delta, 1);
                }
            }
            if (dag.getCurrentMap().getPlayerList().get(playerID).getPrimaryWeapon() instanceof DaAssault) {
                dag.getLogic().assaultBurst(dag, playerID, shotAngle);
            }
        }
        //update Logic
        dag.getLogic().localUpdate(delta);
        updateChatLog(dag);
        
        // Check all player's health. If 0, remove player.
        // If player arrayList is empty, game over state
        dag.getCurrentMap().getPlayerList().entrySet().removeIf((player) -> player.getValue().getCurrent_health() <= 0);
        if(dag.getCurrentMap().getPlayerList().size() <= 0) {
			// Clear inputs, stop sound and transition to gameover state
			input.clearKeyPressedRecord();
			currentSound.stop();
			// Go back to splash state for now
			game.enterState(DungeonsAkimboGame.GAMEOVERSTATE);
        }
    }

    private void updateChatLog(DungeonsAkimboGame dag) {
        // TODO Auto-generated method stub
        if (dag.getClient() != null) {
            if (!dag.getClient().getMessageLog().isEmpty()) {
                List<String> newChats = dag.getClient().getMessageLog();
                for (String message : newChats) {
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
        return DungeonsAkimboGame.MULTIPLAYTESTSTATE;
    }
}
