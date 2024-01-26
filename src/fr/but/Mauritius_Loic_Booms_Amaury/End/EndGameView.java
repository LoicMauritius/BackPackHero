package fr.but.Mauritius_Loic_Booms_Amaury.End;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;

/**
 * The EndGameView class of this game.
 * It permit to display the ending menu when the game is complete
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class EndGameView {

	/**
	 * This method permit to display the endgame menu
	 * 
	 * @param context 	ApplicationContext ( zen5 )
	 */
	public void drawEndMenu(ApplicationContext context) {
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		context.renderFrame(graphics -> {
			
			graphics.clearRect(0, 0, Math.round(width), Math.round(height));
			//image de fond
			try {
				var image = ImageIO.read(Files.newInputStream(Path.of("img","ending_fond.png")));
				graphics.drawImage(image, 0, 0, Math.round(width),Math.round(height), null);
			} catch (IOException e) {
				//Couleur de fond
				graphics.setBackground(Color.BLACK);
				e.printStackTrace();
			}
			
		});
	}
}
