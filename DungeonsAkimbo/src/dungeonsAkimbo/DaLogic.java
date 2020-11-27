package dungeonsAkimbo;

import java.util.ArrayList;

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

	public void clientUpdate(int playerID, int delta) {
		clientUpdateEntities(playerID, delta);
		collideCheck(delta);
	}

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
		for (Projectile b : map.getPlayer_bullets()) {
			b.update(delta);
		}

		// Update entities
		map.getPlayerList().get(playerID).update(delta);
		map.getPlayerList().get(playerID).getPrimaryWeapon().update(delta);
		map.getEnemyAttacks().forEach((hitbox) -> hitbox.update(delta));
		mobs.forEach((mob) -> mob.update(delta));

	}

	public void startCollisions() {
		bonkHandler = new DaCollisions(map);
	}

	public void collideCheck(int delta) {
		bonkHandler.playerCollsionsTest(delta);
		bonkHandler.enemyCollisionsTest(delta);
	}

}
