package fr.but.Mauritius_Loic_Booms_Amaury.Home;


import fr.but.Mauritius_Loic_Booms_Amaury.Game.GameModel;
import fr.umlv.zen5.ApplicationContext;
/**
 * The Model of the menu where we start after lunch the game.
 * It create directly the controller and the view
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class HomeModel {
	
	/**
	 * The HomeModel constructor.
	 * It permit to do the menu loop and quit if the loop end
	 * 
	 * @param context GameContext
	 * @throws InterruptedException
	 */
	public HomeModel(ApplicationContext context) throws InterruptedException {
		
		var view = new HomeView();
		var controller = new HomeController();
		
		switch(controller.accueil_loop(context,view)) {
		case 1:
			view.remove_accueil(context);
			new GameModel(context);
		case -1:
			context.exit(0);
			
		}
	}
}
