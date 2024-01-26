package fr.but.Mauritius_Loic_Booms_Amaury.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Inventory;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Items;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Position;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Equipements.Sword;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_hero;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_room;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.RoomModel;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.Event.Events;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Enemy.Enemy;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Enemy.EnemyAction;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Hero.HeroModel;
import fr.umlv.zen5.ApplicationContext;

/**
 * The GameView class of this game.
 * It permit to display the visual of the game
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class GameView {
	
	//The margin between the border of the screen and the map
	public static final int margin = 50;
	//The margin of the cases inside the map
	public static final int margin_map = 30;
	
	//The coords of the begining of the map
	private static final int map_startX = 20;
	private static final int map_startY = 80;
	
	//The margin between two case in line and in column
	private static final int margin_case = 15;
	//The width and the height of the cases
    private static final int case_width = 40;
    private static final int case_height = 40;

	/**
	 * This method permit to display the background of the game
	 * 
	 * @param context 	ApplicationContext ( zen5 )
	 */
	public void drawBG(ApplicationContext context) {
    	var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
    	//Interface graphique du jeu ( fond )
		
    	context.renderFrame(graphics -> {
    		
    		//modifier la police
    		graphics.setColor(Color.WHITE);
    		Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, 40);
    		graphics.setFont(font);
    		
    		//Mettre le sol et le mur du jeu
    		try {
    			var image = ImageIO.read(Files.newInputStream(Path.of("img","gamefond.png")));
    			graphics.drawImage(image, 0,Math.round(height/2)-50 ,Math.round(width) ,Math.round(height/2), null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		
    	});
	}
	
	/**
	 * This method permit to write in the view the number of which floor we are
	 * 
	 * @param context 	ApplicationContext ( zen5 )
	 * @param nfloor 	An integer that
	 */
	void drawNbFloor(ApplicationContext context, int nfloor) {
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();

		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			String numString = "Floor : " + nfloor;
			Font font = new Font("impact", Font.BOLD, 40);
			graphics.setFont(font);
			graphics.drawString(numString, Math.round(width/2-width/4), 60);
		});
	}
	
	/**
	 * This method permit to remove in the view the number of which floor we are to put the next one
	 * 
	 * @param context 	ApplicationContext ( zen5 )
	 */
	void removeNbFloor(ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();

		context.renderFrame(graphics -> {
			Font font = new Font("impact", Font.BOLD, 40);
			graphics.setFont(font);
			graphics.clearRect(Math.round(width/2-width/4), 0,180,60);
		});
	}
    
	/**
	 * This method permit to draw the Back of the map and to take the dimensions of all the rooms ( reachable or not )
	 * 
	 * @param context 				ApplicationContext ( zen5 )
	 * @return Pos_et_Dim_Room 		An HashMap of Position_room and Dimensions for have de (x,y) dimensions of each cases
	 * 								( example: Position_room (0,0) -> Dimensions in the screen ( 165, ... , ... , ... ))
	 */
    public HashMap<Position_room,Dimensions> drawFloors(ApplicationContext context,  RoomModel[][] floor) {
    	
    	var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		HashMap<Position_room,Dimensions> Pos_et_Dim_Room = new HashMap<>();
		var Fwidth = floor.length;
		var Fheight = floor[0].length;
    	//Interface graphique de l'étage
		
    	context.renderFrame(graphics -> {
    		
    		graphics.clearRect(map_startX, map_startY, Math.round((case_width+margin_case)*GameController.nb_case_max)+margin*2,  Math.round((case_height+margin_case)*Fheight)+margin*2);
    		graphics.setColor(Color.ORANGE);
    		graphics.draw3DRect(map_startX, map_startY, Math.round((case_width+margin_case)*GameController.nb_case_max)+margin*2,  Math.round((case_height+margin_case)*Fheight)+margin*2, true);
    		graphics.fill3DRect(map_startX, map_startY, Math.round((case_width+margin_case)*GameController.nb_case_max)+margin*2,  Math.round((case_height+margin_case)*Fheight)+margin*2, true);

    		//Pour connaitre les dimensions des cases du tableau
    		for (int y = 0; y < Fheight; y++) {
                for (int x = 0; x < Fwidth; x++) {
                	Color m = new Color(10,10,10,100);
                	graphics.setColor(m);
                
					Pos_et_Dim_Room.put(new Position_room(x,y),new Dimensions( map_startX + margin_map + margin_case+ ((case_width + margin_case) * x), map_startY + margin_map + margin_case + ((case_height + margin_case) * y), case_width, case_height));
                }
            }
    		drawPath(graphics,Fwidth,Fheight, floor, Pos_et_Dim_Room);
    	});
    	
    	return Pos_et_Dim_Room;
    }
    
    /**
	 * This method permit to draw the path followed by the model of the floor.
	 * 
	 * @param graphics 				Graphics2D
	 * @param Fwidth 				The width of the floor
	 * @param Fheight 				The height of the floor
	 * @param floor 				A matrix of RoomModel who contain the path we follow ( with events )
	 * @param Pos_et_Dim_Room 		An HashMap of Position_room and Dimensions for have de (x,y) dimensions of each cases
	 * 								( example: Position_room (0,0) -> Dimensions in the screen ( 165, ... , ... , ... ))
	 */
	private void drawPath(Graphics2D graphics, int Fwidth, int Fheight, RoomModel[][] floor, HashMap<Position_room,Dimensions> Pos_et_Dim_Room) {
		for (int y = 0; y < Fheight; y++) {
            for (int x = 0; x < Fwidth; x++) {
            	if(floor[x][y].reachable()) {
            		if(floor[x][y].getEvent() != null) {
            			drawEvents(graphics,Fwidth,Fheight, floor, Pos_et_Dim_Room,x,y);
            		}else {
            			Color m = new Color(201, 86, 19, 40);
	            		graphics.setColor(m);
	            		var pos_room = new Position_room(x,y);
	            		var dim_room = Pos_et_Dim_Room.get(pos_room);
	            		var room = new Rectangle2D.Float(dim_room.x(),dim_room.y(),dim_room.w(),dim_room.h());
	            		graphics.draw(room);
	            		graphics.fill(room);
            		}
            		
            	}
            }
        }
	}
	
	 /**
		 * This method permit to draw the events on the map
		 * 
		 * @param graphics 				Graphics2D
		 * @param Fwidth 				The width of the floor
		 * @param Fheight 				The height of the floor
		 * @param floor 				A matrix of RoomModel who contain the path we follow ( with events )
		 * @param Pos_et_Dim_Room 		An HashMap of Position_room and Dimensions for have de (x,y) dimensions of each cases
		 * 								( example: Position_room (0,0) -> Dimensions in the screen ( 165, ... , ... , ... ))
		 */
	private void drawEvents(Graphics2D graphics, int Fwidth, int Fheight, RoomModel[][] floor, HashMap<Position_room,Dimensions> Pos_et_Dim_Room, int x,int y) {
            Color m = new Color(201, 86, 19, 40);
            graphics.setColor(m);
            var pos_room = new Position_room(x,y);
            var dim_room = Pos_et_Dim_Room.get(pos_room);
            var room = new Rectangle2D.Float(dim_room.x(),dim_room.y(),dim_room.w(),dim_room.h());
            graphics.draw(room);
            graphics.fill(room);
            BufferedImage image;
			try {
				image = ImageIO.read(Files.newInputStream(Events.List_events.get(floor[x][y].getEvent())));
				graphics.drawImage(image,dim_room.x(),dim_room.y(),dim_room.w(),dim_room.h(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * This method permit to draw the position of the Hero on the map
	 * 
	 * @param context 				ApplicationContext ( zen 5 )
	 * @param rooms 				A matrix of RoomModel who contain the path we follow ( with events )
	 * @param Position_hero pos		The position (x,y) of the hero on the map
	 */
	public void drawHeroPosition(ApplicationContext context,  RoomModel[][] rooms,Position_hero pos) {
    	
    	var screenInfo = context.getScreenInfo();
    	var width = screenInfo.getWidth();
		
    	//Dessiner la position du personnage dans la carte
		
    	context.renderFrame(graphics -> { 	
               try {
					var image = ImageIO.read(Files.newInputStream(Path.of("img","perso1.png")));
					graphics.drawImage(image,map_startX+margin_map+ margin_case + ((case_width+ margin_case)*pos.x) ,map_startY +margin_map +margin_case + ((case_height+ margin_case)*pos.y),case_width,case_height, null);
				} catch (IOException e) {
					e.printStackTrace();
			}
    	});
    }
	
	/**
	 * This method permit to draw the Hero on the bottom left
	 * 
	 * @param context 			ApplicationContext ( zen 5 )
	 * @param HeroModel hero	The model of the Hero we play	
	 */
	public void drawHero(ApplicationContext context, HeroModel hero) {
    	var screenInfo = context.getScreenInfo();
		var height = screenInfo.getHeight();
		var hero_width = 300;
		var hero_height = 280;
		int PV = hero.getHealthPoints();
		int ActionPoints = hero.getactionPoints();
		
    	//Interface graphique du hero
		
    	context.renderFrame(graphics -> {
    		
    		//modifier la police
    		graphics.setColor(Color.WHITE);
    		Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, 40);
    		graphics.setFont(font);

    		//Met les Points de vie
    		String strhealt = "Health: " + PV + "/" + HeroModel.PV_MAX;
    		graphics.drawString(strhealt, 350, Math.round(height/2+hero_height+100));
    		
    		//Met les Points d'action
    		String straction = "Action: " + ActionPoints + "/" + hero.getMaxActionPoints();
    		graphics.drawString(straction, 350, Math.round(height/2+hero_height+100)+40);
    		
    		//Dessiner le personnage
    		try {
    			var image = ImageIO.read(Files.newInputStream(Path.of("img","perso1.png")));
    			graphics.drawImage(image, 300,Math.round(height/2+100) ,hero_width ,hero_height, null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		
    	});
	}
	
	/**
	 * This method permit to draw the enemy on the bottom right
	 * 
	 * @param context 			ApplicationContext ( zen 5 )
	 * @param HeroModel hero	The model of the Hero we play	
	 */
	public void drawEnemy(ApplicationContext context, Enemy ratwolf) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		var enemy_width = 270;
		var enemy_height = 280;
		int PV = ratwolf.getHp();
		
    	//Interface graphique du hero
		
    	context.renderFrame(graphics -> {
    		
    		//modifier la police
    		graphics.setColor(Color.WHITE);
    		Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, 40);
    		graphics.setFont(font);
    		
    		//Met les Points de vie
    		String strhealt = "Health: " + PV + "/" + ratwolf.getMax_Hp();
    		graphics.drawString(strhealt, Math.round(width/2+enemy_width+100), Math.round(height/2+enemy_height+100));
    		
    		//Met les Points de vie
    		String strprotect = "Protection: " + ratwolf.getProtection();
    		graphics.drawString(strprotect, Math.round(width/2+enemy_width+100), Math.round(height/2+enemy_height+100)+40);
    		
    		//Dessiner le personnage
    		try {
    			var image = ImageIO.read(Files.newInputStream(Path.of("img","ratwolf.png")));
    			graphics.drawImage(image, Math.round(width/2+enemy_width+100),Math.round(height/2+100) ,enemy_width ,enemy_height, null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		
    	});
	}
	
	/**
	 * This method permit to draw the enemy action
	 * 
	 * @param context 			ApplicationContext ( zen 5 )
	 * @param HeroModel hero	The model of the Hero we play	
	 */
	public void drawaction(ApplicationContext context, EnemyAction action) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		var enemy_width = 270;
		var enemy_height = 280;
		
		context.renderFrame(graphics ->{
			//modifier la police
    		graphics.setColor(Color.WHITE);
    		Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, 40);
    		graphics.setFont(font);
    		
			switch(action) {
				case Attack:
					String straction = "Action: Attack";
		    		graphics.drawString(straction, Math.round(width/2+enemy_width+100),Math.round(height/2+50));
		    		break;
				case Protect:
					straction = "Action: Protect";
		    		graphics.drawString(straction, Math.round(width/2+enemy_width+100),Math.round(height/2+50));
		    		break;
			}
		});
	}
	
	/**
	 * This method permit to draw the Hero's inventory
	 * 
	 * @param context 			ApplicationContext ( zen 5 )
	 * @param HeroModel hero	The model of the Hero we play	
	 * @return 
	 */
	public HashMap<Position, Dimensions> drawInventory(ApplicationContext context,Inventory inv) {
    	
    	var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
        context.renderFrame(graphics ->{
        	//Efface l'inventaire d'avant
        	graphics.clearRect(Math.round(width/2),
					0,
					Math.round(width/2), 
					Math.round(height/2-150));
        	
        	//Mettre le fond global de l'inventaire
    		try {
    			var image = ImageIO.read(Files.newInputStream(Path.of("img","sac.png")));
    			graphics.drawImage(image, Math.round(width/2),
    					50,
    					Math.round(width/2), 
    					Math.round(height/2-100), null);
    			
    			drawInventoryZone(graphics,width);
    			}catch (IOException e) {
    				e.printStackTrace();
    			}
    		});
        
	        var pos_dim_inventaire = drawCases(context,inv,width);
	        drawItems(context,inv,pos_dim_inventaire);
			return pos_dim_inventaire;
		
        }

	/**
	 * This method permit to draw the Hero's inventory Items
	 * 
	 * @param context 			Graphics2D, the zone where we draw
	 * @param inv				The width of the screen	
	 * @param pos_dim_inventaire 
	 */
	private void drawItems(ApplicationContext context, Inventory inv, HashMap<Position, Dimensions> pos_dim_inventaire) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		var listItems = inv.getItems();
		
		//Pour on itère sur les items
		for(Entry<Items, Position[]> entry : listItems.entrySet()) {
			var item = entry.getKey();
		    var positions = new ArrayList<Position>();
		    //On transforme en Arraylist pour mieux prendre les éléments
		    for(Position p : entry.getValue()) {
		    	positions.add(p);
		    }
		    drawItem(context,inv,item,positions,pos_dim_inventaire);
		}
	}

	/**
	 * This method permit to draw one item based on is position
	 * 
	 * @param context 					ApplicationContext ( zen 5 )
	 * @param Inventory inv				The backpack of the hero
	 * @param Items item				The item we want to display
	 * @param ArrayList positions		The list of the positions of the item
	 */
	private void drawItem(ApplicationContext context, Inventory inv, Items item, ArrayList<Position> positions,
			HashMap<Position, Dimensions> pos_dim_inventaire) {
		context.renderFrame(graphics ->{
	    	try {
    			var image = ImageIO.read(Files.newInputStream(item.image()));
    			Position first = (Position) positions.get(0);
    			Dimensions firstPos = pos_dim_inventaire.get(first);
    			Position last = (Position) positions.get(positions.size()-1);
    			Dimensions lastPos = pos_dim_inventaire.get(last);
    			System.out.println(firstPos.x() + "," +
    					firstPos.y() + "," + 
    					lastPos.w()+(lastPos.w()*(last.x()-first.x())) + "," +
    					lastPos.h()+(lastPos.h()*(last.y()-first.y())));
    			graphics.drawImage(image,firstPos.x() , 
    					firstPos.y(), 
    					lastPos.w()+(lastPos.w()*(last.y()-first.y())),
    					lastPos.h()+(lastPos.h()*(last.x()-first.x())),
    					null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
	    });
		
	}

	/**
	 * This method permit to draw the Hero's inventory zone ( it's just a landmark for draw the cases)
	 * 
	 * @param graphics 			Graphics2D, the zone where we draw
	 * @param float width		The width of the screen	
	 */
	private static void drawInventoryZone(Graphics2D graphics, float width) {
		Rectangle2D.Float inventory = new Rectangle2D.Float(
				Math.round(width/2+225), 
				150, 
				510, 
				250);
		graphics.setColor(new Color(145,85,25));
		graphics.draw(inventory);
		graphics.fill(inventory);
	}

	/**
	 * This method permit to draw the Hero's inventory cases
	 * 
	 * @param context 			Graphics2D, the zone where we draw
	 * @param inv				The inventory of the Hero
	 * @param float width		The width of the screen	
	 */
	private HashMap<Position,Dimensions> drawCases(ApplicationContext context, Inventory inv, float width) {
		Integer[][] cases_inventaire = inv.getInventory();
		HashMap<Position,Dimensions> pos_dim_inventory = new HashMap<>();
		
		context.renderFrame(graphics ->{
			for(int line = 0; line<cases_inventaire.length ; line++ ) {
				for(int column = 0; column<cases_inventaire[0].length ; column++ ) {
					var pos = new Position(line,column);
					pos_dim_inventory.put(pos, drawCase(context,inv,pos));
				}
			}
		});
		
		return pos_dim_inventory;
    }
	
	/**
	 * This method permit to draw the Hero's inventory cases
	 * 
	 * @param context 			Graphics2D, the zone where we draw
	 * @param inv				The inventory of the Hero
	 * @param Position pos		The position of the case we draw
	 * @param float width		The width of the screen	
	 */
	private Dimensions drawCase(ApplicationContext context, Inventory inv, Position pos) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		var dim = new Dimensions(Math.round(width/2+225) + 10 + ((70 + 20) * pos.y()), 150 + 10 + ((70 + 10) * pos.x()), 70,70);
		Integer[][] cases_inventaire = inv.getInventory();
		
		context.renderFrame(graphics ->{
			Rectangle2D.Float case_de_inventaire = new Rectangle2D.Float(
					dim.x(), 
					dim.y(), 
					dim.w(), 
					dim.h());
			if(cases_inventaire[pos.x()][pos.y()] == 0) {
				graphics.setColor(Color.black);
				graphics.draw(case_de_inventaire);
				graphics.setColor(Color.white);
				graphics.fill(case_de_inventaire);
			}else {
				graphics.setColor(Color.black);
				graphics.draw(case_de_inventaire);
				graphics.setColor(Color.YELLOW);
				graphics.fill(case_de_inventaire);
			}
		});
		return dim;
    }

	/**
	 * This method permit to draw the Treasure of the treasure event
	 * 
	 * @param context 			ApplicationContext ( zen5 )
	 */
	public Dimensions drawChest(ApplicationContext context) {

		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		var dim_treasure = new Dimensions(Math.round(width/2+100),Math.round(height/2) ,500 ,500);
		
		context.renderFrame(graphics -> {
			//Dessiner le trésor
    		try {
    			var image = ImageIO.read(Files.newInputStream(Path.of("img","logo_map", "coffre.png")));
    			graphics.drawImage(image, dim_treasure.x(),dim_treasure.y() ,dim_treasure.w() ,dim_treasure.h(), null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		});
		
		return dim_treasure;
	}

	public Dimensions drawItem(ApplicationContext context,Items item) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		var dim_sword = new Dimensions(Math.round(width/2) + 10, Math.round(height/2) + 10,70,(70*item.getSize()));
		
		context.renderFrame(graphics -> {
			try {
    			var image = ImageIO.read(Files.newInputStream(item.image()));
    			graphics.drawImage(image, Math.round(width/2) + 10, 
    					Math.round(height/2) + 10, 
    					70, 
    					(70*item.getSize()), null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		});
		return dim_sword;
	}
	
}
