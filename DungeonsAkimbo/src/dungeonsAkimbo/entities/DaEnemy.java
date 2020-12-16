package dungeonsAkimbo.entities;

import jig.Entity;

public interface DaEnemy {
	
	// Called when a collision interaction is confirmed
	public void collisionAction(boolean isHit, boolean isPlayer);
	
	// An implementation line to handle an enemy attack
	public Projectile attack(Entity player);
	
}
