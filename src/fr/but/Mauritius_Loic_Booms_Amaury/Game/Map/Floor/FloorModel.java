package fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Map.Entry;

import fr.but.Mauritius_Loic_Booms_Amaury.Game.GameController;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_hero;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_room;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.RoomModel;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.Event.Events;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Hero.HeroModel;
import fr.umlv.zen5.ApplicationContext;

public class FloorModel {

	//Model
	private int FLOOR_WIDTH = 11;
    private int FLOOR_HEIGHT = 5;
    
    private int nb_marchants = 1;
    private int nb_healers = 1;
    private int nb_tresors = 2;
    private int nb_enemy = 4;
    
    private RoomModel[][] floor;
    
    public FloorModel(ApplicationContext context, GameController controller, int addcase,int nb_floor, HeroModel hero) {
    	int Fwidth = FLOOR_WIDTH+addcase;
    	int Fheight = FLOOR_HEIGHT;

    	floor = generatefloor(nb_floor,Fwidth,Fheight);
    	
    	var controler = new GameController();
        
        if(!controler.mapLoop(context,floor,hero)){
        	end();
        };
    }
    
    private RoomModel[][] generatefloor(int nb_floor,int width,int height) {

    	RoomModel[][] floor = new RoomModel[width][height];
    	floor = initFloor(floor,width,height);

    	//Defini des chemins aléatoire
    	if(nb_floor == 0) {
    		floor = Path1(floor,nb_floor);	
    	}else {
    		var r = new Random();
	    	int path = r.nextInt(3);
	    	if(path == 0) {
	    		if(nb_floor >= 2) {
	    			floor = Path4(floor,nb_floor);
	    		}else {
	    			floor = Path1(floor,nb_floor);	
	    		}
	    		
	    	}else if(path == 1){
	    		floor = Path2(floor,nb_floor);
	    	}else if(path == 2){
	    		floor = Path3(floor,nb_floor);
	    	}
    	}
    	
    	floor = setRoomEnemy(floor,4);
    	floor = setRoomTreasure(floor,2);
    	floor = setRoomHealer(floor,1);
    	floor = setRoomMerchant(floor,1);

    	return floor;
    }
    
