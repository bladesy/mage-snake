package uk.ac.qub.dblades01.mage;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import android.view.WindowManager;

import uk.ac.qub.dblades01.mage.audio.Audio;
import uk.ac.qub.dblades01.mage.audio.SimpleAudio;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.graphics.SimpleGraphics;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.SimpleInput;
import uk.ac.qub.dblades01.mage.io.FileIO;
import uk.ac.qub.dblades01.mage.io.SimpleFileIO;

/* Provides access to all subsystems of the engine, while keeping track of the current Screen.
Extends AppCompatActivity to act as the entry point into the application, and provide context to any
subsystem. */
public abstract class Game extends AppCompatActivity {
    private FastRenderView fastRenderView;
    private FileIO fileIO;
    private Audio audio;
    private Input input;
    private Graphics graphics;
    private Screen screen;

    /* The extending class becomes an actual Game when it specifies the first Screen to use. */
    public abstract Screen getStartScreen();

    /* Called when the application starts. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int frameBufferWidth, frameBufferHeight;
        float scaleX, scaleY;
        boolean landscape;
        Bitmap frameBuffer;
        DisplayMetrics displayMetrics;

        /* Make the application fullscreen. */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();

        /* landscape is true if the device is in landscape orientation. */
        landscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        /* If the device is landscape, then the frameBuffer is in landscape also. */
        frameBufferWidth = landscape? 480 : 320;
        frameBufferHeight = landscape? 320 : 480;
        frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);

        /* Instantiate and fill a DisplayMetrics objects to obtain the actual resolution of the
        device's display. */
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        /* Calculate to which scale frameBuffer is in comparison to the actual display. */
        scaleX = (float) frameBufferWidth / displayMetrics.widthPixels;
        scaleY = (float) frameBufferHeight / displayMetrics.heightPixels;

        /* Instantiate subsystems. */
        fastRenderView = new FastRenderView(this, frameBuffer);
        fileIO = new SimpleFileIO(this);
        audio = new SimpleAudio(this);
        input = new SimpleInput(this, fastRenderView, scaleX, scaleY);
        graphics = new SimpleGraphics(getAssets(), frameBuffer);
        screen = getStartScreen();

        /* Display fastRenderView. */
        setContentView(fastRenderView);
    }

    /* Called whenever the application starts, whether it be starting for the first time, restarting
    after switching applications, or restarting after the device wakes from sleeping. */
    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        fastRenderView.resume();
    }

    /* Called whenever the application stops, whether it be stopping for the last time, to switch
    applications, or to sleep. */
    @Override
    public void onPause() {
        super.onPause();
        screen.pause();
        fastRenderView.pause();

        /* If the application is pausing because it's about to finish, then dispose of the
        screen. */
        if(isFinishing())
            screen.dispose();
    }

    /* Return the FileIO subsystem accessor. */
    public FileIO getFileIO() {
        return fileIO;
    }

    /* Return the Audio subsystem accessor. */
    public Audio getAudio() {
        return audio;
    }

    /* Return the Input subsystem accessor. */
    public Input getInput() {
        return input;
    }

    /* Return the Graphics subsystem accessor. */
    public Graphics getGraphics() {
        return graphics;
    }

    /* Return the current Screen being used. */
    public Screen getScreen() {
        return screen;
    }

    /* Set the screen member variable to the parameter screen. */
    public void setScreen(Screen screen) {
        if(screen == null)
            throw new IllegalArgumentException("Screen must not be a null value");

        /* The previous Screen must clean up and dispose of itself before the new Screen can be
        set. */
        this.screen.pause();
        this.screen.dispose();

        /* Set up the new Screen. */
        screen.resume();
        screen.update(0);

        /* Setting the new Screen as the current Screen. */
        this.screen = screen;
    }
}
