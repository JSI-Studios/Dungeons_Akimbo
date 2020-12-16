package dungeonsAkimbo.gui;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;
import dungeonsAkimbo.entities.DaBoi;
import dungeonsAkimbo.entities.DaMiniBoi;
import dungeonsAkimbo.entities.DaChest;
import dungeonsAkimbo.entities.DaGold;
import dungeonsAkimbo.entities.DaGoldPouch;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.DaPickup;
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
					g.flush();
					((Entity) player.getPrimaryWeapon()).render(g);
					//System.out.println("primary weapon type" + player.getPrimaryWeapon().getClass());
					
					player.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
	}
	
	public void renderPlayerGui(Graphics g) {
		for(Map.Entry<Integer, Player> uniquePlayer : currentGame.getPlayerList().entrySet()) {
			Player player = uniquePlayer.getValue();
			int playerKey = uniquePlayer.getKey();
			float barWidth = 125;
			float barHeight = 25;
			if(playerKey > -1) {
				playerKey = playerKey + 1;
				g.setColor(Color.gray);
				g.drawString("Player " + playerKey, 1050, 25+100*(playerKey-1));
				float healthBar = ((float) player.getCurrent_health() / (float) player.getMax_health()) * barWidth;
				g.drawString("Health", 1050, 50+100*(playerKey-1));
				g.fillRect(1050, 75+100*(playerKey-1), barWidth, barHeight);
				g.setColor(Color.red);
				g.fillRect(1050, 75+100*(playerKey-1), healthBar, barHeight);
				g.setColor(Color.black);
			}else {
				g.drawString("Player " + 1, 1050, 25);
				float healthBar = ((float) player.getCurrent_health() / (float) player.getMax_health()) * barWidth;
				g.drawString("Health", 1050, 50);
				g.fillRect(1050, 75, barWidth, barHeight);
				g.setColor(Color.red);
				g.fillRect(1050, 75, healthBar, barHeight);
				g.setColor(Color.black);
			}
			
		}
		
	}
	
	public void renderMobs(Graphics g) {
		// Render the mobs
		for(DaMob mob: currentGame.getMobList()) {
			if((mob.getX()/tileWidth) > this.xOffSet && (mob.getY()/tileHeight) > this.yOffSet ) {	
				if(mob.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
					mob.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		
		// Render the mini boss (it's basically a higher diff mob)
		DaMiniBoi miniBoss = currentGame.getMiniBoss();
		if((miniBoss.getX()/tileWidth) > this.xOffSet && (miniBoss.getY()/tileHeight) > this.yOffSet ) {	
			if(miniBoss.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
				g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
				miniBoss.render(g);
				g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
			}
		}
		
		// Render the boss
		DaBoi boss = currentGame.getBoss();
		if((boss.getX()/tileWidth) > this.xOffSet && (boss.getY()/tileHeight) > this.yOffSet ) {	
			if(boss.getX() < 1024 + (xOffSet*DaMap.TILE_SIZE)) {
				g.translate(-xOffSet*DaMap.TILE_SIZE,-yOffSet*DaMap.TILE_SIZE);
				boss.render(g);
				g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
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
	public void renderItems(Graphics g) {
		for(DaChest chest: currentGame.getChestList()) {
			if((chest.getX()/tileWidth) >= this.xOffSet - 1 && (chest.getY()/tileHeight) >= this.yOffSet-1) {
				if(chest.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					chest.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		for(DaGold gold: currentGame.getGoldList()) {
			if((gold.getX()/tileWidth) >= this.xOffSet - 1 && (gold.getY()/tileHeight) >= this.yOffSet-1) {
				if(gold.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					gold.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		for(DaGoldPouch goldPouch: currentGame.getgoldPouchList()) {
			if((goldPouch.getX()/tileWidth) >= this.xOffSet - 1 && (goldPouch.getY()/tileHeight) >= this.yOffSet-1) {
				if(goldPouch.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					goldPouch.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
		for(DaPickup pickUp: currentGame.getPickupList()) {
			if((pickUp.getX()/tileWidth) >= this.xOffSet - 1 && (pickUp.getY()/tileHeight) >= this.yOffSet-1) {
				if(pickUp.getX() < 1024 + (xOffSet * DaMap.TILE_SIZE)) {
					g.translate(-xOffSet*DaMap.TILE_SIZE, -yOffSet*DaMap.TILE_SIZE);
					pickUp.render(g);
					g.translate(xOffSet*DaMap.TILE_SIZE, yOffSet*DaMap.TILE_SIZE);
				}
			}
		}
	}

}
