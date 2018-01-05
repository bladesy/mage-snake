package uk.ac.qub.dblades01.mage.audio;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/* A simple implementation of the Audio interface. */
public class SimpleAudio implements Audio {
    AssetManager assetManager;
    SoundPool soundPool;

    /* Configures audio for the the application and instantiates assetManager using activity. */
    public SimpleAudio(Activity activity) {
        /* Set the activity audio output to the music stream. */
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        /* Get the activity AssetManager to access audio assets. */
        assetManager = activity.getAssets();
        /* Create a SoundPool to store the short audio clips of Sound objects in memory. */
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
    }

    /* Return a new Music instance using the assetName of an audio asset. */
    @Override
    public Music newMusic(String assetName) {
        AssetFileDescriptor assetFileDescriptor;

        try {
            assetFileDescriptor = assetManager.openFd(assetName);
            return new SimpleMusic(assetFileDescriptor);
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load Music asset: " + assetName);
        }
    }

    /* Return a new Sound instance using the assetName of an audio asset. */
    @Override
    public Sound newSound(String assetName) {
        int soundId;
        AssetFileDescriptor assetFileDescriptor;

        try {
            assetFileDescriptor = assetManager.openFd(assetName);
            soundId = soundPool.load(assetFileDescriptor, 0);
            return new SimpleSound(soundPool, soundId);
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load Sound asset: " + assetName);
        }
    }
}
