package uk.ac.qub.dblades01.mage;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class SimpleAudio implements Audio {
    AssetManager assetManager;
    SoundPool soundPool;

    public SimpleAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assetManager = activity.getAssets();
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
    }

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
