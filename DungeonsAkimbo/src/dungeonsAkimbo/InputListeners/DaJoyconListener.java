package dungeonsAkimbo.InputListeners;

import org.joyconLib.*;

import java.util.Arrays;
import java.util.Map;

/**
 * The controller listener to be used for joy-cons. This utilizes the joyconLib repository maintained by
 * GitHub user elgoupil at https://github.com/elgoupil/joyconLib
 */
public class DaJoyconListener implements JoyconListener{
    // this implementation assumes that the joy-con is held horizontally
    private float x;
    private float y;
    private boolean[] buttonsPressed;
    private boolean isLeft;
    /*
        button assignments (with the joy-con held horizontally):
        0 - bottom button on d-pad or ABXY buttons
        1 - right button on d-pad or ABXY buttons
        2 - left button on d-pad or ABXY buttons
        3 - top button on d-pad or ABXY buttons
        4 - SL
        5 - SR
        6 - not mapped to a button
        7 - not mapped to a button
        8 - "-" button (left joy-con only)
        9 - "+" button (right joy-con only)
       10 - left joystick
       11 - right joystick
       12 - capture button
       13 - home button
       14 - L/R, depending on joy-con type (left or right)
       15 - ZL/ZR, depending on joy-con type (left or right)

     */
    public DaJoyconListener(boolean isLeft) {
        buttonsPressed = new boolean[16];
        Arrays.fill(buttonsPressed, false);
        this.isLeft = isLeft;
    }
    /**
     * @param je The event object
     */
    @Override
    public void handleNewInput(JoyconEvent je) {
        for (Map.Entry<String, Boolean> entry: je.getNewInputs().entrySet()) {
            // do work with new input entry here
            // keep in mind that inputs are interpreted from the perspective of the joy-con held vertically

            // button input detection and handling:
            if (entry.getKey().equals(JoyconConstant.LEFT) || entry.getKey().equals(JoyconConstant.A)) {
                // if bottom button changed:
                buttonsPressed[0] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.DOWN) || entry.getKey().equals(JoyconConstant.X)) {
                // if right button changed:
                buttonsPressed[1] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.UP) || entry.getKey().equals(JoyconConstant.B)) {
                // if left button changed:
                buttonsPressed[2] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.RIGHT) || entry.getKey().equals(JoyconConstant.Y)) {
                // if top button changed:
                buttonsPressed[3] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.SL)) {
                // if SL changed:
                buttonsPressed[4] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.SR)) {
                // if SR changed:
                buttonsPressed[5] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.MINUS)) {
                // if - changed (left joy-con only):
                buttonsPressed[8] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.PLUS)) {
                // if + changed (right joy-con only):
                buttonsPressed[9] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.LEFT_STICK)) {
                // if left joy-con's joystick changed:
                buttonsPressed[10] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.RIGHT_STICK)) {
                // if right joy-con's joystick changed:
                buttonsPressed[11] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.CAPTURE)) {
                // if capture button changed (left joy-con only):
                buttonsPressed[12] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.HOME)) {
                // if home button changed (right joy-con only):
                buttonsPressed[13] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.L) || entry.getKey().equals(JoyconConstant.R)) {
                // if L/R changed:
                buttonsPressed[14] = entry.getValue();
            }
            if (entry.getKey().equals(JoyconConstant.ZL) || entry.getKey().equals(JoyconConstant.ZR)) {
                // if ZL/ZR changed:
                buttonsPressed[15] = entry.getValue();
            }

        }

        // joystick input detection and handling:
        // keep in mind that the vertical and horizontal values of the joystick
        // returned by getVertical/getHorizontal are in the following format, with the joy-con held vertically:
        /*
                            1.0
                             |
                             |
                             |
                 -1.0 -------+------- 1.0
                             |
                             |
                             |
                           -1.0
         */
        if (isLeft) {
            x = -je.getVertical();
            y = -je.getHorizontal();
        }
        else {
            x = je.getVertical();
            y = je.getHorizontal();
        }
    }

    public float getX() {return x;}

    public float getY() {return y;}

    public boolean isButtonPressed(int button) {return buttonsPressed[button];}
}
