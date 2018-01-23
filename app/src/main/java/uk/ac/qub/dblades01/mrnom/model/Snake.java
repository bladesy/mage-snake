package uk.ac.qub.dblades01.mrnom.model;

import java.util.ArrayList;
import java.util.List;

/* Controlled by the user, inorder to move around the World and eat Ink. */
public class Snake {
    public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
    public int direction;
    public List<SnakePart> parts;

    public Snake() {
        direction = UP;
        parts = new ArrayList<>();

        parts.add(new SnakePart(5, 5));
        parts.add(new SnakePart(5, 6));
        parts.add(new SnakePart(5, 7));
    }

    /* Return the modulo between expression and modulus. */
    private int modulo(int expression, int modulus) {
        return ((expression % modulus) + modulus) % modulus;
    }

    /* Turn the direction that Snake will move, 90 degrees to the left of its current direction. */
    public void turnLeft() {
        direction = modulo(direction + 1, 4);
    }

    /* Turn the direction that Snake will move, 90 degrees to the right of its current direction. */
    public void turnRight() {
        direction = modulo(direction - 1, 4);
    }

    /* Grows Snake by one SnakePart - this should be called when Snake eats Ink. */
    public void eat() {
        SnakePart lastPart;

        lastPart = parts.get(parts.size() - 1);

        parts.add(new SnakePart(lastPart.x, lastPart.y));
    }

    /* Move the Snake in the current direction, with each SnakePart moving into the position of the
    next SnakePart. */
    public void move() {
        int snakeLength;
        SnakePart head;

        snakeLength = parts.size() - 1;
        head = parts.get(0);

        for(int i = snakeLength; i > 0; --i) {
            SnakePart part, partBefore;

            part = parts.get(i);
            partBefore = parts.get(i - 1);

            part.x = partBefore.x;
            part.y = partBefore.y;
        }

        switch(direction) {
            case UP:
                head.y = modulo(head.y - 1, World.HEIGHT);
                break;
            case LEFT:
                head.x = modulo(head.x - 1, World.WIDTH);
                break;
            case DOWN:
                head.y = modulo(head.y + 1, World.HEIGHT);
                break;
            case RIGHT:
                head.x = modulo(head.x + 1, World.WIDTH);
                break;
        }
    }

    /* Return whether or not the head SnakePart of the Snake has collided with its tail. */
    public boolean isBitten() {
        int snakeLength;
        SnakePart head;

        snakeLength = parts.size();
        head = parts.get(0);

        for(int i = 1; i < snakeLength; ++i) {
            SnakePart tail;

            tail = parts.get(i);

            if (head.x == tail.x && head.y == tail.y)
                return true;
        }

        return false;
    }
}
