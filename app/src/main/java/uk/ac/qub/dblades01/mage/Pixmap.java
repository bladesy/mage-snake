package uk.ac.qub.dblades01.mage;

import android.graphics.Bitmap;

import uk.ac.qub.dblades01.mage.Graphics.PixmapFormat;

public interface Pixmap {
    int getWidth();
    int getHeight();

    Bitmap getBitmap();
    PixmapFormat getFormat();

    void dispose();
}
