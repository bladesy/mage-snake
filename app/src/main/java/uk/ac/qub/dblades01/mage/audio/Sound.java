package uk.ac.qub.dblades01.mage.audio;

/* Describes an audio source stored in a SoundPool in memory. */
public interface Sound {
    void play(float volume);
    void dispose();
}
