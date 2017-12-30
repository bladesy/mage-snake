package uk.ac.qub.dblades01.mage;

import uk.ac.qub.dblades01.mage.Graphics.PixmapFormat;

public interface Pixmap {
    int getWidth();
    int getHeight();

    PixmapFormat getFormat();

    void dispose();
}
