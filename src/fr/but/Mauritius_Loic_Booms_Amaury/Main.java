package fr.but.Mauritius_Loic_Booms_Amaury;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import fr.but.Mauritius_Loic_Booms_Amaury.Home.HomeModel;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;

/**
 * The Main class of this game is the starter of the game which directly lunch the game menu
 * 
 * @author Mauritius Loic and Booms Amaury
 */
public class Main {
	/**
	 * Start the game
	 * 
	 * @param context  GameContext
	 */
	private static void BackPack_hero(ApplicationContext context) throws InterruptedException {
	
		while(true) {
			new HomeModel(context);
		}
	}

	/**
	 * Executable program.
	 * 
	 * @param args Spurious arguments.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		Application.run(Color.BLACK, t -> {
			try {
				BackPack_hero(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
