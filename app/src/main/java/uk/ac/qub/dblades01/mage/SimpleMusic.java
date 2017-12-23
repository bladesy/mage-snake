package uk.ac.qub.dblades01.mage;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

public class SimpleMusic implements Music, OnCompletionListener {
    boolean isPrepared = false;
    MediaPlayer mediaPlayer;

    public SimpleMusic(AssetFileDescriptor assetFileDescriptor) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load SimpleMusic");
        }
    }

    @Override
    public void play() {
        if(mediaPlayer.isPlaying())
            return;

        try {
            synchronized(this) {
                if(!isPrepared) {
                    mediaPlayer.prepare();
                    isPrepared = true;
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

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public synchronized void stop() {
        mediaPlayer.stop();
        isPrepared = false;
    }

    @Override
    public void dispose() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void setLooping(boolean loop) {
        mediaPlayer.setLooping(loop);
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return !isPrepared;
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
        isPrepared = false;
    }
}
