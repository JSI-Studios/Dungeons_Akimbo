package dungeonsAkimbo.entities;


public interface Weapon {
	
	public Object primaryAtk(double inAngle);		//Primary attack of weapon, returns a Swipe or projectile based on melee/ranged
	
	public Object secondaryAtk(double inAngle);		//Secondary attack of weapon, returns a Swipe or projectile based on melee/ranged
	
	public void getEffect();						//get weapon effect if it has one. IE. fire, freeze, slow, posion, etc.
	
	public void throwWep();							//Throw current weapon.
	
	public void update(final int delta);


}
