package dungeonsAkimbo.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaShotty extends Entity implements Ranged {
	private int ammo, shoot_timer, range, damage;
	double angle;
	final int maxTimer;
	private boolean canShoot;
	private Animation sprite;
	
	public DaShotty() {
		ammo = 20;
		shoot_timer = 500;
		maxTimer = 500;
		range = 400;
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
		
		this.angle = inAngle;
		
		ArrayList<Projectile> bulletArray = new ArrayList<Projectile>();
		Projectile bullet = new Projectile(this.getX(), this.getY(), this.damage, this.range);
		Projectile bullet2 = new Projectile(this.getX(), this.getY(), this.damage, this.range);
		//Projectile bullet3 = new Projectile(this.getX(), this.getY(), this.damage, this.range);
		
		for (int i = 0; i <= 3; i++) {
			bullet = new Projectile(this.getX(), this.getY(), this.damage, this.range);
			bullet2 = new Projectile(this.getX(), this.getY(), this.damage, this.range);
			bullet.rotate(this.angle);
			bullet2.rotate(this.angle);
			bullet.Set_Velocity(this.angle + i*10);
			bullet2.Set_Velocity(this.angle - i*10);
			//bullet3.Set_Velocity(this.angle);
			bulletArray.add(bullet);
			bulletArray.add(bullet2);
		}
		
		this.canShoot = false;
		return bulletArray;
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
