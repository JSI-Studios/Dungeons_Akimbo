package dungeonsAkimbo;

import java.util.ArrayList;
import java.util.Map;

import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.map.DaWall;

public class DaCollisions {
	
	private DungeonsAkimboGame dag;
	private ArrayList<DaMob> mobList;
	private ArrayList<DaWall> wallList;
	private ArrayList<Projectile> ProjectileList;
	private Map<Integer, Player> playerList;
	
	public DaCollisions (DungeonsAkimboGame dag) {
		this.dag = dag;
		this.mobList = dag.getCurrentMap().getMobList();
		this.wallList = dag.getCurrentMap().getWallList();
		this.ProjectileList = dag.getCurrentMap().getPlayer_bullets();
		this.playerList = dag.getCurrentMap().getPlayerList();
		
	}
	
	
	public void playerCollsionsTest(int delta) {
		for(Map.Entry<Integer, Player> uniquePlayer : playerList.entrySet()) {
			Player playerCheck = uniquePlayer.getValue();
			for(DaWall wall : wallList) {
				if(playerCheck.collides(wall) != null) {
					playerCheck.translate(playerCheck.collides(wall).getMinPenetration().scale(delta));
				}
			}
		}
	}

}
