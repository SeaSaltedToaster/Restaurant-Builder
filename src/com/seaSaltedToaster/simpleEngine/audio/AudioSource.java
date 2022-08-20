package com.seaSaltedToaster.simpleEngine.audio;

import org.lwjgl.openal.AL10;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class AudioSource {

	private int sourceID;
	public boolean isPlaying;
	  
	public AudioSource() {
		this.sourceID = AL10.alGenSources();
		AL10.alSourcef(this.sourceID, AL10.AL_PITCH, 1.0F);
		AL10.alSourcef(this.sourceID, AL10.AL_GAIN, 1.0F);
		AL10.alSource3f(this.sourceID, AL10.AL_POSITION, 0f, 0f, 0f);
	}
	  
	public void Play(int buffer) {
		this.isPlaying = true;
		AL10.alSourcei(this.sourceID, 4105, buffer);
		AL10.alSourcePlay(this.sourceID);
		this.isPlaying = false;
	}
	  
	public void delete() {
		AL10.alSourceStop(sourceID);
		AL10.alDeleteSources(this.sourceID);
	}
	
	public void stopSound() {
		AL10.alSourceStop(sourceID);
	}
	
	public void pause() {
		AL10.alSourcePause(sourceID);
	}
	
	public void resume() {
		AL10.alSourcePlay(sourceID);
	}
	
	public void setPosition(Vector3f position) {
		AL10.alSource3f(this.sourceID, AL10.AL_POSITION, position.x, position.y, position.z);
	}
	
	public void setPitch(float pitch) {
		AL10.alSourcef(this.sourceID, AL10.AL_PITCH, pitch);
	}
	
	public void setGain(float gain) {
		AL10.alSourcef(this.sourceID, AL10.AL_GAIN, gain);
	}
	
	public void setVelocity(Vector3f velocity) {
		AL10.alSource3f(this.sourceID, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}
	
	public void setLooping(boolean looping) {
		AL10.alSourcei(this.sourceID, AL10.AL_LOOPING, looping ? AL10.AL_TRUE : AL10.AL_FALSE);
	}


}
