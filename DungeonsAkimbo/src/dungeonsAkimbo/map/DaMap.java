package dungeonsAkimbo.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import dungeonsAkimbo.DungeonsAkimboGame;
import dungeonsAkimbo.entities.DaBoi;
import dungeonsAkimbo.entities.DaMiniBoi;
import dungeonsAkimbo.entities.DaChest;
import dungeonsAkimbo.entities.DaGold;
import dungeonsAkimbo.entities.DaGoldPouch;
import dungeonsAkimbo.entities.DaMob;
import dungeonsAkimbo.entities.DaPickup;
import dungeonsAkimbo.entities.Player;
import dungeonsAkimbo.entities.Projectile;

public class DaMap implements TileBasedMap{
		
	//Map variables
	private int mapWidth;  //map width in number of tiles
	private int mapHeight; //map height in number of tiles
	
	//Tile variables
	private int tileWidth;	//a single tile's width in pixels
	private int tileHeight; //a single tile's height in pixels
	
	//Tiles
	private TiledMap currentMap;
	private ArrayList<DaTile> tileList;
	private ArrayList<DaWall> wallList;
	private DaTile[][] tiles; //2d array of tile entities
	
	// Game entities
	private ArrayList<DaChest> chests;
	private ArrayList<DaGold> gold;
	private ArrayList<DaGoldPouch> moreGold;
	private ArrayList<DaPickup> pickups;
	private ArrayList<DaMob> mobs;
	private ArrayList<Projectile> player_bullets;
	private ArrayList<Projectile> enemyAttacks;
	private Map<Integer, Player> playerList;
	private DaMiniBoi miniBoss;
	private DaBoi boss;
	
	//PathFinding variables
	private Boolean[][] visited;
	
	public final static int TILE_SIZE = 32;
	
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
	
	public ArrayList<DaWall> getWallList() {
		// TODO Auto-generated method stub
		return wallList;
	}
	
	public ArrayList<DaMob> getMobList(){
		return mobs;
	}
	
