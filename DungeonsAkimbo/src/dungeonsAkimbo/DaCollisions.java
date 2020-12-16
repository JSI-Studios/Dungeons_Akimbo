package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

import dungeonsAkimbo.entities.DaBoi;
import dungeonsAkimbo.entities.DaChest;
import dungeonsAkimbo.entities.DaGold;
import dungeonsAkimbo.entities.DaGoldPouch;
import dungeonsAkimbo.entities.DaMiniBoi;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.DaSpawner;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.entities.Ranged;
import dungeonsAkimbo.map.DaMap;
import dungeonsAkimbo.map.DaWall;

public class DaCollisions {
	
	private ArrayList<DaMob> mobList;
	private ArrayList<DaWall> wallList;
	private ArrayList<Projectile> ProjectileList;
	private ArrayList<Projectile> enemyAttacks;
	private Map<Integer, Player> playerList;
	private ArrayList<DaMiniBoi> miniBoss;
	private ArrayList<DaBoi> boss;
	private ArrayList<DaSpawner> spawns;
	private ArrayList<DaChest> chests;
	private ArrayList<DaGoldPouch> pouch;
	private ArrayList<DaGold> gold;
	
	public DaCollisions (DaMap map) {
		this.mobList = map.getMobList();
		this.wallList = map.getWallList();
		this.ProjectileList = map.getPlayer_bullets();
		this.playerList = map.getPlayerList();
		this.enemyAttacks = map.getEnemyAttacks();
		this.miniBoss = map.getMiniBoss();
		this.boss = map.getBoss();
		this.spawns = map.getSpawnList();
		this.chests = map.getChestList();
		this.pouch = map.getgoldPouchList();
		this.gold = map.getGoldList();
	}
	
	
	public void playerCollsionsTest(int delta) {
		for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
			Player playerCheck = uniquePlayer.getValue();
			for(DaWall wall : wallList) {
				if(playerCheck.collides(wall) != null) {
					playerCheck.translate(playerCheck.collides(wall).getMinPenetration().scale(delta/.5f));
				}
			}
			for(Iterator<Projectile> current = this.enemyAttacks.iterator(); current.hasNext();) {
				Projectile attackHitBox = current.next();
				if (playerCheck.isDodging() == false) {
					if(playerCheck.collides(attackHitBox) != null) {
						playerCheck.setCurrent_health(playerCheck.getCurrent_health() - attackHitBox.Get_Damage());
						if(attackHitBox.getSpriteType() <= 0) {
							current.remove();
						} else {
							// Don't continuously damage the player, but allow animation to render completely
							attackHitBox.setDamage(0);
						}
					}
				}
			}
			for(DaMob mob : mobList) {
				if(playerCheck.collides(mob) != null) {
					if (playerCheck.isDodging() == true) {
						continue;
					} else {
						playerCheck.setCurrent_health(playerCheck.getCurrent_health() - 1);
					}
				}
			}
			for(Iterator<DaChest> currentChest = this.chests.iterator(); currentChest.hasNext();) {
				DaChest chest = currentChest.next();
				if(playerCheck.collides(chest) != null) {
					playerCheck.translate(playerCheck.collides(chest).getMinPenetration().scale(delta/.5f));
					int[] openChest = chest.open();
					if(openChest != null) {
						for(Integer objects: openChest) {
							if(objects == 1) {
								// Add health
								playerCheck.setCurrent_health(playerCheck.getCurrent_health() + 10);
							} else if (objects == 2) {
								// Add ammo
								Ranged current = playerCheck.getPrimaryWeapon();
								playerCheck.getPrimaryWeapon().setAmmo(current.getAmmo() + 20);
							} else if (objects == 3) {
								// Add powerups
								
							} else if (objects == 4) {
								// Add gold bags/puch
								playerCheck.setPoints(playerCheck.getPoints() + 25);
							} else {
								// Add gold
								playerCheck.setPoints(playerCheck.getPoints() + 5);
							}
						}
					}
				}
			}
			// Handle gold pouch
			for(Iterator<DaGoldPouch> currentPouch = this.pouch.iterator(); currentPouch.hasNext();) {
				DaGoldPouch goldPouch = currentPouch.next();
				if(playerCheck.collides(goldPouch) != null) {
					playerCheck.setPoints(playerCheck.getPoints() + goldPouch.pickUp());
					currentPouch.remove();
				}
			}
			// Handle individual gold
			for(Iterator<DaGold> currentGold = this.gold.iterator(); currentGold.hasNext();) {
				DaGold golden = currentGold.next();
				if(playerCheck.collides(golden) != null) {
					playerCheck.setPoints(playerCheck.getPoints() + golden.pickUp());
					currentGold.remove();
				}
			}
		}
	}
	
	public void enemyCollisionsTest(int delta) {
		for(Iterator<DaMob> current = this.mobList.iterator(); current.hasNext();) {
			DaMob mob = current.next();
			for(DaWall wall : wallList) {
				// Handle wall collision first
				int[] ignoreMob = {0, 3};
				if(mob.collides(wall) != null && !IntStream.of(ignoreMob).anyMatch(i -> i == mob.getType())) {
					mob.translate(mob.collides(wall).getMinPenetration().scale(delta/8));
				}
			}
			for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
				// Deal collision will player
				if(mob.collides(uniquePlayer.getValue()) != null) {
					// If mob type == 0, return true for isPlayer to pause the mob
					mob.collisionAction(true, mob.getType() == 0);
					mob.translate(mob.collides(uniquePlayer.getValue()).getMinPenetration().scale(delta));
				}
			}
			for(Iterator<Projectile> currentProjectile = this.ProjectileList.iterator(); currentProjectile.hasNext();) {
				Projectile playerHit = currentProjectile.next();
				if(mob.collides(playerHit) != null) {
					mob.setHealth(mob.getHealth() - playerHit.Get_Damage());
					if(playerHit.getSpriteType() <= 0) {
						currentProjectile.remove();
					} else {
						// Don't continuously damage the mob, but allow animation to render completely
						playerHit.setDamage(0);
					}
				}
			}
			for(DaMiniBoi mobBoss : miniBoss) {
				if(mob.collides(mobBoss) != null && mob.getType() == 2) {
					mob.translate(mob.collides(mobBoss).getMinPenetration().scale(delta));
				}
				
			}
			if(mob.isDead()) {
				// mob is dead
				current.remove();
			}
		}
		// Iterate through each player to determine boss interactions
		for(Map.Entry<Integer, Player> uniquePlayer: playerList.entrySet()) {
			Player player = uniquePlayer.getValue();
			// Handle collision with player, tell player to decrease health later
			for(DaMiniBoi mobBoss: miniBoss) {
				if(player.collides(mobBoss) != null) {
					player.translate(player.collides(mobBoss).getMinPenetration().scale(delta * 4f));
					mobBoss.collisionAction(true, true);
					player.setCurrent_health(player.getCurrent_health() - 1);
				}
			}
			for(DaBoi theBoss: boss) {
				if(player.collides(theBoss) != null) {
					theBoss.collisionAction(true, true);
					if(theBoss.isStall()) {
						player.translate(player.collides(theBoss).getMinPenetration().scale(delta * 5f));
					} 
					player.setCurrent_health(player.getCurrent_health() - 1);
				}
				
			}
		}
		// Iterate each projectile for boss interaction
		for(Iterator<Projectile> currentProjectile = this.ProjectileList.iterator(); currentProjectile.hasNext();) {
			Projectile playerHit = currentProjectile.next();
			// Iterate through all boss (usually one)
			for(Iterator<DaBoi> currentBoss =  this.boss.iterator(); currentBoss.hasNext();) {			
				DaBoi theBoss = currentBoss.next();
				if(theBoss.collides(playerHit) != null && !theBoss.isStall()) {
					theBoss.setHealth(theBoss.getHealth() - playerHit.Get_Damage());
					if(playerHit.getSpriteType() <= 0) {
						currentProjectile.remove();
					} else {
						// Don't continuously damage the mob, but allow animation to render completely
						playerHit.setDamage(0);
					}
					if(theBoss.getHealth() == 0) {
						currentBoss.remove();
					}
				}	
			}
			// Iterate through all mini bosses (usually one)
			for(Iterator<DaMiniBoi> currentMiniBoss = this.miniBoss.iterator(); currentMiniBoss.hasNext();) {
				DaMiniBoi mobBoss = currentMiniBoss.next();				
				if(mobBoss.collides(playerHit) != null) {
					mobBoss.setHealth(mobBoss.getHealth() - playerHit.Get_Damage());
					if(playerHit.getSpriteType() <= 0) {
						currentProjectile.remove();
					} else {
						// Don't continuously damage the mob, but allow animation to render completely
						playerHit.setDamage(0);
					}
					if(mobBoss.getHealth() <= 0) {
						currentMiniBoss.remove();
					}
				}
				
			}
			for(Iterator<DaSpawner> currentSpawner = this.spawns.iterator(); currentSpawner.hasNext();) {
				DaSpawner spawn = currentSpawner.next();
				if(playerHit.collides(spawn) != null) {
					// Decrease health of spawn and remove it
					spawn.setHealth(spawn.getHealth() - playerHit.Get_Damage());
					currentProjectile.remove();
					// Check health after collision to remove
					if(spawn.getHealth() <= 0) {
						currentSpawner.remove();
					}
				}
			}
		}
	}

}
