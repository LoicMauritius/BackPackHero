package fr.but.Mauritius_Loic_Booms_Amaury.Game;

public record Dimensions(int x,int y,int w,int h) {
	
	public Dimensions copy(Dimensions dim) {
		return new Dimensions(dim.x,dim.y,dim.w,dim.h);
	}
	
	@Override
	public String toString() {
		return " Points: " + this.x + " x " + y + " , " + w + " x " + h + ";\n" ;
	}
}
