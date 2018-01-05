package uk.ac.qub.dblades01.mage.graphics;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/* A simple implementation of the Graphics interface */
public class SimpleGraphics implements Graphics {
    private AssetManager assetManager;
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint = new Paint();
    private Rect src = new Rect(), dst = new Rect();

    /* Pass in the frameBuffer Bitmap for canvas to draw on, along with the assetManager for
    creating Pixmaps */
    public SimpleGraphics(AssetManager assetManager, Bitmap frameBuffer) {
        this.assetManager = assetManager;
        this.frameBuffer = frameBuffer;
        canvas = new Canvas(frameBuffer);
    }

    /* Get the width of the drawable area */
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    /* Get the height of the drawable area */
    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }

    /* Clear the drawable area with the passed in colour at full opacity */
    @Override
    public void clear(int colour) {
        canvas.drawColor(colour & 0x00ffffff);
    }

    /* Draw a pixel at (x, y) of the passed in colour */
    @Override
    public void drawPixel(int x, int y, int colour) {
        paint.setColor(colour);
        canvas.drawPoint(x, y, paint);
    }

    /* Draw a line from (firstX, firstY) to (secondX, secondY) of the passed in colour */
    @Override
    public void drawLine(int firstX, int firstY, int secondX, int secondY, int colour) {
        paint.setColor(colour);
        canvas.drawLine(firstX, firstY, secondX, secondY, paint);
    }

    /* Draw a filled rectangle of top-left point at (x, y), with the passed in width and height, and
    of passed in colour */
    @Override
    public void drawRectangle(int x, int y, int width, int height, int colour) {
        paint.setColor(colour);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    /* Return a Pixmap using the Bitmap asset of bitmapName, storing its pixels in memory according
    to the passed in format */
    @Override
    public Pixmap newPixmap(String bitmapName, PixmapFormat format) {
        Config config;
        Options options;
        InputStream bitmapInput;
        Bitmap bitmap;

        /* Set config to the corresponding format value that BitmapFactory uses - note that the
        format parameter merely indicates the preferred format, it is not guaranteed */
        switch(format) {
            case RGB565:
                config = Config.RGB_565;
                break;
            case ARGB4444:
                config = Config.ARGB_4444;
                break;
            /* If an invalid format is passed in, then we assume ARGB8888 will be used */
            case ARGB8888:
            default:
                config = Config.ARGB_8888;
                break;
        }

        options = new Options();
        options.inPreferredConfig = config;
        /* Set to null inorder to check whether an instance has been succesfully created later on */
        bitmapInput = null;

        try {
            bitmapInput = assetManager.open(bitmapName);
            bitmap = BitmapFactory.decodeStream(bitmapInput, null, options);

            /* Throws an error if decodeStream returns null */
            if(bitmap == null)
                throw new IOException();
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load bitmap asset: " + bitmapName);
        }
        finally {
            /* If the bitmapInput was given an InputStream, close it */
            if(bitmapInput != null) {
                try {
                    bitmapInput.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        /* Set format to the corresponding value based on the actual pixel format that bitmap was
        created with - this is necessary, as there were no guarantees that the preferred format was
        used */
        switch(bitmap.getConfig()) {
            case RGB_565:
                format = PixmapFormat.RGB565;
                break;
            case ARGB_4444:
                format = PixmapFormat.ARGB4444;
                break;
            case ARGB_8888:
            default:
                format = PixmapFormat.ARGB8888;
                break;
        }

        /* Create and return a new SimplePixmap instance, passing through bitmap and the actual
        PixmapFormat of bitmap */
        return new SimplePixmap(bitmap, format);
    }

    /* Draw the passed in Pixmap to the drawable area with top-left position being (x, y) */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(pixmap.getBitmap(), x, y, null);
    }

    /* Draw a rectangular section of pixmap of passed in width and height, having a top-left
    position of (srcX, srcY), to the drawable area where it remains the same size and has a top-left
    position of (dstX, dstY) */
    @Override
    public void drawPixmap(Pixmap pixmap, int srcX, int srcY, int dstX, int dstY, int width,
                           int height) {
        src.set(srcX, srcY, srcX + width - 1, srcY + height - 1);
        dst.set(dstX, dstY, dstX + width - 1, dstY + height - 1);
        canvas.drawBitmap(pixmap.getBitmap(), src, dst, null);
    }
}
