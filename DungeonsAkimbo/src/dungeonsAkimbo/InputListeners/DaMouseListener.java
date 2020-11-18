package dungeonsAkimbo.InputListeners;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class DaMouseListener implements MouseListener {
    private Input input;
    private boolean leftMouseClicked;

    /**
     * Notification that the mouse wheel position was updated
     *
     * @param change The amount of the wheel has moved
     */
    @Override
    public void mouseWheelMoved(int change) {

    }

    /**
     * Notification that a mouse button was clicked. Due to double click
     * handling the single click may be delayed slightly. For absolute notification
     * of single clicks use mousePressed().
     * <p>
     * To be absolute this method should only be used when considering double clicks
     *
     * @param button     The index of the button (starting at 0)
     * @param x          The x position of the mouse when the button was pressed
     * @param y          The y position of the mouse when the button was pressed
     * @param clickCount The number of times the button was clicked
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

    }

    /**
     * Notification that a mouse button was pressed
     *
     * @param button The index of the button (starting at 0)
     * @param x      The x position of the mouse when the button was pressed
     * @param y      The y position of the mouse when the button was pressed
     */
    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == Input.MOUSE_LEFT_BUTTON) leftMouseClicked = true;
    }

    /**
     * Notification that a mouse button was released
     *
     * @param button The index of the button (starting at 0)
     * @param x      The x position of the mouse when the button was released
     * @param y      The y position of the mouse when the button was released
     */
    @Override
    public void mouseReleased(int button, int x, int y) {
        if (button == Input.MOUSE_LEFT_BUTTON) leftMouseClicked = false;
    }

    /**
     * Notification that mouse cursor was moved
     *
     * @param oldx The old x position of the mouse
     * @param oldy The old y position of the mouse
     * @param newx The new x position of the mouse
     * @param newy The new y position of the mouse
     */
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

    }

    /**
     * Notification that mouse cursor was dragged
     *
     * @param oldx The old x position of the mouse
     * @param oldy The old y position of the mouse
     * @param newx The new x position of the mouse
     * @param newy The new y position of the mouse
     */
    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {

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

    public boolean isLeftMouseClicked() {
        return leftMouseClicked;
    }
}
