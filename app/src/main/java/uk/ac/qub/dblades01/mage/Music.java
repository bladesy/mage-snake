package uk.ac.qub.dblades01.mage;

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
