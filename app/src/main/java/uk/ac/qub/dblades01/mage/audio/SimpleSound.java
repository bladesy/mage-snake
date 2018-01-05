package uk.ac.qub.dblades01.mage.audio;

import android.media.SoundPool;

/* A simple implementation of the Sound interface. */
public class SimpleSound implements Sound {
    int soundId;
    SoundPool soundPool;

    /* soundPool is the SoundPool instance that the audio is stored within, the integer id being
    that which it has been assigned in the SoundPool to distinguish it from other audio stored there
    also. */
    public SimpleSound(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    /* Play the associated audio from soundPool at the passed in volume. */
    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    /* Dispose of the associated audio from the soundPool, unloading it from memory. */
    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }
}
