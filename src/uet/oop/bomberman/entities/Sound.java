package uet.oop.bomberman.entities;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.stream.events.StartDocument;

import uet.oop.bomberman.BombermanGame;

public class Sound {

	private static Clip clip;

	public static BombermanGame bGame = new BombermanGame();
	public static void play(String path) {
		try {
			File file = new File("res\\sound\\" + path + ".wav");
			AudioInputStream in = AudioSystem.getAudioInputStream(file);

			clip = AudioSystem.getClip();

			clip.open(in);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(BombermanGame.test);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}	
	}
	
	public static void start() {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(BombermanGame.test);
			clip.start();
	}

	public static void loopInf() {
		if (clip.isActive()) {
			return;
		}
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
