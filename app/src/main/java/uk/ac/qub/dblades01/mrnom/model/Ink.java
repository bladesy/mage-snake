package uk.ac.qub.dblades01.mrnom.model;

/* A blob of ink that is eaten by the Snake. */
public class Ink {
    public static final int TYPE_1 = 0, TYPE_2 = 1, TYPE_3 = 2;
    public int type, x, y;

    /* type corresponds to the Pixmap used, while x and y are the coordinates in terms of the World
    coordinate system. */
    public Ink(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
