package dungeonsAkimbo.InputListeners;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.ArrayList;

public class DaKeyListener implements KeyListener {
    private ArrayList<Integer> keysPressed;
    private Input input;
    public DaKeyListener() {
        keysPressed = new ArrayList<>();
    }

    /**
     * Notification that a key was pressed
     *
     * @param key The key code that was pressed (@see org.newdawn.slick.Input)
     * @param c   The character of the key that was pressed
     */
    @Override
    public void keyPressed(int key, char c) {
        if (!keysPressed.contains(key)) keysPressed.add(key);
    }

    /**
     * Notification that a key was released
     *
     * @param key The key code that was released (@see org.newdawn.slick.Input)
     * @param c   The character of the key that was released
     */
    @Override
    public void keyReleased(int key, char c) {
        if (keysPressed.contains(key)) keysPressed.remove((Object) key);
    }

    /**
     * Set the input that events are being sent from
     *
     * @param input The input instance sending events
     */
    @Override
    public void setInput(Input input) {
        this.input = input;
    }

    /**
     * Check if this input listener is accepting input
     *
     * @return True if the input listener should recieve events
     */
    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    /**
     * Notification that all input events have been sent for this frame
     */
    @Override
    public void inputEnded() {

    }

    /**
     * Notification that input is about to be processed
     */
    @Override
    public void inputStarted() {

    }

    public DaCommand issueCommand() {
        int hori = 0;
        int vert = 0;
        if (keysPressed.contains((Object) Input.KEY_D)) hori++;
        if (keysPressed.contains((Object) Input.KEY_A)) hori--;
        if (keysPressed.contains((Object) Input.KEY_S)) vert++;
        if (keysPressed.contains((Object) Input.KEY_W)) vert--;
        DaCommand daCommand;
        if (hori != 0 || vert != 0) {
            daCommand = new DaCommand("Move");
            daCommand.setMoveDirection(hori, vert);
            return daCommand;
        }
        else return new DaCommand("Stop");
    }
}
