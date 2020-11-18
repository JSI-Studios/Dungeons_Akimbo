package dungeonsAkimbo.entities;

import jig.Entity;

public interface DaEnemy {
	
	// check collision with the player and handle state of the enemy
	public boolean checkCollision(Entity object, boolean isPlayer);
	
	// An implementation line to handle an enemy attack
	public void attack(Entity player);
	
}
