package fr.but.Mauritius_Loic_Booms_Amaury.End;

import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.Event.Action;

/**
 * The EndGame class of this game.
 * It permit to start the ending of the game if you complete all the levels and quit the game
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class EndGame {
	
	/**
	 * The EndGame constructor.
	 * It calls the loop and the view of the EndGame
	 * 
	 * @author Mauritius Loic and Booms Amaury
	 */
	public EndGame(ApplicationContext context) {
		var view = new EndGameView();
		view.drawEndMenu(context);
		while(true) {
			if(endloop(context)) {
				context.exit(0);
				break;
			};
		}
	}

	/**
	 * The EndGame controller.
	 * The loop of the EndGame for quit the game (can't restart)
	 * 
	 * @param context  	ApplicationContext of the game
	 * @return False -> continue the endgame menu | True -> End the game
	 */
	private boolean endloop(ApplicationContext context) {
		var event = context.pollOrWaitEvent(10);

		if (event != null) {
			var action = event.getAction();
			if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
				return true;
			}
		}
		return false;
	}
}
