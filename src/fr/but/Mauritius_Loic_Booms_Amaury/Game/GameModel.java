package fr.but.Mauritius_Loic_Booms_Amaury.Game;

import java.awt.geom.Rectangle2D;

import fr.but.Mauritius_Loic_Booms_Amaury.End.EndGame;
import fr.but.Mauritius_Loic_Booms_Amaury.Home.HomeController;
import fr.umlv.zen5.ApplicationContext;

public class GameModel {

	public GameModel(ApplicationContext context){
			
			var screenInfo = context.getScreenInfo();
			var width = screenInfo.getWidth();
			var height = screenInfo.getHeight();
			
			var controller = new GameController();
			controller.game_loop(context);
		}
}
