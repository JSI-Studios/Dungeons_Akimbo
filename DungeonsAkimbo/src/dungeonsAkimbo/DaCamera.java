package dungeonsAkimbo;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

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
	
	public void renderMap() {
		currentGame.getTiledMap().render(0, 0);
	}

}