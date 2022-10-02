package com.seaSaltedToaster.restaurantGame.audio;

import java.util.Random;

import org.lwjgl.openal.AL10;

import com.seaSaltedToaster.simpleEngine.audio.AudioSource;
import com.seaSaltedToaster.simpleEngine.audio.management.AudioMaster;

public class TrackPlayer {
	
	//Player of the music
	public AudioSource player;
	
	//Music list
	private String[] tracks;
	private final int MAX_TRACKS = 2;
	
	private int curTrack = -1;
	private String lastTrack;
	
	public TrackPlayer() {
		this.tracks = new String[MAX_TRACKS];
		addTracks();
		
		this.player = new AudioSource();
	}
	
	public void update() {
		boolean isPlaying = AL10.alGetSourcei(curTrack, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
				
		if(!isPlaying) {
			playNextTrack();
		}
	}
	
	public void playNextTrack() {
	      AL10.alDeleteBuffers(curTrack);
	      
	      String nextName = getRandomTrack();
	      lastTrack = nextName;
	      
	      int track = AudioMaster.loadSound(nextName);
	      curTrack = track;
	      
	      player.Play(track);
	}

	private String getRandomTrack() {
		Random random = new Random();
		int track = random.nextInt(MAX_TRACKS);
		String newTrack = tracks[track];
		if(newTrack == lastTrack)
			return getRandomTrack();
		return newTrack;
	}

	private void addTracks() {
		String ambition = "sounds/ambition";
		this.tracks[0] = ambition;
		String dream = "sounds/dream";
		this.tracks[1] = dream;
	}

}
