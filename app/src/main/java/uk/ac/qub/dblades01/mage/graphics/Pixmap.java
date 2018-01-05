package uk.ac.qub.dblades01.mage.graphics;

import android.graphics.Bitmap;

import uk.ac.qub.dblades01.mage.graphics.Graphics.PixmapFormat;

/* Describes an image file. */
public interface Pixmap {
    /* Return the dimensions of the image. */
    int getWidth();
    int getHeight();

    /* Return the internal Bitmap that stores the image data. */
    Bitmap getBitmap();
    /* Return the format that each pixel consists of. */
    PixmapFormat getFormat();

    /* Remove this Pixmap use. */
    void dispose();
}
