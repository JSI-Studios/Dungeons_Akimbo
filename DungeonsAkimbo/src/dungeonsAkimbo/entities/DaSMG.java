package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaSMG extends Entity implements Ranged {
	private int ammo, maxAmmo, shootTimer, range, damage, reloadTimer, maxReloadTimer;
	final int maxTimer;
	private boolean canShoot, isReloading;
	private Animation sprite;
	
	public DaSMG () {
		ammo = 20;
		maxAmmo = 20;
		shootTimer = 50;
		reloadTimer = 1000;
		maxReloadTimer = 1000;
		maxTimer = 50;
		range = 800;
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
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SMG_RSC).getScaledCopy(.075f), 1);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SMG_RSC).getScaledCopy(.075f).getFlippedCopy(false, true), 1);
		this.addAnimation(sprite);
	}
	
	public Object primaryAtk(double inAngle) {
		
		Projectile bullet;
		
		bullet = new Projectile(this.getX(), this.getY(), this.damage, this.range);
		bullet.rotate(inAngle);
		bullet.Set_Velocity(inAngle);
		this.ammo--;
		this.canShoot = false;
		return bullet;
	}
	
	public void getEffect() {

	}
	
	public void setMaxReloadTimer(int i) {
		this.maxReloadTimer = i;
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
	
	public void setAmmo(int inAmmo) {
		this.ammo = inAmmo;
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

}
