package uk.ac.qub.dblades01.mage.graphics;

import android.graphics.Bitmap;

import uk.ac.qub.dblades01.mage.graphics.Graphics.PixmapFormat;

/* A simple implementation of the Pixmap interface */
public class SimplePixmap implements Pixmap {
    private Bitmap bitmap;
    private PixmapFormat format;

    /* Pass in the Bitmap that this Pixmap represents, along with the Pixmap format that represents
    the format that the Bitmap is in */
    public SimplePixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    /* Return the width of the image */
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    /* Return the height of the image */
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    /* Return the internal Bitmap containing the image data */
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    /* Return the format presenting that which the internal Bitmap is stored in */
    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    /* Remove the internal Bitmap from memory */
    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
