package fr.but.Mauritius_Loic_Booms_Amaury.Game.Sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread{

    private final String filePath;
    private AudioInputStream audioInputStream = null;
    private SourceDataLine line;
    private float volume = 1.0f;

    public Audio(String filePath) {
        this.filePath = filePath;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (line != null) {
            Control[] controls = line.getControls();
            for (Control control : controls) {
                if (control instanceof FloatControl) {
                    FloatControl floatControl = (FloatControl) control;
                    if (floatControl.getType() == FloatControl.Type.MASTER_GAIN) {
                        floatControl.setValue((float) (Math.log10(volume) * 20.0));
                    }
                }
            }
        }
    }

    public void run(){
        File fichier = new File(filePath);
        try {
            AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier);
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            audioInputStream = AudioSystem.getAudioInputStream(fichier);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);

        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        try {
            line.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        setVolume(volume);

        line.start();

        byte bytes[] = new byte[1024];
        int bytesRead=0;

        while(true) {
            try {
                while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                    line.write(bytes, 0, bytesRead);
                }
            } catch (IOException io) {
                io.printStackTrace();
            }

            // Vérifie si le thread a été interrompu
            if (Thread.currentThread().isInterrupted()) {
                break;
            }

            // Rejoue le son depuis le début
            try {
            	if (Thread.currentThread().isInterrupted()) {
            		audioInputStream.reset();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Arrête la lecture audio et libère les ressources
        line.stop();
        line.close();
    }
}
	
