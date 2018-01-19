package uk.ac.qub.dblades01.mrnom;

import java.util.ArrayList;
import java.util.List;

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

    public void turnLeft() {
        direction = modulo(direction + 1, 4);
    }

    public void turnRight() {
        direction = modulo(direction - 1, 4);
    }

    public void eat() {
        SnakePart lastPart;

        lastPart = parts.get(parts.size() - 1);

        parts.add(new SnakePart(lastPart.x, lastPart.y));
    }

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

    private int modulo(int expression, int modulus) {
        return ((expression % modulus) + modulus) % modulus;
    }
}
