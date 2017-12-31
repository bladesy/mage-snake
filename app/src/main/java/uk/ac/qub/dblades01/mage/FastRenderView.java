package uk.ac.qub.dblades01.mage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FastRenderView extends SurfaceView implements Runnable {
    private volatile boolean running;
    private Game game;
    private Bitmap frameBuffer;
    private SurfaceHolder surfaceHolder;
    private Thread renderThread;

    public FastRenderView(Game game, Bitmap frameBuffer) {
        super(game);

        running = false;
        this.game = game;
        this.frameBuffer = frameBuffer;
        surfaceHolder = getHolder();
    }

    @Override
    public void run() {
        long startTime;
        Rect surfaceBounds;

        startTime = System.nanoTime();
        surfaceBounds = new Rect();

        while(running) {
            float deltaTime;
            Canvas surfaceCanvas;
            Screen screen;

            if(!surfaceHolder.getSurface().isValid())
                continue;

            deltaTime = (System.nanoTime() - startTime) / 1e9f;
            startTime = System.nanoTime();

            screen = game.getScreen();
            screen.update(deltaTime);
            screen.draw(deltaTime);

            surfaceCanvas = surfaceHolder.lockCanvas();
            surfaceCanvas.getClipBounds(surfaceBounds);
            surfaceCanvas.drawBitmap(frameBuffer, null, surfaceBounds, null);
            surfaceHolder.unlockCanvasAndPost(surfaceCanvas);
        }
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause() {
        running = false;

        while(true) {
            try {
                renderThread.join();
                return;
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
