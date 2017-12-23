package uk.ac.qub.dblades01.mage;

import android.media.SoundPool;

public class SimpleSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public SimpleSound(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }
}
