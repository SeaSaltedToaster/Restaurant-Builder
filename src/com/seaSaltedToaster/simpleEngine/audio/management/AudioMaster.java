package com.seaSaltedToaster.simpleEngine.audio.management;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class AudioMaster {

	private static List<Integer> buffers = new ArrayList<>();
	  
	  private static long device;
	  
	  public static void init(Engine engine) {
		  device = alcOpenDevice((ByteBuffer) null);
	      if (device == NULL) {
	            throw new IllegalStateException("Failed to open the default OpenAL device.");
	      }
	        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
	        long context = alcCreateContext(device, (IntBuffer) null);
	        if (context == NULL) {
	            throw new   IllegalStateException("Failed to create OpenAL context.");
	        }
	        ALC10.alcMakeContextCurrent(context);
	        AL.createCapabilities(deviceCaps);
	    
	    getListenerData(engine.getCamera().getPosition());
	  }
	  
	  public static void getListenerData(Vector3f pos) {
		  AL10.alListener3f(AL10.AL_POSITION, pos.x, pos.y, pos.z);
		  AL10.alListener3f(AL10.AL_VELOCITY, 0.0F, 0.0F, 0.0F);
	  }
	  
	  public static int loadSound(String file) {
	    int buffer = AL10.alGenBuffers();
	    buffers.add(Integer.valueOf(buffer));
	    WaveData waveFile = WaveData.create("/" + file + ".wav");
	    AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();
	    return buffer;
	  }
	  
	  public static void cleanUp() {
	    for (Iterator<Integer> iterator = buffers.iterator(); iterator.hasNext(); ) {
	      int buffer = ((Integer)iterator.next()).intValue();
	      AL10.alDeleteBuffers(buffer);
	    } 
	  }
	
	
}
