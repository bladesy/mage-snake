package uk.ac.qub.dblades01.mage.audio;

/* Describes an audio source being streamed through a MediaPlayer. */
public interface Music {
    void play();
    void pause();
    void stop();
    void dispose();

    void setLooping(boolean loop);
    void setVolume(float volume);

    boolean isPlaying();
    boolean isStopped();
    boolean isLooping();
}
