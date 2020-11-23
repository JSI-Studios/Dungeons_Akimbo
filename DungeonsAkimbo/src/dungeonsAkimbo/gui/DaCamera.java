package dungeonsAkimbo.gui;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;

import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;
import dungeonsAkimbo.map.DaMap;
import dungeonsAkimbo.map.DaTile;
import dungeonsAkimbo.map.DaWall;

public class DaCamera {
	
	private float xOffSet; //offset of the camera in the x
	private float yOffSet; //offset of the camera in the y
	private int tileWidth; //tile width in px
	private int tileHeight; // tileHeight in px
	private int mapHeight; //Map height in tiles
	private int mapWidth;	//Map width in tiles
	
	DaMap currentGame; //current game's map;
	
	public boolean Debug;
	
	public DaCamera(DaMap map) {
		super();
		xOffSet = 0;
		yOffSet = 0;
		
		currentGame = map;
		
		tileWidth = currentGame.getTileWidth();
		tileHeight = currentGame.getTileHeight();
		mapHeight = currentGame.getHeightInTiles();
		mapWidth = currentGame.getWidthInTiles();
		
		
	}
	
	public float getCameraX() {
		return xOffSet;
	}
	
	public float getCameraY() {
		return yOffSet;
	}
	
	public void moveCameraX(int dx, int delta) {
		if (dx > 0 && xOffSet < mapWidth - 16) {
			xOffSet += .00195f * delta;
		}else if(dx < 0 && xOffSet > 0){
			xOffSet -= .00195f * delta;
		}
		return;
		
	}
	
	public void moveCameraY(int dy, int delta) {
		if (dy > 0 && yOffSet < mapHeight - 16) {
			yOffSet += .00195f * delta;
		}else if(dy < 0 && yOffSet > 0) {
			yOffSet -= .00195f * delta;
		}	
		return;		
	}
	
	public void renderPlayers(Graphics g) {
		for(Map.Entry<Integer, Player> uniquePlayer : currentGame.getPlayerList().entrySet()) {
			Player player = uniquePlayer.getValue();
			if((player.getX()/tileWidth) > this.xOffSet && (player.getY()/tileHeight) > this.yOffSet ) {	
				if(player.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
					((Entity) player.getPrimaryWeapon()).render(g);
					
					player.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
	}
	
	public void renderMobs(Graphics g) {
		for(DaMob mob: currentGame.getMobList()) {
			if((mob.getX()/tileWidth) > this.xOffSet && (mob.getY()/tileHeight) > this.yOffSet ) {	
				if(mob.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
					mob.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
	}
	
	public void renderProjectiles(Graphics g) {
		for(Projectile projectile : currentGame.getPlayer_bullets()) {
			if((projectile.getX()/tileWidth) > this.xOffSet && (projectile.getY()/tileHeight) > this.yOffSet ) {	
				if(projectile.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
					projectile.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}

		for(Projectile projectile : currentGame.getEnemyAttacks()) {
			if((projectile.getX()/tileWidth) > this.xOffSet && (projectile.getY()/tileHeight) > this.yOffSet ) {	
				if(projectile.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
					projectile.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
	}
	
	public void renderMap(Graphics g) {
		for(DaTile tile: currentGame.getTileList()) {
			if((tile.getX()/tileWidth) >= this.xOffSet - 1 && (tile.getY()/tileHeight) >= this.yOffSet-1) {
				if(tile.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					tile.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		for(DaWall wall: currentGame.getWallList()) {
			if((wall.getX()/tileWidth) >= this.xOffSet - 1 && (wall.getY()/tileHeight) >= this.yOffSet-1) {
				if(wall.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					wall.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		//currentGame.getTiledMap().render(0, 0);
	}

}