	private RoomModel[][] initFloor(RoomModel[][] floor, int width, int height) {
    	//Initialser les rooms
    	for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	floor[x][y] = new RoomModel(new Position_room(x,y));
            	floor[x][y].setreachable(false);
            }
        }
    	return floor;
	}
    
	private RoomModel[][] setRoomEnemy(RoomModel[][] floor,int nb) {
    	int n = 0;
    	while(n < nb) {
            Random ran = new Random();
            int Rx = ran.nextInt(2,FLOOR_WIDTH);
            int Ry = ran.nextInt(FLOOR_HEIGHT);
            if(floor[Rx][Ry].getEvent() == null && floor[Rx][Ry].reachable()) {
            	floor[Rx][Ry].setEvent("monsters");
            	n++;
            }
    	}
    	return floor;
    }
    
    private RoomModel[][] setRoomTreasure(RoomModel[][] floor,int nb) {
    	int n = 0;
    	while(n < nb) {
            Random ran = new Random();
            int Rx = ran.nextInt(FLOOR_WIDTH);
            int Ry = ran.nextInt(FLOOR_HEIGHT);
            if(floor[Rx][Ry].getEvent() == null && floor[Rx][Ry].reachable()) {
            	floor[Rx][Ry].setEvent("treasure");
            	n++;
            }
    	}
    	return floor;
    }
    
    private RoomModel[][] setRoomMerchant(RoomModel[][] floor,int nb) {
    	int n = 0;
    	while(n < nb) {
            Random ran = new Random();
            int Rx = ran.nextInt(2,FLOOR_WIDTH);
            int Ry = ran.nextInt(FLOOR_HEIGHT);
            if(floor[Rx][Ry].getEvent() == null && floor[Rx][Ry].reachable()) {
            	floor[Rx][Ry].setEvent("merchant");
            	n++;
            }
    	}
    	return floor;
    }
    
    private RoomModel[][] setRoomHealer(RoomModel[][] floor,int nb) {
    	int n = 0;
    	while(n < nb) {
            Random ran = new Random();
            int Rx = ran.nextInt(3,FLOOR_WIDTH);
            int Ry = ran.nextInt(FLOOR_HEIGHT);
            if(floor[Rx][Ry].getEvent() == null && floor[Rx][Ry].reachable()) {
            	floor[Rx][Ry].setEvent("healer");
            	n++;
            }
    	}
    	return floor;
    }

	public boolean end() {
		return false;
	}
    
	
	private RoomModel[][] Path1(RoomModel[][] floor,int nb_floor) {
    	floor[0][0].setreachable(true);
    	floor[0][0].setEvent("entrance");
    	floor[0][1].setreachable(true);
    	floor[0][1].setEvent("treasure");
    	floor[0][2].setreachable(true);
    	floor[1][2].setreachable(true);
    	floor[2][2].setreachable(true);
    	floor[2][3].setreachable(true);
    	floor[3][3].setreachable(true);
    	floor[4][3].setreachable(true);
    	floor[4][4].setreachable(true);
    	floor[5][4].setreachable(true);
    	floor[6][4].setreachable(true);
    	floor[6][3].setreachable(true);
    	floor[6][2].setreachable(true);
    	floor[6][1].setreachable(true);
    	floor[7][1].setreachable(true);
    	floor[8][1].setreachable(true);
    	floor[8][0].setreachable(true);
    	floor[9][0].setreachable(true);
    	floor[10][0].setreachable(true);
    	floor[10][1].setreachable(true);
    	floor[10][2].setreachable(true);
    	floor[10][3].setreachable(true);
    	floor[10][4].setreachable(true);
    	floor[10][4].setEvent("exit");
		return floor;
	}
    
    private RoomModel[][] Path2(RoomModel[][] floor,int nb_floor) {
    	floor[0][4].setreachable(true);
    	floor[0][4].setEvent("entrance");
    	floor[1][4].setreachable(true);
    	floor[2][4].setreachable(true);
    	floor[2][3].setreachable(true);
    	floor[2][2].setreachable(true);
    	floor[2][1].setreachable(true);
    	floor[3][1].setreachable(true);
    	floor[4][1].setreachable(true);
    	floor[5][1].setreachable(true);
    	floor[6][1].setreachable(true);
    	floor[6][0].setreachable(true);
    	floor[7][0].setreachable(true);
    	floor[8][0].setreachable(true);
    	floor[8][1].setreachable(true);
    	floor[8][2].setreachable(true);
    	floor[8][3].setreachable(true);
    	floor[8][4].setreachable(true);
    	floor[9][4].setreachable(true);
    	floor[10][4].setreachable(true);
    	floor[10][3].setreachable(true);
    	floor[10][2].setreachable(true);
    	floor[10][1].setreachable(true);
    	floor[10][0].setreachable(true);
    	floor[10][0].setEvent("exit");
		return floor;
	}

    private RoomModel[][] Path3(RoomModel[][] floor,int nb_floor) {
    	floor[5][2].setreachable(true);
    	floor[5][2].setEvent("entrance");
    	floor[5][3].setreachable(true);
    	floor[5][4].setreachable(true);
    	floor[6][4].setreachable(true);
    	floor[7][4].setreachable(true);
    	floor[7][3].setreachable(true);
    	floor[7][2].setreachable(true);
    	floor[8][2].setreachable(true);
    	floor[9][2].setreachable(true);
    	floor[10][2].setreachable(true);
    	floor[10][1].setreachable(true);
    	floor[10][0].setreachable(true);
    	floor[9][0].setreachable(true);
    	floor[8][0].setreachable(true);
    	floor[7][0].setreachable(true);
    	floor[6][0].setreachable(true);
    	floor[5][0].setreachable(true);
    	floor[4][0].setreachable(true);
    	floor[3][0].setreachable(true);
    	floor[2][0].setreachable(true);
    	floor[1][0].setreachable(true);
    	floor[0][0].setreachable(true);
    	floor[0][1].setreachable(true);
    	floor[0][2].setreachable(true);
    	floor[0][3].setreachable(true);
    	floor[0][4].setreachable(true);
    	floor[1][4].setreachable(true);
    	floor[2][4].setreachable(true);
    	floor[3][4].setreachable(true);
    	floor[3][3].setreachable(true);
    	floor[3][2].setreachable(true);
    	floor[3][2].setEvent("exit");
		return floor;
	}
    
    private RoomModel[][] Path4(RoomModel[][] floor,int nb_floor) {
    	floor[0][0].setreachable(true);
    	floor[0][0].setEvent("entrance");
    	floor[0][1].setreachable(true);
    	floor[1][1].setreachable(true);
    	floor[1][2].setreachable(true);
    	floor[2][2].setreachable(true);
    	floor[2][3].setreachable(true);
    	floor[3][3].setreachable(true);
    	floor[3][4].setreachable(true);
    	floor[4][4].setreachable(true);
    	floor[5][4].setreachable(true);
    	floor[5][3].setreachable(true);
    	floor[6][3].setreachable(true);
    	floor[6][2].setreachable(true);
    	floor[7][2].setreachable(true);
    	floor[7][1].setreachable(true);
    	floor[8][1].setreachable(true);
    	floor[8][0].setreachable(true);
    	floor[9][0].setreachable(true);
    	floor[10][0].setreachable(true);
    	floor[10][1].setreachable(true);
    	floor[11][1].setreachable(true);
    	floor[11][2].setreachable(true);
    	floor[11][3].setreachable(true);
    	floor[11][4].setreachable(true);
    	floor[10][4].setreachable(true);
    	floor[9][4].setreachable(true);
    	floor[8][4].setreachable(true);
    	floor[8][4].setEvent("exit");
		return floor;
	}
}
