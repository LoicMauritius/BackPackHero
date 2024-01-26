package fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor;

import fr.but.Mauritius_Loic_Booms_Amaury.Exception.RoomException;
import fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Position_room;

public class RoomModel {
	
	private boolean reachable;
	private Position_room pos;
	private String event;
    
    public RoomModel(boolean reachable,Position_room pos) {
        this.reachable = reachable;
        this.pos = pos;
        this.event = null;
    }
    
    public RoomModel(Position_room pos) {
    	this.reachable = false;
        this.pos = pos;
        this.event = null;
	}
    
    public void setEvent(String event) {
    	if(this.reachable) {
    		this.event = event;
    	}else {
    		throw new RoomException(" Vous devez mettre un evenement sur une salle accessible !");
    	}
    }
    
    public void setreachable(boolean r) {
    	this.reachable = r;
    }
    
    public boolean reachable() {
    	return this.reachable;
    }
    
    public String getEvent() {
    	return this.event;
    }
    
    @Override
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append("La salle en position " + this.pos.toString() + " est ");
    	if(this.reachable) {
    		s.append("accessible ");
    		if(this.event != null) {
    			s.append("et a pour evenement " + event);
    		}
    	}else {
    		s.append("inaccessible");
    	}
    	return s.toString();
    }

	public void rm_Event() {
		this.event = null;
	}
    
}