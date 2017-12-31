package uk.ac.qub.dblades01.mage;

import android.graphics.Bitmap;

import uk.ac.qub.dblades01.mage.Graphics.PixmapFormat;

public class SimplePixmap implements Pixmap {
    private Bitmap bitmap;
    private PixmapFormat format;

    public SimplePixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
