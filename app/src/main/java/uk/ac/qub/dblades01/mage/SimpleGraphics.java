package uk.ac.qub.dblades01.mage;

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

public class SimpleGraphics implements Graphics {
    private AssetManager assetManager;
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint = new Paint();
    private Rect src = new Rect(), dst = new Rect();

    public SimpleGraphics(AssetManager assetManager, Bitmap frameBuffer) {
        this.assetManager = assetManager;
        this.frameBuffer = frameBuffer;
        canvas = new Canvas(frameBuffer);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }

    @Override
    public void clear(int colour) {
        canvas.drawColor(colour & 0x00ffffff);
    }

    @Override
    public void drawPixel(int x, int y, int colour) {
        paint.setColor(colour);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int firstX, int firstY, int secondX, int secondY, int colour) {
        paint.setColor(colour);
        canvas.drawLine(firstX, firstY, secondX, secondY, paint);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, int colour) {
        paint.setColor(colour);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public Pixmap newPixmap(String bitmapName, PixmapFormat format) {
        Options options;
        Config config;
        InputStream bitmapInput;
        Bitmap bitmap;

        switch(format) {
            case RGB565:
                config = Config.RGB_565;
                break;
            case ARGB4444:
                config = Config.ARGB_4444;
                break;
            case ARGB8888:
            default:
                config = Config.ARGB_8888;
                break;
        }

        options = new Options();
        options.inPreferredConfig = config;
        bitmapInput = null;

        try {
            bitmapInput = assetManager.open(bitmapName);
            bitmap = BitmapFactory.decodeStream(bitmapInput);

            if(bitmap == null)
                throw new IOException();
        }
        catch(IOException e) {
            throw new RuntimeException("Could not load bitmap asset: " + bitmapName);
        }
        finally {
            if(bitmapInput != null) {
                try {
                    bitmapInput.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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

        return new SimplePixmap(bitmap, format);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(pixmap.getBitmap(), x, y, null);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int srcX, int srcY, int dstX, int dstY, int width,
                           int height) {
        src.set(srcX, srcY, srcX + width - 1, srcY + height - 1);
        dst.set(dstX, dstY, dstX + width - 1, dstY + height - 1);
        canvas.drawBitmap(pixmap.getBitmap(), src, dst, null);
    }
}
