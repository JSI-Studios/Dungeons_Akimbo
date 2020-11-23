package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
	
	public DaCollisions (DaMap map) {
		this.mobList = map.getMobList();
		this.wallList = map.getWallList();
		this.ProjectileList = map.getPlayer_bullets();
		this.playerList = map.getPlayerList();
		this.enemyAttacks = map.getEnemyAttacks();
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
				Projectile hitbox = current.next();
				if(playerCheck.collides(hitbox) != null) {
					playerCheck.setCurrent_health(playerCheck.getCurrent_health() - hitbox.Get_Damage());
					current.remove();
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
				if(mob.collides(wall) != null) {
					mob.translate(mob.collides(wall).getMinPenetration().scale(delta/8));
				}
			}
			for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
				// Deal collision will player
				if(mob.collides(uniquePlayer.getValue()) != null) {
					mob.collisionAction(true, mob.getType() == 0);
					mob.translate(mob.collides(uniquePlayer.getValue()).getMinPenetration().scale(delta));
				}
			}
			if(mob.isDead()) {
				// mob is dead
				current.remove();
			}
		}
	}

}
