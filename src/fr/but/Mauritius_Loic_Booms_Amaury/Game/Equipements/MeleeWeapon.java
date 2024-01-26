package fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements;

public interface MeleeWeapon extends Items{
	
	public String getName();
	public int getSize();
	
	public int getDamage();
	public int getRarity();
	public int getActionCost();
}
