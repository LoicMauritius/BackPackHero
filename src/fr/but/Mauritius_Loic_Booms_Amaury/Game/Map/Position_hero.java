package fr.but.Mauritius_Loic_Booms_Amaury.Game.Map;

public class Position_hero  {
	
	public int x;
	public int y;
	
	public Position_hero(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public Position_hero(){
		this.x = 0;
		this.y = 0;
	}

	@Override
	public String toString() {
		return x + "+" + y;
	}
	
	public void modifier(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Position_hero copy(Position_hero pos) {
		return new Position_hero(pos.x,pos.y);
	}
	
	public boolean equals(Position_hero pos) {
		return (pos.x == this.x && this.y == pos.y);
	}
}
