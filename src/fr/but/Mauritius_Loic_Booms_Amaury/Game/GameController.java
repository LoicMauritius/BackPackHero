package fr.but.Mauritius_Loic_Booms_Amaury.Game;


import java.awt.Color;


import java.awt.Font;
import java.util.HashMap;
import java.util.Map.Entry;

import fr.but.Mauritius_Loic_Booms_Amaury.End.EndGame;
import fr.but.Mauritius_Loic_Booms_Amaury.Exception.FloorException;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Inventory;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Items;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.MeleeWeapon;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Position;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Sword;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_hero;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_room;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.FloorModel;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.RoomModel;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Enemy.Enemy;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Enemy.EnemyAction;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Hero.HeroModel;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Sound.Audio;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Treasure.TreasureModel;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.Event.Action;

/**
 * The GameController class of this game.
 * It permit to manage all the actions of the game
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class GameController {
		
	//The numbers of floors on the game
	private static final int NUM_FLOORS = 10;
	//The max numbers of cases we can put on the map
	public static final int nb_case_max = 16;
	
	//The view never change so I decided to put that on private final
	private final GameView view = new GameView();
	
	//The position and dimensions of each cases in the inventory
	private static HashMap<Position, Dimensions> pos_dim_inventory;

	/**
	 * This method permit to play the music, start the game and control all actions on the game
	 * 
	 * @param context		ApplicationContext ( zen5 )
	 */
	public void game_loop(ApplicationContext context) {
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();

		playMusic();
		view.drawBG(context);
			
		HeroModel hero = new HeroModel(context, view);
		
		view.drawHero(context,hero);
		pos_dim_inventory = view.drawInventory(context, hero.getInventory());
        
		dunjeon_loop(context,hero,view);
			
		new EndGame(context);
	}
		
	/**
	 * This method permit to play the music of the game
	 * ( Audio )
	 */
	private void playMusic() {
		String osName = System.getProperty("os.name");
		String filePath;

		if (osName.startsWith("Windows")) {
		    filePath = "son\\playlist.wav";
		} else {
		    filePath = "son/playlist.wav";
		}
		
		Audio son = new Audio(filePath);
		son.setDaemon(true);
		son.setVolume((float)0.1);
		son.start();
	}

	/**
	 * This method permit to call a new floor in the dunjeon and change the size of it
	 * 
	 * @param context		ApplicationContext ( zen5 )
	 * @param hero1			The hero we play
	 * @param view			The view of the game
	 */
	private void dunjeon_loop(ApplicationContext context, HeroModel hero1, GameView view) {
			int Nfloor = 0;
	    	int addcase = 0;
	    	
			while(Nfloor < NUM_FLOORS) {
	    		//Ajouter des salles
	    		switch(Nfloor) {
	    		case 2:
	    			addcase++;
	    		case 4:
					addcase++;
	    		case 5:
					addcase++;
	    		case 7:
					addcase++;
	    		case 9:
					addcase++;
	    		case 10:
					addcase++;
	    		}
	    		if(Nfloor > nb_case_max) {
	    			throw new FloorException("Le nombre de case maximum a été dépasser ! ");
	    		}else {
		    		view.drawNbFloor(context,Nfloor);
		    		new FloorModel(context, this, addcase,Nfloor,hero1);
		    		view.removeNbFloor(context);
		    		Nfloor++;
	    		}
	    		
	    		//Test de l'écran de fin (ending)
	    		//if(Nfloor == 1) {
	    			//break;
	    		//}
	    	}
			
		}
		
		/**
		 * This method permit to control actions for the map
		 * 
		 * @param context		ApplicationContext ( zen5 )
		 * @param floor			The floor with all rooms ( RoomModel[][] )
		 * @param hero			The hero we play
		 */
		public boolean mapLoop(ApplicationContext context, RoomModel[][] floor, HeroModel hero){
			
			var pos_dim = view.drawFloors(context,floor);
	    	
	        var pos_hero = new Position_hero(0,0);
	        var Fwidth = floor.length;
	        var Fheight = floor[0].length;
	        
	        //Define the appear position of the hero
	        for (int y = 0; y < Fheight; y++) {
	            for (int x = 0; x < Fwidth; x++) {
	        		if(floor[x][y].getEvent() == "entrance") {
	        			pos_hero.modifier(x, y);
	        			break;
	        		}
	        	}
	        }
	        
	        view.drawHeroPosition(context, floor,pos_hero);
			
	        //Boucle de la map
			while(true) {
				var event = context.pollOrWaitEvent(10);

				if (event != null) {
					var action = event.getAction();
					if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
						context.exit(0);
						return false;
					}
					if (action == Action.POINTER_DOWN) {
						var location = event.getLocation();
						for (Entry<Position_room, Dimensions> entry : pos_dim.entrySet()) {
						    var pos = entry.getKey();
						    var dim = entry.getValue();
							if (dim.x() <= location.x && dim.y() <= location.y && dim.w()+dim.x() >= location.x && dim.h()+dim.y() >= location.y) {
								boolean fin = DeplacerHero(context,floor,pos_hero,pos,view,hero);
								if(fin) {
									return false;
								}
							}
						}
					}
				}else {
					continue;
				}
			}
		}

	/**
	* This method permit to move the hero on the floor to reach is destination
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param floor			The floor with all rooms ( RoomModel[][] )
	* @param pos_hero		The Position (x,y) of the hero on the floor
	* @param pos			The Position (x,y) of the room to reach
	* @param view			The view of the game
	* @param hero			The hero we play
	* 
	* @return True -> The hero reach the end of the floor || False -> The hero is in the floor
	*/
	private boolean DeplacerHero(ApplicationContext context, RoomModel[][] floor, Position_hero pos_hero, Position_room pos, GameView view, HeroModel hero) {

		var son_marche = startStepSound();
		
		while(pos_hero.x != pos.x() || pos_hero.y != pos.y()) {
			var pos_de_base = Position_hero.copy(pos_hero);
			if(pos_hero.x < pos.x() && floor[pos_hero.x+1][pos_hero.y].reachable()) {
				pos_hero.modifier(pos_hero.x+1, pos_hero.y);
				view.drawFloors(context, floor);
				view.drawHeroPosition(context, floor, pos_hero);
			}else if(pos_hero.y < pos.y() && floor[pos_hero.x][pos_hero.y+1].reachable()) {
				pos_hero.modifier(pos_hero.x, pos_hero.y+1);
				view.drawFloors(context, floor);
				view.drawHeroPosition(context, floor, pos_hero);
			}else if(pos_hero.x > pos.x() && floor[pos_hero.x-1][pos_hero.y].reachable()) {
				pos_hero.modifier(pos_hero.x-1, pos_hero.y);
				view.drawFloors(context, floor);
				view.drawHeroPosition(context, floor, pos_hero);
			}else if(pos_hero.y > pos.y() && floor[pos_hero.x][pos_hero.y-1].reachable()){
				pos_hero.modifier(pos_hero.x, pos_hero.y-1);
				view.drawFloors(context, floor);
				view.drawHeroPosition(context, floor, pos_hero);
			}
			if(floor[pos_hero.x][pos_hero.y].getEvent() != null) {
				//On arrete le son de step
				var fin = activeEvent(context,floor[pos_hero.x][pos_hero.y].getEvent(),hero,floor,pos_hero);
				if(fin) {
					return true;
				}
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Si le héro reste au même endroit on casse la boucle, pour éviter que le jeu plante
			if(pos_de_base.equals(pos_hero)) {
				break;
			}
		}
		
		return false;
	}

	/**
	* This method permit to start the foot step sound
	* 
	* @return Audio son_marche		The step sound
	*/
	private Audio startStepSound() {
		String osName = System.getProperty("os.name");
		String filePath;

		if (osName.startsWith("Windows")) {
		    filePath = "son\\deplacement.wav";
		} else {
		    filePath = "son/deplacement.wav";
		}
		
		Audio son_marche = new Audio(filePath);
		son_marche.setDaemon(true);
		son_marche.setVolume((float)0.5);
		son_marche.start();
		return son_marche;
	}

	/**
	* This method permit to active event when the hero is in a room
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param event			A String that says the name of the event of the room*
	* @param hero			The hero we play
	* @param floor			The floor with all rooms ( RoomModel[][] )
	* @param pos_hero		The Position (x,y) of the hero on the floor
	* 
	* @return True -> The hero reach the end of the floor || False -> The hero is in the floor
	*/
	private boolean activeEvent(ApplicationContext context, String event, HeroModel hero, RoomModel[][] floor, Position_hero pos_hero) {
		//TODO Activer tout les events
		switch(event) {
			case "exit":
				view.drawBG(context);
				view.drawHero(context, hero);
				return true;
				
			case "monsters":
				fightloop(context, hero);
				floor[pos_hero.x][pos_hero.y].rm_Event();
				view.drawBG(context);
				view.drawHero(context, hero);
				return false;
				
			case "healer":
				floor[pos_hero.x][pos_hero.y].rm_Event();
				view.drawBG(context);
				view.drawHero(context, hero);
				return false;
				
			case "merchant":
				floor[pos_hero.x][pos_hero.y].rm_Event();
				view.drawBG(context);
				view.drawHero(context, hero);
				return false;
				
			case "treasure":
				treasureloopbefore(context, hero);
				floor[pos_hero.x][pos_hero.y].rm_Event();
				view.drawBG(context);
				view.drawHero(context, hero);
				return false;
		}
		return false;
	}
	
	/**
	* This method permit to start the fight and block the map moving
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param hero			The hero we play
	*/
	private void fightloop(ApplicationContext context, HeroModel hero) {
		var fight = true;
		int Tour_hero = 1;
		
		var Ratwolf = new Enemy("Rat-loup", 32, 7, 14);
		var Enemy_action = Ratwolf.action();
		view.drawEnemy(context,Ratwolf);
	
		while(fight) {
			
			if(Tour_hero == 1) {
				view.drawaction(context,Enemy_action);
				Tour_hero = heroturn(context,Ratwolf,hero);
			}else if(Tour_hero == 0){
				Tour_hero = enemyturn(context,Ratwolf,hero,Enemy_action);
				//Après le tour de l'ennemi, il change d'action
				Enemy_action = Ratwolf.action();
				if(hero.getHealthPoints() < 1) {
					System.out.println("GameOver");
		        	context.exit(1);
		        }
			}else{
				return;
			}
		}
	}

	/**
	* This method permit to start the enemy turn with all the actions he do
	* 
	* @param context				ApplicationContext ( zen5 )
	* @param Enemy ratwolf			The enemy
	* @param HeroModel hero			The hero we play
	* @param enemy_action 			The action of the enemy
	*/
	private int enemyturn(ApplicationContext context, Enemy ratwolf, HeroModel hero, EnemyAction enemy_action) {
		switch(enemy_action){
			case Attack:
				hero.setHealthPoints(hero.getHealthPoints() - ratwolf.getDgt());
				break;
			case Protect:
				ratwolf.setProtection(ratwolf.getProtection() + ratwolf.getProtect());
				break;
		}
		hero.setactionPoints(hero.getMaxActionPoints());
		view.drawBG(context);
		view.drawHero(context, hero);
		view.drawEnemy(context,ratwolf);
		return 1;
	}

	/**
	* This method permit to start the hero turn with all the actions
	* 
	* @param context		ApplicationContext ( zen5 )
	 * @param hero 
	* @param hero			The hero we play
	*/
	private int heroturn(ApplicationContext context, Enemy Ratwolf, HeroModel hero) {
		while(true) {
			if(hero.getactionPoints() <= 0) {
				return 0;
			}
			
			var event = context.pollOrWaitEvent(10);

			if (event != null) {
				var action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					if(event.getKey() == KeyboardKey.Q) {
						context.exit(0);
						return -1;
					}else if(event.getKey() == KeyboardKey.SPACE) {
						return -1;
					}else if(event.getKey() == KeyboardKey.Z) {
						return 0;
					}
				}
				if (action == Action.POINTER_DOWN) {
					var location = event.getLocation();
		
					//Si on clique sur un item
					for (Entry<Position, Dimensions> entry : pos_dim_inventory.entrySet()) {
					    var pos = entry.getKey();
					    var dim = entry.getValue();
						if (dim.x() <= location.x && dim.y() <= location.y && dim.w()+dim.x() >= location.x && dim.h()+dim.y() >= location.y) {
							
							Items item = getItemClick(hero,pos);

							if(item instanceof MeleeWeapon) {
								MeleeWeapon weapon = (MeleeWeapon) item;
								
								//ActionPoints use
								if(hero.getactionPoints() - weapon.getActionCost() < 0) {
									System.out.println("Action impossible");
								}else {
									//Attack on the enemy
									if(Ratwolf.getProtection()>0) {
										if(Ratwolf.getProtection()-weapon.getDamage() >= 0) {
											Ratwolf.setProtection(Ratwolf.getProtection()-weapon.getDamage());
										}else {
											Ratwolf.setProtection(0);
										}
									}else {
										Ratwolf.setHp(Ratwolf.getHp() - weapon.getDamage());
									}
									
									hero.setactionPoints(hero.getactionPoints() - weapon.getActionCost());
								}
								
								view.drawBG(context);
								view.drawHero(context, hero);
								view.drawEnemy(context,Ratwolf);
								if(Ratwolf.getHp() < 1) {
									view.drawBG(context);
									view.drawHero(context, hero);
									return -1;
								}
								return 1;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method permit to return the item clicked
	 * 
	 * @param HeroModel hero		The hero we play
	 * @param Position pos			The position of the click on the inventory
	 */
	private Items getItemClick(HeroModel hero, Position pos) {
		var listItems = hero.getInventory().getItems();
		for (Entry<Items, Position[]> entry : listItems.entrySet()) {
			var item = entry.getKey();
		    var positions = entry.getValue();
		    for(Position p : positions) {
		    	if(p.equals(pos)) {
		    		return item;
		    	}
		    }
		}
		return null;
	}

	/**
	* This method permit to start the treasureRoom control and block the map moving
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param hero			The hero we play
	*/
	public void treasureloopbefore(ApplicationContext context, HeroModel hero) {
		
		var dim_treasure = view.drawChest(context);
		
		while(true) {
			var event = context.pollOrWaitEvent(10);

			if (event != null) {
				var action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					if(event.getKey() == KeyboardKey.Q) {
						context.exit(0);
					}else if(event.getKey() == KeyboardKey.SPACE) {
						return;
					}
				}
				if (action == Action.POINTER_DOWN) {
					var location = event.getLocation();
		
					//Si on clique sur le trésor
					if(dim_treasure.x() < location.x && location.x < dim_treasure.x()+dim_treasure.w() && dim_treasure.y() < location.y && location.y < dim_treasure.y()+dim_treasure.h()) {
						view.drawBG(context);
						view.drawHero(context, hero);
						
						if(!treasureloopafter(context,hero)) {
							return;
						}
					}
				}
			}
		}
	}

	/**
	* This method permit to create items for stock them or not in the backpack
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param hero			The hero we play
	* 
	* @return true -> The treasureRoom loop continue || False -> The treasureRoom loop stop and hero quit the room
	*/
	private boolean treasureloopafter(ApplicationContext context, HeroModel hero) {
		boolean item_in_inv = false;
		//Créer une épée alétoire
		Items item = TreasureModel.chooseItem(hero.getInventory());
		var dim_item = view.drawItem(context,item);
		
		while(!item_in_inv) {
			
			var event = context.pollOrWaitEvent(10);
			
			if (event != null) {
				var action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					if(event.getKey() == KeyboardKey.Q) {
						context.exit(0);
					}else if(event.getKey() == KeyboardKey.SPACE) {
						return true;
					}
				}
				if (action == Action.POINTER_DOWN) {
					var location = event.getLocation();
					if(dim_item.x() < location.x && location.x < dim_item.x()+dim_item.w() && 
							dim_item.y() < location.y && location.y < dim_item.y()+dim_item.h()) {
						System.out.println("Vous cliquez sur l'épée");
						item_in_inv = moveItem(context, hero, dim_item,item);
					}
				}
			}
		}
		return false;
	}

	/**
	* This method permit to move the sword in the inventory
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param hero			The hero we play
	* @param dim_sword		The dimensions of the sword we move
	* @param sword 			The sword we move
	 * @return 
	*/
	private boolean moveItem(ApplicationContext context, HeroModel hero, Dimensions dim_sword, Items item) {
		while(true) {
			var event = context.pollOrWaitEvent(10);
			
			if (event != null) {
				var action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					if(event.getKey() == KeyboardKey.Q) {
						context.exit(0);
						return false;
					}
				}
				if (action == Action.POINTER_DOWN) {
					var location = event.getLocation();
					
					System.out.println(pos_dim_inventory);
					for (Entry<Position, Dimensions> entry : pos_dim_inventory.entrySet()) {
					    var pos = entry.getKey();
					    var dim = entry.getValue();
						if (dim.x() <= location.x && dim.y() <= location.y && dim.w()+dim.x() >= location.x && dim.h()+dim.y() >= location.y) {
							System.out.println("Déplace l'épée");
							if(placeItem(context,hero,item,pos)){
								view.drawInventory(context, hero.getInventory());
								return true;
							}
						}
					}
				}
			}
		}
	}

	/**
	* This method permit to place an item in the inventory
	* 
	* @param context		ApplicationContext ( zen5 )
	* @param hero			The hero we play
	* @param sword 			The sword we move
	* @param pos 			The position of the case we clicked on
	*/
	private boolean placeItem(ApplicationContext context, HeroModel hero, Items item, Position pos) {
		System.out.println("Place l'épée dans l'inv");
		for(Items i : hero.getInventory().getItems().keySet()) {
			if(i.equals(item)) {
				return true;
			}
		}
		if(pos.x() + item.getSize() <= hero.getInventory().max_line ) {
			var positions = new Position[item.getSize()];
			int n = 0;
			for(int p = pos.x(); p < pos.x() + item.getSize(); p++) {
				if(hero.getInventory().getInventory()[p][pos.y()] == 1) {
					System.out.println("Vous ne pouvez pas mettre cet item ici");
					return false;
				}
			}
			for(int p = pos.x(); p < pos.x() + item.getSize(); p++) {
				hero.getInventory().setCaseFilled(p, pos.y());
				positions[n] = new Position(p,pos.y());
				n++;
			}
			hero.getInventory().addItem(item, positions);
		}else if(pos.y() + item.getSize() <= hero.getInventory().max_column) {
			var positions = new Position[item.getSize()];
			int n = 0;
			for(int p = pos.y(); p < pos.y() + item.getSize(); p++) {
				if(hero.getInventory().getInventory()[pos.x()][p] == 1) {
					System.out.println("Vous ne pouvez pas mettre cet item ici");
					return false;
				}
			}
			for(int p = pos.y(); p < pos.y() + item.getSize(); p++) {
				hero.getInventory().setCaseFilled(pos.x(), p);
				positions[n] = new Position(pos.x(),p);
				n++;
			}
			hero.getInventory().addItem(item, positions);
		}else {
			return false;
		}
		return true;
	}
}
