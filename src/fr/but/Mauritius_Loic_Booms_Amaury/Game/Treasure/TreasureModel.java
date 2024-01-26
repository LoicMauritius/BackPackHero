package fr.but.Mauritius_Loic_Booms_Amaury.Game.Treasure;

import java.nio.file.Files;

import java.nio.file.Path;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import java.util.Random;

import fr.but.Mauritius_Loic_Booms_Amaury.Game.Dimensions;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.GameController;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.GameView;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Inventory;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Items;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Position;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.HealthPotion;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Sword;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_room;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Hero.HeroModel;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.Event.Action;

/**
 * The TreasureEvent class of this game.
 * It permit to create the theoretical model of treasure situation when you step on it by the map
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public final record TreasureModel() {	

	//List of Items you can have on the treasure
	public static final Sword Wooden_Sword = new Sword("Wooden Sword", 3, 7, 1, 1,
			Path.of("img","Wooden_sword.png"));
	public static final Sword Slime_Sword = new Sword("Slime Sword", 2, 6, 2, 1,
			Path.of("img","Slime_Sword.png"));
	public static final Sword Key_Sword = new Sword("Key Sword", 2, 9, 3, 2, 
			Path.of("img","Key_Sword.png"));
	public static final Sword Amaury_Blade = new Sword("Amaury's Blade", 3, 100, 5, 3,
			Path.of("img","Amaury_Sword.png"));
	
	public static final HealthPotion Regen_potion = new HealthPotion("ManaPotion", 1, 5, 1, Path.of("img","healthpotion.png"));
	
	/**
	 * This method permit to return a random item
	 * 
	 * @param inventory 
	 * 
	 * @return Items item			An item to put on the backpack
	 */
	public static Items chooseItem(Inventory inventory) {
		var rand = new Random();
		int obj = rand.nextInt(10);
		
		// 9 chance out of 10 to have a sword
		if(obj == 0) {
			return chooseConsumable();
		}else {
			return chooseBlade();
		}
		
	}
	
	private static Items chooseConsumable() {
		return Regen_potion;
	}

	public static Sword chooseBlade() {

		var rand = new Random();
		int chance = rand.nextInt(100);
		if(chance <= 50) {
			return Wooden_Sword;
		}else if(chance < 70) {
			return Key_Sword;
		}else if(chance < 97) {
			return Slime_Sword;
		}else {
			return Amaury_Blade;
		}
	}

	
}
