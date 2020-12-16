package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaAssault extends Entity implements Ranged{

	private int ammo, maxAmmo, burstAmount, maxBurstAmount, firingTimer, maxfiringTimer, shootTimer, maxShootTimer, range, damage, reloadTimer, maxReloadTimer;

	double angle;
	
	private boolean canShoot, canFireBullet, isBursting, shooted, isReloading;

	private Animation sprite;
	DungeonsAkimboGame dag;
	
	public DaAssault() {
		this.ammo = 24;
		this.maxAmmo = 24;
		this.burstAmount = 3;
		this.maxBurstAmount = 3;
		this.firingTimer = 30;
		this.maxfiringTimer = 30;
		this.shootTimer = 1000;
		this.maxShootTimer = 1000;
		this.reloadTimer = 1000;
		this.maxReloadTimer = 1000;
		this.range = 1500;
		this.damage = 40;
		
		this.canShoot = false;


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
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_ASSAULT_RSC).getScaledCopy(.175f), 1);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_ASSAULT_RSC).getScaledCopy(.175f).getFlippedCopy(false, true), 1);
		this.addAnimation(sprite);
	}
	
	@Override
	public void primaryAtk() {
		this.canShoot = false;
		this.shooted = true;
		//this.isBursting = true;
	}
	
	
	public void getEffect() {

	}
	
	public boolean isCan_shoot() {
		return canShoot;
	}
	
	public boolean isCanFireBullet() {
		return canFireBullet;
	}
	
	public boolean setIsBursting(boolean isBursting) {
		return this.isBursting = isBursting;
	}

	public void setCanFireBullet(boolean canFireBullet) {
		this.canFireBullet = canFireBullet;
	}

	public int getBurstAmount() {
		return burstAmount;
	}

	public void setBurstAmount(int burstAmount) {
		this.burstAmount = burstAmount;
	}
	
	public int getfiringTimer() {
		return firingTimer;
	}

	public void setfiringTimer(int firingTimer) {
		this.firingTimer = firingTimer;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public int getAmmo() {
		return this.ammo;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	
	public void decrementBurstAmount() {
		this.burstAmount--;
		this.ammo--;
	}
	
	public void decrementAmmo() {
		this.ammo--;
	}
	
	public void throwWep() {
		// TODO Auto-generated method stub

	}
	
	public Projectile secondaryAtk(double inAngle) {
		return null;
	}

	public void reload() {
		// TODO Auto-generated method stub

	}
	
	public boolean isReloading() {
		return isReloading;
	}

	public void setReloading(boolean isReloading) {
		this.isReloading = isReloading;
	}
	
	public void update(final int delta) {
		
		
		if ( !this.canShoot && this.burstAmount > 0 && this.isBursting && this.shooted) {		
			this.canFireBullet = true;
		}
		
		if (!this.isBursting) {
			this.firingTimer -= delta;
		}
		
		if (this.firingTimer <= 0) {
			this.isBursting = !this.isBursting;
			this.firingTimer = this.maxfiringTimer;
		}

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
		}
	
		if (this.isReloading == false) {
			if (this.ammo > 0) {
				if (this.shootTimer <= 0) {
					this.canShoot = true;
					this.burstAmount = this.maxBurstAmount;
					this.shootTimer = this.maxShootTimer;
				}
			}
		}

		if(Math.abs(this.getRotation()) < 90) {
			this.sprite.setCurrentFrame(0);
			
		}else if(Math.abs(this.getRotation()) > 90){
			this.sprite.setCurrentFrame(1);			
		}
		
	}

	public Object primaryAtk(double inAngle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAmmo(int inAmmo) {
		this.ammo = inAmmo;		
	}
}
