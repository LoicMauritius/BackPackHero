package fr.but.Mauritius_Loic_Booms_Amaury.Game.Map;

public record Position_room(int x, int y) {
	
	@Override
	public String toString() {
		return x + " , " + y;
	}
}
