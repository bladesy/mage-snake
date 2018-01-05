package uk.ac.qub.dblades01.mage.audio;

/* Describes a creator of audio-related instances from assets. */
public interface Audio {
    Music newMusic(String assetName);
    Sound newSound(String assetName);
}
