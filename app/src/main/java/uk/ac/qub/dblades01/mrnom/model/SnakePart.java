package uk.ac.qub.dblades01.mrnom.model;

/* A segment of a Snake, including its head. */
public class SnakePart {
    public int x, y;

    /* x and y are the coordinates of the SnakePart in terms of the World. */
    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
