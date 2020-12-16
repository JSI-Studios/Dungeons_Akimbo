package dungeonsAkimbo.entities;

import jig.Vector;

public interface Ranged extends Weapon{
	
	
	public void reload();		//reload weapon
	
	public int getAmmo();		//get current magazine ammo count
	
	public int getRange();		//get maximum range of this Ranged Weapon
	
	public boolean isCan_shoot();
	
	public void setAmmo(int inAmmo);

}
