package fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements;

import java.nio.file.Path;
import java.util.Objects;

public record HealthPotion(String Name, int Size , int Recovery, int Uses, Path image) implements Consumable{

	public HealthPotion(String Name, int Size , int Recovery, int Uses, Path image) {
		this.Name = Objects.requireNonNull(Name);
		
		if(Size > 0) {
			this.Size = Size;
		}else {
			throw new IllegalArgumentException("La taille d'un consommable doit être d'au moins 1.");
		}
		
		if(Recovery > 0) {
			this.Recovery = Recovery;
		}else {
			throw new IllegalArgumentException("Le recouvrement de mana ne peuvent pas être inférieur à 1.");
		}
		
		if(Uses >= 0) {
			this.Uses = Uses;	
		}else {
			throw new IllegalArgumentException("Le coût en mana d'un consommable ne peut être inférieur à 0.");
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
	public Path image() {
		return image;
	}

	@Override
	public int getUses() {
		return Uses;
	}

	public int getRecovery() {
		return Recovery;
	}
	
}
