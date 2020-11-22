package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;

public class DaSniper extends Entity implements Ranged {

	private int ammo, shoot_timer, range, damage;
	private boolean can_shoot;
	private Image leftGunImage, rightGunImage;

	public DaSniper() {
		ammo = 20;
		shoot_timer = 2000;
		range = 20;
		damage = 40;
		can_shoot = true;

		leftGunImage = ResourceManager.getImage(DungeonsAkimboGame.DA_SNIPER_RSC).getScaledCopy(.075f).getFlippedCopy(false, true);
		rightGunImage = ResourceManager.getImage(DungeonsAkimboGame.DA_SNIPER_RSC).getScaledCopy(.075f);
	
		
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
		
		System.out.println(Math.abs(this.getRotation()));
		if(Math.abs(this.getRotation()) < 90) {
			leftGunImage.setRotation((float) this.getRotation());
			this.removeImage(leftGunImage);
			this.addImage(rightGunImage);	
			
		}else if(Math.abs(this.getRotation()) > 90){
			rightGunImage.setRotation((float) this.getRotation());
			this.removeImage(rightGunImage);
			this.addImage(leftGunImage);			
		}
		
	}

}
