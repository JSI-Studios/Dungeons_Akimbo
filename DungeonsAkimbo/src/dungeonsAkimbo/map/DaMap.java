package dungeonsAkimbo.map;

import java.util.ArrayList;

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
	private ArrayList<DaTile> tileList;
	private DaTile[][] tiles; //2d array of tile entities
	
	//PathFinding variables
	private Boolean[][] visited;
	
	private final int TILE_SIZE = 32;
	
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
	
	public ArrayList<DaTile> getTileList(){
		return tileList;
	}
	
	
	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		visited[x][y] = true;
		
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
	
	public int getTileSize() {
		return TILE_SIZE;
	}
	
	public void loadMap() {
		loadNewMap(currentMap);
	}
	
	public void loadNewMap(TiledMap mapPlan) {
		mapWidth = mapPlan.getWidth();
		mapHeight = mapPlan.getHeight();
		
		tileWidth = mapPlan.getTileWidth();
		tileHeight = mapPlan.getTileHeight();
		
		tiles = new DaTile[mapWidth][mapHeight];
		tileList = new ArrayList<DaTile>();
		visited = new Boolean[mapWidth][mapHeight];
		
		for(int xTile = 0; xTile < mapWidth; xTile++) {
			for(int yTile = 0; yTile < mapHeight; yTile++) {
				DaTile tile = new DaTile(xTile * TILE_SIZE, yTile * TILE_SIZE, mapPlan.getTileId(xTile, yTile, 0), mapPlan.getTileImage(xTile, yTile, 0));
				tileList.add(tile);
				tiles[xTile][yTile] = tile;
			}
		}
		
		
	}

}
