package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaPistol extends Entity implements Ranged {
	private int ammo, shoot_timer, range, damage;
	final int maxTimer;
	private boolean canShoot;
	private Animation sprite;
	
	public DaPistol() {
		ammo = 20;
		shoot_timer = 500;
		maxTimer = 500;
		range = 1500;
		damage = 40;
		canShoot = true;

		/**
		 * Add an animation to handle flipping of the sprite with rotation.
		 * This is due to the fact that using addImage() and removeImage()
		 * do not work quickly enough or compensate for rotations applied to the 
		 * image after it is added.
		 * 
		 * Workaround: Add an animation with autoUpdate set to false, right sprite
		 * is added at frame 0 and left sprite is added at frame 1. we swap between
		 * frames based on our rotation in update().
		 */
		sprite = new Animation(false);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_PISTOL_RSC).getScaledCopy(.075f), 1);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_PISTOL_RSC).getScaledCopy(.075f).getFlippedCopy(false, true), 1);
		this.addAnimation(sprite);
	}
	
	public Object primaryAtk(double inAngle) {
		
		Projectile bullet;
		
		bullet = new Projectile(this.getX(), this.getY(), this.damage, this.range);
		bullet.rotate(inAngle);
		bullet.Set_Velocity(inAngle);
		
		this.canShoot = false;
		return bullet;
	}
	
	public void getEffect() {

	}
	
	public boolean isCan_shoot() {
		return canShoot;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public int getAmmo() {
		return this.ammo;
	}
	
	public void throwWep() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Projectile secondaryAtk(double inAngle) {
		return null;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}
	
	public void update(final int delta) {

		if (this.canShoot == false) {
			shoot_timer -= delta;
			// System.out.println("Player is dodge? " + this.dodging);
		}

		if (shoot_timer <= 0) {
			this.canShoot = true;
			shoot_timer = this.maxTimer;
		}
		//Print current rotation to console.
		//System.out.println(this.getRotation());
		if(Math.abs(this.getRotation()) < 90) {
			this.sprite.setCurrentFrame(0);
			
		}else if(Math.abs(this.getRotation()) > 90){
			this.sprite.setCurrentFrame(1);			
		}
		
	}
}
