package dungeonsAkimbo.InputListeners;

import jig.Vector;
import org.newdawn.slick.command.Command;

public class DaCommand implements Command {

    public Vector moveDirection;
    public String name;
    public boolean shoot;

    public DaCommand(String name) {
        this.name = name;
        shoot = false;
    }

    public void setMoveDirection(float x, float y) {
        this.moveDirection = new Vector(x,y).setLength(1.0f);
    }

    public void setMoveDirection(Vector moveDirection) {
        this.moveDirection = moveDirection.setLength(1.0f);
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }
}
