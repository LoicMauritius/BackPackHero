package fr.but.Mauritius_Loic_Booms_Amaury.Home;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event.Action;

/**
 * The Controller of the menu.
 * It manage clicks and permit interactions on the menu
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class HomeController {
	
	/**
	 * The main loop of the accueil
	 * It permit to take the click position and return which button is click ( -1 if quit and 1 if play)
	 * 
	 * @param context 	GameContext
	 * @param view 		HomeView
	 * @return int		1 -> Start the game | O -> Nothing appends | -1 -> Quit the game
	 */
	public int accueil_loop(ApplicationContext context, HomeView view) {
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		view.drawaccueil(context);
		
		//Action de l'accueil
		
		var event = context.pollOrWaitEvent(2000);
		if(event==null) {
			return 0;
		}
		var action = event.getAction();
		if (action == Action.POINTER_DOWN) {
			var location = event.getLocation();
			Ellipse2D play = new Ellipse2D.Float(100, Math.round(height-100-Math.round(width/10)),Math.round(width/10),Math.round(width/10));
			Ellipse2D quit = new Ellipse2D.Float(100+Math.round(width/6), Math.round(height-100-Math.round(width/10)),Math.round(width/10),Math.round(width/10));
			context.renderFrame(graphics -> {
				//Verification des Ellipses
				graphics.setColor(Color.RED);
				graphics.draw(play);
				graphics.draw(quit);
			});
			if(play.contains(location)) {
				return 1;
			}
			if(quit.contains(location)) {
				return -1;
			}
		}
		return 0;
	}
}
