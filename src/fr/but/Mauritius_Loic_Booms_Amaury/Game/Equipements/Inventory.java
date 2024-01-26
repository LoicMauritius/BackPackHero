package fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.uge.memory.Coordinates;
import fr.umlv.zen5.ApplicationContext;

public class Inventory {
    private Integer[][] Inventory;
    private HashMap<Items,Position[]> Items;
    public int max_line = 3;
    public int max_column = 5;

    public Inventory() {
    	Inventory = new Integer[max_line][max_column];
    	Items = new HashMap<Items,Position[]>();
    	this.init();
    }

    public void init() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
            	Inventory[i][j] = 0;
            }
        }
    }
    
    public void addItem(Items item,Position... pos) {
    	Items.put(Objects.requireNonNull(item),Objects.requireNonNull(pos));
    }
    
    public void removeItem(Items item) {
    	Items.remove(item);
    }
    
    @Override
    /**
	 * Print the inventory on the console
	 * 
	 * @return String of the inventory
	 */
    public String toString() {
    	StringBuilder s = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
            	s.append(" " + Inventory[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public Integer[][] getInventory(){
    	return this.Inventory;
    }
    
    public HashMap<Items,Position[]> getItems(){
    	return this.Items;
    }
    
    public void setCaseFilled(int x, int y){
    	this.Inventory[x][y] = 1;
    }
    
    public void setCaseEmpty(int x, int y){
    	this.Inventory[x][y] = 0;
    }
}
