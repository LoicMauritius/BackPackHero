package fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements;

import java.nio.file.Path;
import java.util.Objects;

/**
 * The Sword class of this game.
 * It permit to create a sword for fight
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public record Sword(String Name, int Size , int Damage, int Rarity, int ActionCost, Path image) implements MeleeWeapon{

	/**
	 * The Sword constructor.
	 * It permit to create a sword for fight
	 * 
	 * @param String Name			The name of the sword
	 * @param int Size				The size of the sword ( the number of case it will take )
	 * @param int Damage			The it will make to enemies
	 * @param int Rarity			The rarity of the weapon (between 1 and 5)
	 * @param int ActionCost				The actionPoint it cost to the hero
	 */
	public Sword(String Name, int Size , int Damage, int Rarity, int ActionCost, Path image) {

		this.Name = Objects.requireNonNull(Name);
		
		if(Size > 0) {
			this.Size = Size;
		}else {
			throw new IllegalArgumentException("La taille d'une arme doit être d'au moins 1.");
		}
		
		if(Damage > 0) {
			this.Damage = Damage;
		}else {
			throw new IllegalArgumentException("Les dommages infligés par une épée ne peuvent pas être inférieur à 1.");
		}
		
		if(0 < Rarity && Rarity <= 5) {
			this.Rarity = Rarity;
		}else {
			throw new IllegalArgumentException("La rareté doit être comprise entre 1 et 5.");
		}
		
		if(ActionCost > 0) {
			this.ActionCost = ActionCost;	
		}else {
			throw new IllegalArgumentException("Le coût en action d'une arme ne peut être inférieur à 0.");
		}
		
		this.image = Objects.requireNonNull(image);
		
	}

	@Override
	public String getName() {
		return Name;
	}

	@Override
	public int getSize() {
		return Size;
	}

	@Override
	public int getDamage() {
		return Damage;
	}

	@Override
	public int getRarity() {
		return Rarity;
	}

	@Override
	public int getActionCost() {
		return ActionCost;
	}

}
