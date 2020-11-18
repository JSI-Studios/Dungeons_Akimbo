package dungeonsAkimbo.InputListeners;

import org.newdawn.slick.Input;

public class DaKeyboardMouseListener {
    private Input input;
    private DaKeyListener keyListener;
    private DaMouseListener mouseListener;

    public DaKeyboardMouseListener() {
        keyListener = new DaKeyListener();
        mouseListener = new DaMouseListener();
    }

    public DaKeyboardMouseListener(Input input) {
        keyListener = new DaKeyListener();
        mouseListener = new DaMouseListener();
        setInput(input);
    }

    public void setInput(Input input) {
        if (input != null) {
            input.removeKeyListener(keyListener);
            input.removeMouseListener(mouseListener);
        }
        this.input = input;
        input.addKeyListener(keyListener);
        input.addMouseListener(mouseListener);
    }

    public DaCommand issueCommand() {
        DaCommand daCommand = keyListener.issueCommand();
        daCommand.setShoot(mouseListener.isLeftMouseClicked());
        return daCommand;
    }
}
