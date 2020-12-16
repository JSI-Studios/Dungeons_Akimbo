package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Projectile extends Entity {
	
	private Vector velocity;
	private float speed;
	private int damage;
	private int timer;
	private boolean isMelee;
	private int spriteType;
	
	private Animation attackAnimation;
	private SpriteSheet attackSprite;
	
	public Projectile (final float x, final float y, int damage) {
		super(x, y);
		this.speed = 0.5f;
		this.setDamage(damage);
		this.setTimer(90000);
		this.isMelee = false;
		this.attackAnimation = null;
		this.attackSprite = null;
		this.setSpriteType(-1);
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	public Projectile(final float x, final float y, int damage, int timer) {
		super(x, y);
		this.speed = 0.5f;
		this.setDamage(damage);
		this.setTimer(timer);
		this.isMelee = false;
		this.attackAnimation = null;
		this.attackSprite = null;
		this.setSpriteType(-1);
		// Deal with changing sprite later for different melee sprites
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	// Most descriptive constructor without custom sprites
	public Projectile (final float x, final float y, int damage, int speed, int timer, boolean isMelee) {
		super(x, y);
		this.setDamage(damage);
		this.speed = speed;
		this.setTimer(timer);
		this.isMelee = isMelee;
		this.attackAnimation = null;
		this.attackSprite = null;
		this.setSpriteType(-1);
		// Deal with changing sprite later for different melee sprites
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	public Projectile(final float x, final float y, int damage, int timer, int spriteType) {
		super(x, y);
		this.setDamage(damage);
		this.timer = timer;
		this.speed = 0.1f;
		this.setSpriteType(spriteType);
		// Add hitbox of a projectile
		if(spriteType == 1) {
			this.attackSprite = ResourceManager.getSpriteSheet(DungeonsAkimboGame.BANG, 64, 64);
			Image temp = attackSprite.getSprite(0, 0);
			this.addImageWithBoundingBox(temp);
			this.removeImage(temp);
			// Use spriteType to switch attack animation
			this.attackAnimation = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.BANG, 64, 64), 0, 0, 22, 0, true, 25, true);
			addAnimation(this.attackAnimation);
			this.attackAnimation.setLooping(false);
		} else if(spriteType == 2) {
			Image[] frames = {
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_ONE),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_TWO),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_THREE),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_FOUR),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_FIVE)};
			this.attackAnimation = new Animation(true);
			this.attackSprite = null;
			Image temp = frames[0];
			this.addImageWithBoundingBox(temp);
			this.removeImage(temp);
			// iterate through frames to animate
			for(int i = 0; i < frames.length; i++) {
				this.attackAnimation.addFrame(frames[i], 80);
			}
			this.attackAnimation.setLooping(false);
			addAnimation(this.attackAnimation);
		} else {
			this.attackAnimation = null;
			this.attackSprite = null;
		}
	}	
	
	public Projectile(final float x, final float y, int damage, int timer, float speed, int angle, int spriteType) {
		super(x, y);
		this.setDamage(damage);
		this.timer = timer;
		this.speed = speed;
		this.setSpriteType(spriteType);
		// Add hitbox of a projectile
		if(spriteType == 1) {
			this.attackSprite = ResourceManager.getSpriteSheet(DungeonsAkimboGame.BANG, 64, 64);
			Image temp = attackSprite.getSprite(0, 0);
			this.addImageWithBoundingBox(temp);
			this.removeImage(temp);
			// Use spriteType to switch attack animation
			this.attackAnimation = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.BANG, 64, 64), 0, 0, 22, 0, true, 25, true);
			addAnimation(this.attackAnimation);
			this.attackAnimation.setLooping(false);
		} else if(spriteType == 2) {
			Image[] frames = {
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_ONE),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_TWO),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_THREE),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_FOUR),
					ResourceManager.getImage(DungeonsAkimboGame.MAGIC_FIVE)};
			this.attackAnimation = new Animation(true);
			this.attackSprite = null;
			Image temp = frames[0];
			this.addImageWithBoundingBox(temp);
			this.removeImage(temp);
			// iterate through frames to animate
			for(int i = 0; i < frames.length; i++) {
				this.attackAnimation.addFrame(frames[i], 120);
			}
			this.attackAnimation.setLooping(false);
			addAnimation(this.attackAnimation);
		} else {
			this.attackAnimation = null;
			this.attackSprite = null;
		}
		this.Set_Velocity(angle);
	}
	
	public void decreaseTimer(final int delta) {
		// Dedicated function that decrements the duration of the projectile
		this.timer -= delta;
	}
	
	public void Set_Velocity(double inAngle) {
		velocity = Vector.getVector(inAngle, this.speed);
		
	}
	
	public int Get_Damage() {
		return this.getDamage();
	}
	
	public void update(final int delta) {
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
//			System.out.println("caught exception when trying to translate player velocity" + e);
		} 
	}

	public boolean isActive() {
		if(this.attackAnimation != null) {
			return !this.attackAnimation.isStopped();
		}
		return false;
	}
	
	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
