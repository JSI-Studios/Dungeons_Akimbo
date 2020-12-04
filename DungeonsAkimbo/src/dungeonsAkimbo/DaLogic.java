package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Iterator;

import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.map.DaMap;

public class DaLogic {

	DaMap map;
	DaCollisions bonkHandler;

	public DaLogic(DaMap gameMap) {
		this.map = gameMap;
		startCollisions();
	}
	
	
	//logic update to be used by client until we can remove the need for playerID
	//currently just updating entities and checking collisions
	//this should get get called in the playstate's update()
	//and used by the server to update it's map.
	
	public void clientUpdate(int playerID, int delta) {
		clientUpdateEntities(playerID, delta);
		collideCheck(delta);
	}
	
	//update entities 
	private void clientUpdateEntities(int playerID, int delta) {
		// Mob attacking the player
		ArrayList<DaMob> mobs = map.getMobList();
		for (DaMob mob : mobs) {
			Projectile hit = mob.attack(map.getPlayerList().get(playerID));
			if (hit != null) {
				// System.out.println("Reached");
				map.getEnemyAttacks().add(hit);
			}
		}

		// Check for collision with mobs, and also update projectiles
		//Iterator<Bullet> bulletIter = cg.bullet_array.iterator(); bulletIter.hasNext();
		for (Iterator<Projectile> bIter = map.getPlayer_bullets().iterator(); bIter.hasNext();) {
			Projectile b = bIter.next();
			
			b.update(delta);
			b.decreaseTimer(delta);
			
			if (b.getTimer() <= 0) {
				bIter.remove();
			}
		}

		// Update entities
		map.getPlayerList().get(playerID).update(delta);
		map.getPlayerList().get(playerID).getPrimaryWeapon().update(delta);
		map.getEnemyAttacks().forEach((hitbox) -> hitbox.update(delta));
		mobs.forEach((mob) -> mob.update(delta));

	}
	//start up a collision handler for the map
	public void startCollisions() {
		bonkHandler = new DaCollisions(map);
	}
	//check for collisions in this update
	public void collideCheck(int delta) {	
		bonkHandler.playerCollsionsTest(delta);
		bonkHandler.enemyCollisionsTest(delta);
	}

}