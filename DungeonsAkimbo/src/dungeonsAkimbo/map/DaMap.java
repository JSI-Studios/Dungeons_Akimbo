package dungeonsAkimbo.map;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class DaMap implements TileBasedMap{
	
	//Map variables
	private int mapWidth;  //map width in number of tiles
	private int mapHeight; //map height in number of tiles
	
	//Tile variables
	private int tileWidth;	//a single tile's width in pixels
	private int tileHeight; //a single tile's height in pixels
	
	//Entities and tiles
	private TiledMap currentMap;
	private DaTile[][] tiles; //2d array of tile entities
	
	//PathFinding variables
	private boolean visited;
	
	public DaMap(TiledMap map) {
		this.currentMap = map;
		this.mapWidth = map.getWidth();
		this.mapHeight = map.getHeight();
		this.tileWidth = map.getTileWidth();
		this.tileHeight = map.getTileHeight();
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return mapWidth;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return mapHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public TiledMap getTiledMap() {
		return currentMap;
	}
	
	
	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		// TODO Auto-generated method stub
		return 0;
	}

}
