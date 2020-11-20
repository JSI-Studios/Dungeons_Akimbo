package dungeonsAkimbo;

import java.util.ArrayList;
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
	private Map<Integer, Player> playerList;
	
	public DaCollisions (DaMap map) {
		this.mobList = map.getMobList();
		this.wallList = map.getWallList();
		this.ProjectileList = map.getPlayer_bullets();
		this.playerList = map.getPlayerList();
		
	}
	
	
	public void playerCollsionsTest(int delta) {
		for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
			Player playerCheck = uniquePlayer.getValue();
			for(DaWall wall : wallList) {
				if(playerCheck.collides(wall) != null) {
					playerCheck.translate(playerCheck.collides(wall).getMinPenetration().scale(delta/.5f));
				}
			}
			/*
			for(DaMob mob : mobList) {
				if(playerCheck.collides(mob) != null) {
					playerCheck.translate(playerCheck.collides(mob).getMinPenetration().scale(delta/2));
				}
			}
			*/
		}
	}
	
	public void enemyCollisionsTest(int delta) {
		for(DaMob mob : mobList) {
			for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
				if(mob.collides(uniquePlayer.getValue()) != null) {
					mob.translate(mob.collides(uniquePlayer.getValue()).getMinPenetration().scale(delta));
				}
			}
			for(DaWall wall : wallList) {
				if(mob.collides(wall) != null) {
					mob.translate(mob.collides(wall).getMinPenetration().scale(delta/8));
				}
			}
		}
	}

}
