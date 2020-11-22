package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaSniper extends Entity implements Ranged {

	private int ammo, shoot_timer, range, damage;
	private boolean can_shoot;
	private Animation sprite;

	public DaSniper() {
		ammo = 20;
		shoot_timer = 2000;
		range = 20;
		damage = 40;
		can_shoot = true;

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
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SNIPER_RSC).getScaledCopy(.075f), 1);
		sprite.addFrame(ResourceManager.getImage(DungeonsAkimboGame.DA_SNIPER_RSC).getScaledCopy(.075f).getFlippedCopy(false, true), 1);
		this.addAnimation(sprite);
		
	}
	
	public boolean isCan_shoot() {
		return can_shoot;
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

	@Override
	public void throwWep() {
		// TODO Auto-generated method stub

	}

	public Projectile primaryAtk(double inAngle) {
		
		Projectile bullet;
		
		bullet = new Projectile(this.getX(), this.getY(), this.damage);
		bullet.rotate(inAngle);
		bullet.Set_Velocity(inAngle);
		
		this.can_shoot = false;
		return bullet;
	}

	@Override
	public Projectile secondaryAtk(double inAngle) {
		return null;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	public void getEffect() {

	}
	
	public void update(final int delta) {

		if (this.can_shoot == false) {
			shoot_timer -= delta;
			// System.out.println("Player is dodge? " + this.dodging);
		}

		if (shoot_timer <= 0) {
			this.can_shoot = true;
			shoot_timer = 2000;
		}
		//Print current rotation to console.
		//System.out.println(Math.abs(this.getRotation()));
		if(Math.abs(this.getRotation()) < 90) {
			this.sprite.setCurrentFrame(0);
			
		}else if(Math.abs(this.getRotation()) > 90){
			this.sprite.setCurrentFrame(1);			
		}
		
	}

}
