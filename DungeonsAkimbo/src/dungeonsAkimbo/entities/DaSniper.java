package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaSniper extends Entity implements Weapon {
	
	private int ammo, shoot_timer, range, damage;
	private boolean can_shoot;
	
	public DaSniper() {
		ammo = 20;
		shoot_timer = 30;
		range = 20;
		damage = 40;
		can_shoot = true;
		
		//add in art
	}
	
	public Vector Get_Position() { return this.getPosition(); };
	
	public void Set_Position(float x, float y) { this.setPosition(x, y); };

	@Override
	public Projectile Attack(double inAngle) {
		
		// TODO Auto-generated method stub
		this.can_shoot = false;
		
		Projectile bullet = new Projectile(this.getX(), this.getY(), this.damage);

		bullet.rotate(inAngle);
		bullet.Set_Velocity(inAngle);
		return bullet;
		
	}
	
	public int Get_Range() {
		return this.range;
	}
	
	public void Decrement_Ammo() {
		this.ammo--;
	}
	
	public int Get_Ammo() {
		return this.ammo;
	}
	
	public int Get_Damage() {
		return this.damage;
	}

	@Override
	public void Throw_Wep() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(final int delta) {
		
		if (this.can_shoot == false) {
			shoot_timer -= delta;
			//System.out.println("Player is dodge? " + this.dodging);
		}
		
		if (shoot_timer <= 0) {
			this.can_shoot = true;
			shoot_timer = 30;	
		}
		 
	}

}
