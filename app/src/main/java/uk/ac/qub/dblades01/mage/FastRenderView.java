package uk.ac.qub.dblades01.mage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/* Extends from SurfaceView, meaning that when this class is displayed as a view, it will display
the contents of a Surface object, which is handled by the internal SurfaceHolder. Additionally,
implementing Runnable means that this class contains and runs as the game thread. */
public class FastRenderView extends SurfaceView implements Runnable {
    private volatile boolean running;
    private Game game;
    private Bitmap frameBuffer;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;

    /* game is passed in not only to instantiate the game member variable, but because it inherits
    from context, it can be passed into the SurfaceView constructor - while frameBuffer is the
    Bitmap to be displayed on the screen. */
    public FastRenderView(Game game, Bitmap frameBuffer) {
        super(game);

        this.game = game;
        this.frameBuffer = frameBuffer;
        running = false;
        /* A wrapper around the Surface that frameBuffer is drawn to. */
        surfaceHolder = getHolder();
    }

    /* Runs as the game thread, looping the process of taking the frame delta time, updating the
    screen, drawing the screen, then sending the drawn frameBuffer of this new frame to be displayed
    on the Surface. */
    @Override
    public void run() {
        long startTime;
        Rect surfaceBounds;

        startTime = System.nanoTime();
        surfaceBounds = new Rect();

        /* The game loop. */
        while(running) {
            float deltaTime;
            Canvas surfaceCanvas;
            Screen screen;

            /* Wait until Surface can be drawn to before starting a new frame. */
            if(!surfaceHolder.getSurface().isValid())
                continue;

            /* Convert the time delta between this frame and the last into seconds. */
            deltaTime = (System.nanoTime() - startTime) / 1e9f;
            startTime = System.nanoTime();

            /* Update and draw the new frame of the current screen. */
            screen = game.getScreen();
            screen.update(deltaTime);
            screen.draw(deltaTime);

            /* Draw frameBuffer to the entire Surface, using the bounds of the Surface itself to
            ensure this. */
            surfaceCanvas = surfaceHolder.lockCanvas();
            surfaceCanvas.getClipBounds(surfaceBounds);
            surfaceCanvas.drawBitmap(frameBuffer, null, surfaceBounds, null);
            surfaceHolder.unlockCanvasAndPost(surfaceCanvas);
        }
    }

    /* Called when the application resumes, inorder to signal that the game loop should run again,
    while also creating and starting the game thread that contains the loop. */
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /* Called when the application pauses, inorder to signal to the game loop to stop running, while
    also waiting for the game thread to end. */
    public void pause() {
        running = false;

        /* Wait for the game thread to end. */
        while(true) {
            try {
                gameThread.join();
                return;
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
