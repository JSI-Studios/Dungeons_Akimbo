package dungeonsAkimbo;

import jig.Entity;

public interface DaEnemy {
	
	// check collision with the player and handle state of the enemy
	public boolean checkCollision(Entity player);
	
	// An implementation line to handle an enemy attack
	public void attack(Entity player);
	
}
