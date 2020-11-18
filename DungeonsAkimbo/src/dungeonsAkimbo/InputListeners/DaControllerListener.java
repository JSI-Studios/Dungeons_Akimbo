package dungeonsAkimbo.InputListeners;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;

/**
 * A generic controllerListener implementation that needs to be bound to a controller
 */
public class DaControllerListener implements org.newdawn.slick.ControllerListener {
    private Boolean bound;
    private Input input;
    private int controllerNum;
    private boolean[] buttonsPressed;

    /**
     * Initializes this listener without binding it to a controller
     */
    public DaControllerListener() {
        controllerNum = -1;
        bound = false;
    }

    /**
     * Initializes and binds this listener to a controller
     *
     * @param controller The controller number to bind to this listener,
     *                   which can be re-bound later
     * @param buttons The number of buttons to be recognized from the controller.
     */
    public DaControllerListener(int controller, int buttons) {
        controllerNum = controller;
        this.bind(controller, buttons);
    }

    /**
     * Binds this listener to a controller.
     * Will print an error message if this listener is already bound to a controller.
     *
     * @param controller The controller number to bind to this listener,
     *                   which can be re-bound later.
     * @param buttons The number of buttons to be recognized from the controller.
     */
    public void bind(int controller, int buttons) {
        if (controllerNum >= 0) {
            System.out.println("Error: already bound! Please unbind current controller first!");
            return;
        }
        controllerNum = controller;
        buttonsPressed = new boolean[buttons];
        System.out.println("Controller " + controller + ", " + buttons + " buttons!");
        bound = true;
    }

    /**
     * Unbinds this listener from its bound controller.
     * Will print error message if this listener is not bound to a controller.
     */
    public void unbind() {
        if (controllerNum <= 0) {
            System.out.println("Error: not bound! This listener is ready to be bound!");
        }
        controllerNum = -1;
        System.out.println("Controller " + controllerNum + " unbound!");
        bound = false;
    }

    /**
     * Notification that the left control has been pressed on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was pressed.
     */
    @Override
    public void controllerLeftPressed(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + " Left pressed!");
    }

    /**
     * Notification that the left control has been released on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was released.
     */
    @Override
    public void controllerLeftReleased(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + "Left released!");
    }

    /**
     * Notification that the right control has been pressed on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was pressed.
     */
    @Override
    public void controllerRightPressed(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + " Right pressed!");
    }

    /**
     * Notification that the right control has been released on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was released.
     */
    @Override
    public void controllerRightReleased(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + " Right released!");
    }

    /**
     * Notification that the up control has been pressed on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was pressed.
     */
    @Override
    public void controllerUpPressed(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + " Up pressed!");
    }

    /**
     * Notification that the up control has been released on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was released.
     */
    @Override
    public void controllerUpReleased(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + "Up released!");
    }

    /**
     * Notification that the down control has been pressed on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was pressed.
     */
    @Override
    public void controllerDownPressed(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + "Down pressed!");
    }

    /**
     * Notification that the down control has been released on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was released.
     */
    @Override
    public void controllerDownReleased(int controller) {
        if (controller == controllerNum) System.out.println("Controller " + controller + "Down released!");
    }

    /**
     * Notification that a button control has been pressed on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was pressed.
     * @param button     The index of the button pressed (starting at 1)
     */
    @Override
    public void controllerButtonPressed(int controller, int button) {
        if (controller == controllerNum) {
            System.out.println("Controller " + controller + " button " + button + " pressed!");
            buttonsPressed[button-1] = true;
        }
    }

    /**
     * Notification that a button control has been released on
     * the controller.
     *
     * @param controller The index of the controller on which the control
     *                   was released.
     * @param button     The index of the button released (starting at 1)
     */
    @Override
    public void controllerButtonReleased(int controller, int button) {
        if (controller == controllerNum) {
            System.out.println("Controller " + controller + " button " + button + " released!");
            buttonsPressed[button-1] = false;
        }
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
        return bound;
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

    /**
     * Prompt the listener to return a command based on current inputs.
     * @return a command containing the name of the command
     *         and (eventually) joystick direction.
     * TODO: flesh out command names and command selection
     * TODO: allocate space for additional command information (ex. direction)
     */
    public BasicCommand issueCommand() {
//        example command name assignment below (Used in Daniel Garrett's Slick2d-ControllerTest repository):
//        for (int i = 1; i < buttonsPressed.length; i++) {
//            if (buttonsPressed[i]) {
//                switch (i) {
//                    case 1: return new BasicCommand("Red");
//                    case 2: return new BasicCommand("Green");
//                    case 3: return new BasicCommand("Blue");
//                    case 4: return new BasicCommand("Yellow");
//                    case 5: return new BasicCommand("Cyan");
//                    case 6: return new BasicCommand("Magenta");
//                    default: return new BasicCommand("White");
//                }
//            }
//        }
        System.out.println("Command issued!");
        return new BasicCommand("Command!");
    }
}
