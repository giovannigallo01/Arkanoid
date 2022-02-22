package application.sound;

import java.io.File;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {

	HashMap<String, Clip> clips;
	Clip clip;
	File file;
	AudioInputStream in;
	private static SoundHandler instance = null;
	
	public SoundHandler() {
		clips = new HashMap<String, Clip>();
		loadSound("collision", "collision.wav");
		loadSound("fail", "fail.wav");
		loadSound("game", "game.wav");
		loadSound("menu", "menu.wav");
		loadSound("win", "win.wav");
	}
	
	private void loadSound(String name, String fileName) {
		try {
			file = new File("src/application/resources/sounds/" + fileName);
			in = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clips.put(name, clip);
			clips.get(name).open(in);
		} catch (Exception e) {
			clip = null;
			e.printStackTrace();
		}
	}
	
	public static SoundHandler getInstance() {
		if(instance == null)
			instance = new SoundHandler();
		return instance;
	}
	
	public void loop(String name) {
		clip = getClip(name);
		if(clip != null) {
			stop(name);
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void start(String name) {
		clip = getClip(name);
		if(clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stop(String name) {
		clip = getClip(name);
		if(clip != null)
			clip.stop();
	}
	
	private Clip getClip(String name) {
		return clips.get(name);
	}
}
