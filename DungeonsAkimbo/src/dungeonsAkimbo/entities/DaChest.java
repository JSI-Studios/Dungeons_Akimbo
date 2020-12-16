package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaChest extends Entity {
	private int contentsGroup;
	private boolean state;
	private Image open, close;
	
	public DaChest(float px, float py, int contents) {
		super(px,py);
		this.contentsGroup = contents;
		this.state = false;
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		this.close = miscSheet.getSprite(5, 107);
		this.open = miscSheet.getSprite(5, 108);
		
		this.addImageWithBoundingBox(close);
		
		//todo: rig up open/close sprites
	}
	
	
	public int[] open() {
		int[] contents = null;
		
		if (!this.state) {
			contents = getContents(this.contentsGroup);
			this.removeImage(close);
			this.addImage(open);
			this.state = true;
			
			}
		
		return contents;
	}


	private int[] getContents(int contentsGroup) {
		// TODO return an array with ints representing objects ie. 1=health 2=ammo 3=powerup 4=gold bag 5=gold pieces
		if(contentsGroup == 0) {
			return new int[] {1,1,2};
		}else if (contentsGroup == 1) {
			return new int[] {1,2,2};
		}else if (contentsGroup == 2) {
			return new int[] {2,2};
		}else if (contentsGroup == 3) {
			return new int[] {3,5,5};
		}else if (contentsGroup == 4) {
			return new int[] {5,4,4};
		}
		
		return null;
	}
}
