package uk.ac.qub.dblades01.mage.graphics;

import android.graphics.Bitmap;

/* Describes an image file */
public interface Pixmap {
    /* Get the dimensions of the image */
    int getWidth();
    int getHeight();

    /* Get the internal Bitmap that stores the image data */
    Bitmap getBitmap();
    /*  Get the format that each pixel consists of */
    Graphics.PixmapFormat getFormat();

    /* Remove the image from memory and use */
    void dispose();
}
