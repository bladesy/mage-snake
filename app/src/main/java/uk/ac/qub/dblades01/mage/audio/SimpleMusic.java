package uk.ac.qub.dblades01.mage.audio;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

/* A simple implementation of the Music interface, that also listens for the completion of its
internal MediaPlayer. */
public class SimpleMusic implements Music, OnCompletionListener {
    boolean playerPrepared = false;
    MediaPlayer mediaPlayer;

    /* assetFileDescriptor describes the audio asset that will be streamed into memory and played
    back using mediaPlayer. */
    public SimpleMusic(AssetFileDescriptor assetFileDescriptor) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            playerPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load SimpleMusic");
        }
    }

    /* Play the set audio file using mediaPlayer. */
    @Override
    public void play() {
        if(mediaPlayer.isPlaying())
            return;

        try {
            /* Prevent other threads from accessing playerPrepared during this block, as there is a
            moment where the mediaPlayer is prepared, but playerPrepared is not yet set true. */
            synchronized(this) {
                if(!playerPrepared) {
                    mediaPlayer.prepare();
                    playerPrepared = true;
                }
            }
            mediaPlayer.start();
        }
        catch(IOException e) {
            throw new RuntimeException("Could not prepare SimpleMusic: IO");
        }
        catch(IllegalStateException e) {
            throw new RuntimeException("Could not prepare SimpleMusic: Invalid state");
        }
    }

    /* Pause playback of the set audio file by mediaPlayer. */
    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    /* Stop playback of the set audio file by mediaPlayer, the difference between simply pausing
    being that mediaPlayer must be prepared again. */
    @Override
    public void stop() {
        /* Ensures that playerPrepared is set false immediately after mediaPlayer is stopped, and
        thus needs preparing before playback again. */
        synchronized(this) {
            mediaPlayer.stop();
            playerPrepared = false;
        }
    }

    /* Dispose of the internal mediaPlayer, rendering this SimpleMusic instance unusable. */
    @Override
    public void dispose() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    /* Set whether or not to loop the audio file playback based on the loop parameter. */
    @Override
    public void setLooping(boolean loop) {
        mediaPlayer.setLooping(loop);
    }

    /* Set the volume of the audio file based on the volume parameter, setting both right and left
    channels equally. */
    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    /* Return whether or not it is true that the audio file is currently playing. */
    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /* Return whether or not it is true that the mediaPlayer has been stopped. */
    @Override
    public boolean isStopped() {
        return !playerPrepared;
    }

    /* Return whether or not the audio file is set to loop. */
    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    /* Called when mediaPlayer finishes playing the audio file, to signal that the mediaPlayer must
    be prepared again. */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playerPrepared = false;
    }
}
