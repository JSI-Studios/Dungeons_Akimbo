package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public interface Weapon {
	
	public Projectile Attack(double inAngle);
	
	public Vector Get_Position();
	
	public void Set_Position(float x, float y);
	
	public int Get_Range();
	
	public void Decrement_Ammo();
	
	public int Get_Ammo();
	
	public int Get_Damage();
	
	public void Throw_Wep();
	
	public void update(final int delta);


}
