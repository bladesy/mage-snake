package uk.ac.qub.dblades01.mage;

public interface Graphics {
    enum PixmapFormat {
        RGB565,
        ARGB4444,
        ARGB8888
    }

    int getWidth();
    int getHeight();

    void clear(int colour);
    void drawPixel(int x, int y, int colour);
    void drawLine(int firstX, int firstY, int secondX, int secondY, int colour);
    void drawRectangle(int x, int y, int width, int height, int colour);

    Pixmap newPixmap(String assetName, PixmapFormat format);
    void drawPixmap(Pixmap pixmap, int x, int y);
    void drawPixmap(Pixmap pixmap, int srcX, int srcY, int srcWidth, int srcHeight, int dstX,
                    int dstY);
}
