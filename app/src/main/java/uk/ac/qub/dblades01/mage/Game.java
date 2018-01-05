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

public abstract class Game extends AppCompatActivity {
    FastRenderView fastRenderView;
    FileIO fileIO;
    Audio audio;
    Input input;
    Graphics graphics;
    Screen screen;

    public abstract Screen getStartScreen();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int frameBufferWidth, frameBufferHeight;
        float scaleX, scaleY;
        boolean landscape;
        Bitmap frameBuffer;
        DisplayMetrics displayMetrics;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();

        landscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        frameBufferWidth = landscape? 480 : 320;
        frameBufferHeight = landscape? 320 : 480;
        frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        scaleX = frameBufferWidth / displayMetrics.widthPixels;
        scaleY = frameBufferHeight / displayMetrics.heightPixels;

        fastRenderView = new FastRenderView(this, frameBuffer);
        fileIO = new SimpleFileIO(this);
        audio = new SimpleAudio(this);
        input = new SimpleInput(this, fastRenderView, scaleX, scaleY);
        graphics = new SimpleGraphics(getAssets(), frameBuffer);
        screen = getStartScreen();

        setContentView(fastRenderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        fastRenderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        fastRenderView.pause();
        screen.pause();

        if(isFinishing())
            screen.dispose();
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Audio getAudio() {
        return audio;
    }

    public Input getInput() {
        return input;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        if(screen == null)
            throw new IllegalArgumentException("Screen must not be a null value");

        this.screen.pause();
        this.screen.dispose();

        screen.resume();
        screen.update(0);

        this.screen = screen;
    }
}
