package uk.ac.qub.dblades01.mrnom.model;

import java.util.Random;

public class World {
    public static final int WIDTH = 8, HEIGHT = 11, INK_SCORE = 10;
    public static final float TICK_INTIAL = 0.5f, TICK_DECREMENT = 0.05f;

    public int score;
    public float tick, tickTime;
    public boolean gameEnded;
    public boolean[][] grid;
    public Random random;
    public Snake snake;
    public Ink ink;

    public World() {
        score = 0;
        tick = TICK_INTIAL;
        tickTime = 0;
        gameEnded = false;
        grid = new boolean[WIDTH][HEIGHT];
        random = new Random();
        snake = new Snake();
        placeInk();
    }

    /* Replace ink with a new Ink instance of different position in the World. */
    private void placeInk() {
        int inkX, inkY;

        for(int x = 0; x < WIDTH; ++x)
            for(int y = 0; y < HEIGHT; ++y)
                grid[x][y] = false;

        for(SnakePart part : snake.parts)
            grid[part.x][part.y] = true;

        inkX = random.nextInt(WIDTH);
        inkY = random.nextInt(HEIGHT);

        while(true) {
            if(grid[inkX][inkY] == false)
                break;

            inkX += 1;

            if(inkX >= WIDTH) {
                inkX = 0;
                inkY += 1;

                if(inkY >= HEIGHT)
                    inkY = 0;
            }
        }

        ink = new Ink(random.nextInt(3), inkX, inkY);
    }

    /* Update the World based on deltaTime being the time in seconds since the last update. This
    World update should keep its state up to date, and keep snake and ink moving. */
    public void update(float deltaTime) {
        if(gameEnded)
            return;

        tickTime += deltaTime;

        while(tickTime > tick) {
            SnakePart head;

            tickTime -= tick;
            snake.move();

            if(snake.isBitten()) {
                gameEnded = true;
                return;
            }

            head = snake.parts.get(0);

            if(head.x == ink.x && head.y == ink.y) {
                score += INK_SCORE;
                snake.eat();

                if (snake.parts.size() == WIDTH * HEIGHT) {
                    gameEnded = true;
                    return;
                }

                placeInk();
            }

            if(score % 100 == 0 && tick - TICK_DECREMENT > 0)
                tick = TICK_INTIAL - (TICK_DECREMENT * (score / 100));
        }
    }
}