	public ArrayList<DaChest> getChestList() {
		return chests;
	}
	
	
	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		visited[x][y] = true;
		
	}
	
	public void clearVisited() {
        for(int x=0; x < getWidthInTiles(); x++) {
            for (int y = 0; y < getHeightInTiles(); y++) {
                visited[x][y] = false;
            }
        }
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
	
	public Map<Integer, Player> getPlayerList() {
		// TODO Auto-generated method stub
		return playerList;
	}
	
	public ArrayList<Projectile> getPlayer_bullets() {
		return player_bullets;
	}
	
	public void addNewPlayer(int playerID) {
		Player player = new Player(DungeonsAkimboGame.WIDTH / 2, DungeonsAkimboGame.HEIGHT / 3, 1);
		playerList.put(playerID, player);		
	}
	
	public void loadNewMap(TiledMap mapPlan) {
		mapWidth = mapPlan.getWidth();
		mapHeight = mapPlan.getHeight();
		
		tileWidth = mapPlan.getTileWidth();
		tileHeight = mapPlan.getTileHeight();
		
		tiles = new DaTile[mapWidth][mapHeight];
		tileList = new ArrayList<DaTile>();
		wallList = new ArrayList<DaWall>();
		
		playerList = new HashMap<Integer, Player>();
		player_bullets = new ArrayList<Projectile>();
		this.setEnemyAttacks(new ArrayList<Projectile>());
		mobs = new ArrayList<DaMob>();
		chests = new ArrayList<DaChest>();
		gold = new ArrayList<DaGold>();
		moreGold = new ArrayList<DaGoldPouch>();
		pickups = new ArrayList<DaPickup>();
		
		
		visited = new Boolean[mapWidth][mapHeight];
		
		for(int xTile = 0; xTile < mapWidth; xTile++) {
			for(int yTile = 0; yTile < mapHeight; yTile++) {
				DaTile tile = new DaTile(xTile * TILE_SIZE, yTile * TILE_SIZE, mapPlan.getTileId(xTile, yTile, 0), mapPlan.getTileImage(xTile, yTile, 0));
				tileList.add(tile);
				tiles[xTile][yTile] = tile;
			}
		}
		
		for(int xTile = 0; xTile < mapWidth; xTile++) {
			for(int yTile = 0; yTile < mapHeight; yTile++) {
				if(mapPlan.getTileId(xTile, yTile, 1) != 0) {
					DaWall wall = new DaWall(xTile * TILE_SIZE, yTile * TILE_SIZE, mapPlan.getTileId(xTile, yTile, 1), mapPlan.getTileImage(xTile, yTile, 1));
					wallList.add(wall);
				}
			}
		}
		
		mobs.add(new DaMob(DungeonsAkimboGame.WIDTH / 4, DungeonsAkimboGame.HEIGHT / 4, 0, true));
		mobs.add(new DaMob(DungeonsAkimboGame.WIDTH / 3, DungeonsAkimboGame.HEIGHT / 3, 1, true));
		mobs.add(new DaMob(DungeonsAkimboGame.WIDTH / 3, DungeonsAkimboGame.HEIGHT / 3 + 100, 2, true));
		mobs.add(new DaMob(DungeonsAkimboGame.WIDTH / 5, DungeonsAkimboGame.HEIGHT / 5, 3, true));
		chests.add(new DaChest((DungeonsAkimboGame.WIDTH / 3) + 32*3, (DungeonsAkimboGame.HEIGHT / 3) + 32*4, 0));
		gold.add(new DaGold((DungeonsAkimboGame.WIDTH / 3) + 32*5, (DungeonsAkimboGame.HEIGHT / 3) + 32*7));
		moreGold.add(new DaGoldPouch((DungeonsAkimboGame.WIDTH / 3) + 32*5, (DungeonsAkimboGame.HEIGHT / 3) + 32*8));
		pickups.add(new DaPickup((DungeonsAkimboGame.WIDTH / 3) + 32*5, (DungeonsAkimboGame.HEIGHT / 3) + 32*9, 0));
		pickups.add(new DaPickup((DungeonsAkimboGame.WIDTH / 3) + 32*5, (DungeonsAkimboGame.HEIGHT / 3) + 32*10, 1));
		pickups.add(new DaPickup((DungeonsAkimboGame.WIDTH / 3) + 32*5, (DungeonsAkimboGame.HEIGHT / 3) + 32*11, 2));
		
		// Begin including mini boss
		setMiniBoss(new DaMiniBoi(DungeonsAkimboGame.WIDTH / 2, DungeonsAkimboGame.HEIGHT / 2, true));
		// Begin including boss
		setBoss(new DaBoi(DungeonsAkimboGame.WIDTH / 3 + 100, DungeonsAkimboGame.HEIGHT / 3 + 100, true));
	}

	public ArrayList<Projectile> getEnemyAttacks() {
		return enemyAttacks;
	}

	public void setEnemyAttacks(ArrayList<Projectile> enemyAttacks) {
		this.enemyAttacks = enemyAttacks;
	}

	public DaMiniBoi getMiniBoss() {
		return miniBoss;
	}
	

	public void setMiniBoss(DaMiniBoi miniBoss) {
		this.miniBoss = miniBoss;
	}

	public ArrayList<DaGold> getGoldList() {
		// TODO Auto-generated method stub
		return gold;
	}

	public ArrayList<DaGoldPouch> getgoldPouchList() {
		// TODO Auto-generated method stub
		return moreGold;
	}

	public ArrayList<DaPickup> getPickupList() {
		// TODO Auto-generated method stub
		return pickups;
	}


	

	public DaBoi getBoss() {
		return boss;
	}

	public void setBoss(DaBoi boss) {
		this.boss = boss;
	}

}
