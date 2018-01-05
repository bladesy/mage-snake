package uk.ac.qub.dblades01.mage.graphics;

import android.graphics.Bitmap;

import uk.ac.qub.dblades01.mage.graphics.Graphics.PixmapFormat;

/* A simple implementation of the Pixmap interface. */
public class SimplePixmap implements Pixmap {
    private Bitmap bitmap;
    private PixmapFormat format;

    /* bitmap is the image this Pixmap represents, along with format, which represents the format
    that the Bitmap is in. */
    public SimplePixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    /* Return the width of the image. */
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    /* Return the height of the image. */
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    /* Return the internal Bitmap, bitmap. */
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    /* Return the pixel format bitmap is stored in. */
    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    /* Remove bitmap from use. */
    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
