package uk.ac.qub.dblades01.mage.graphics;

/* Describes a class that can be drawn to */
public interface Graphics {
    /* Possible formats that a pixmap can store each pixel in */
    enum PixmapFormat {
        RGB565,
        ARGB4444,
        ARGB8888
    }

    /* Getting the dimensions that can be drawn to */
    int getWidth();
    int getHeight();

    /* Drawing in terms of pixels and primitive shapes */
    void clear(int colour);
    void drawPixel(int x, int y, int colour);
    void drawLine(int firstX, int firstY, int secondX, int secondY, int colour);
    void drawRectangle(int x, int y, int width, int height, int colour);

    /* Creation and drawing of pixmaps */
    Pixmap newPixmap(String bitmapName, PixmapFormat format);
    void drawPixmap(Pixmap pixmap, int x, int y);
    void drawPixmap(Pixmap pixmap, int srcX, int srcY, int dstX, int dstY, int width, int height);
}
