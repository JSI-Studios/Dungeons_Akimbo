package dungeonsAkimbo.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaShotty extends Entity implements Ranged {
	private int ammo, maxAmmo, shootTimer, range, damage, reloadTimer, maxReloadTimer;
	double angle;
	final int maxTimer;
	private boolean canShoot, isReloading;
	private Animation sprite;
	
	public DaShotty() {
		ammo = 5;
		maxAmmo = 5;
		shootTimer = 300;
		reloadTimer = 2000;
		maxReloadTimer = 2000;
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
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SHOTTY_RSC).getScaledCopy(.075f), 1);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SHOTTY_RSC).getScaledCopy(.075f).getFlippedCopy(false, true), 1);
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
		this.ammo--;
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

		if (this.ammo <= 0) {
			this.isReloading = true;
		}
		
		if (this.isReloading == true) {
			this.reloadTimer -= delta;
		}
		
		if (this.reloadTimer <= 0) {
			this.ammo = this.maxAmmo;
			this.isReloading = false;
			this.reloadTimer = this.maxReloadTimer;
		}

		if (this.canShoot == false) {
			this.shootTimer -= delta;
			// System.out.println("Player is dodge? " + this.dodging);
		}
	
		if (this.isReloading == false) {
			if (this.ammo > 0) {
				if (this.shootTimer <= 0) {
					this.canShoot = true;
					this.shootTimer = this.maxTimer;
				}
			}
		}
		//Print current rotation to console.
		//System.out.println(this.getRotation());
		if(Math.abs(this.getRotation()) < 90) {
			this.sprite.setCurrentFrame(0);
			
		}else if(Math.abs(this.getRotation()) > 90){
			this.sprite.setCurrentFrame(1);			
		}
		
	}

	@Override
	public void primaryAtk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAmmo(int inAmmo) {
		this.ammo = inAmmo;		
	}
}
