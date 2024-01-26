package fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Hero;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.but.Mauritius_Loic_Booms_Amaury.Game.GameView;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Inventory;
import fr.umlv.zen5.ApplicationContext;

public class HeroModel {
	public final static int PV_MAX = 40;
	private final int MaxactionPoints;
    private int healthPoints;
    private int manaPoints;
    private int actionPoints;
    private int protection;
    private Inventory inventory;
    
    public HeroModel(ApplicationContext context, GameView view) {
        this.healthPoints = PV_MAX;
        this.manaPoints = 30;
        this.MaxactionPoints = 3;
        this.actionPoints = 3;
        this.inventory = new Inventory();
        this.setProtection(0);
    }

	public int getHealthPoints() {
        return healthPoints;
    }
	
	public int getMaxActionPoints() {
        return MaxactionPoints;
    }
	
	public int getactionPoints() {
        return actionPoints;
    }
    
	public void setactionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }
	
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

	public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}
}



/* pour implémenter et utiliser le héro dans le jeu il faudra utilser ce genre de code : 

Hero myHero = new Hero();
int heroHp = myHero.getHealthPoints();
Inventory heroInventory = myHero.getInventory();

*/