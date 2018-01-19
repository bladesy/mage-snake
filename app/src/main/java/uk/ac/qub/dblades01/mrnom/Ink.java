package uk.ac.qub.dblades01.mrnom;

public class Ink {
    public static final int TYPE_1 = 0, TYPE_2 = 1, TYPE_3 = 2;
    public int type, x, y;

    public Ink(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
