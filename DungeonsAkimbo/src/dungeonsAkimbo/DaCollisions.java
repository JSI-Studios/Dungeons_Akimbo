package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

import dungeonsAkimbo.entities.DaBoi;
import dungeonsAkimbo.entities.DaMiniBoi;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.map.DaMap;
import dungeonsAkimbo.map.DaWall;

public class DaCollisions {
	
	private ArrayList<DaMob> mobList;
	private ArrayList<DaWall> wallList;
	private ArrayList<Projectile> ProjectileList;
	private ArrayList<Projectile> enemyAttacks;
	private Map<Integer, Player> playerList;
	private DaMiniBoi miniBoss;
	private DaBoi boss;
	
	public DaCollisions (DaMap map) {
		this.mobList = map.getMobList();
		this.wallList = map.getWallList();
		this.ProjectileList = map.getPlayer_bullets();
		this.playerList = map.getPlayerList();
		this.enemyAttacks = map.getEnemyAttacks();
		this.miniBoss = map.getMiniBoss();
		this.boss = map.getBoss();
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
			for(DaMob mob : mobList) {
//				if(playerCheck.collides(mob) != null) {
//					mob.collisionAction(true, true);
//				}
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
			if(mob.collides(miniBoss) != null && mob.getType() == 2) {
				mob.translate(mob.collides(miniBoss).getMinPenetration().scale(delta));
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
			if(player.collides(miniBoss) != null) {
				player.translate(player.collides(miniBoss).getMinPenetration().scale(delta * 5f));
				miniBoss.collisionAction(true, true);
			}
			if(player.collides(boss) != null) {
				boss.collisionAction(true, true);
			}
		}
	}

}
