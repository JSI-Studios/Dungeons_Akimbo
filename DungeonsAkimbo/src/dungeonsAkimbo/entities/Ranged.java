package dungeonsAkimbo.entities;

public interface Ranged extends Weapon{
	
	public Object primaryFire(double inAngle);
	
	public Object secondaryFire(double inAngle);
	
	public void reload();
	
	public int getAmmo();
	
	public int getRange();

}
