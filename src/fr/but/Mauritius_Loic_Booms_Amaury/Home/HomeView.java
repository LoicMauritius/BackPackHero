package fr.but.Mauritius_Loic_Booms_Amaury.Home;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;

/**
 * The View of the menu.
 * It manages all the graphic interface of the menu
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class HomeView {
	
	/**
	 * The main drawing of the menu.
	 * It permit to display all the accueil menu ( background, title and buttons)
	 * 
	 * @param context GameContext
	 */
	public void drawaccueil(ApplicationContext context) {
		//Interface graphique de l'accueil
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		context.renderFrame(graphics -> {
			//image de fond
			try {
				var image = ImageIO.read(Files.newInputStream(Path.of("img","backpack_hero_fond_accueil.png")));
				graphics.drawImage(image, 0, 0, Math.round(width),Math.round(height), null);
			} catch (IOException e) {
				//Couleur de fond
				graphics.setBackground(Color.BLACK);
				e.printStackTrace();
			}
					
			//logo du jeu BackPack Hero
			try {
				var image = ImageIO.read(Files.newInputStream(Path.of("img","backpack_hero_logo.png")));
				graphics.drawImage(image, 100, 100, Math.round(width/3),Math.round(height/4), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
					
			var margin_bouton = 100;
			//Bouton play (lance le jeu) et Bouton quit ( ferme le jeu)
			try {
				var taille_bouton = Math.round(width/10);
						
				var image = ImageIO.read(Files.newInputStream(Path.of("img","play.png")));
				graphics.drawImage(image,margin_bouton, Math.round(height-margin_bouton-taille_bouton),taille_bouton,taille_bouton, null);
				
				var image2 = ImageIO.read(Files.newInputStream(Path.of("img","quit.png")));
				graphics.drawImage(image2, margin_bouton + Math.round(width/6), Math.round(height-margin_bouton-taille_bouton), taille_bouton,taille_bouton, null);
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * It permit to remove all the accueil menu
	 * 
	 * @param context GameContext
	 */
	public void remove_accueil(ApplicationContext context) {
		
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		
		context.renderFrame(graphics -> {
			//On retire tout le menu
			graphics.clearRect(0, 0, Math.round(width), Math.round(height));
			var fond = new Rectangle2D.Double(0,0,width,height);
			graphics.draw(fond);
			graphics.fill(fond);
		});
	}
}
