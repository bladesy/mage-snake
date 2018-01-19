package uk.ac.qub.dblades01.mrnom;

import java.util.List;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.graphics.Pixmap;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;

public class GameScreen extends Screen {
    private enum GameState {
        READY,
        RUNNING,
        PAUSED,
        GAME_OVER;
    }

    GameState state;
    World world;

    /* game is the Game object that this Screen acts as a section of. */
    public GameScreen(Game game) {
        super(game);

        state = GameState.READY;
        world = new World();
    }

    /* Update components. */
    @Override
    public void update(float deltaTime) {
        Input input;
        List<TouchEvent> touchEvents;

        input = game.getInput();
        touchEvents = input.getTouchEvents();
        input.getKeyEvents();

        switch(state) {
            case READY:
                updateReady(touchEvents);
                break;
            case RUNNING:
                updateRunning(deltaTime, touchEvents);
                break;
            case PAUSED:
                updatePaused(touchEvents);
                break;
            case GAME_OVER:
                updateGameOver(touchEvents);
                break;
        }
    }

    /* Draw components to the frame buffer. */
    @Override
    public void draw(float deltaTime) {
        Graphics graphics;

        graphics = game.getGraphics();

        switch(state) {
            case READY:
                drawReady(graphics);
                break;
            case RUNNING:
                drawRunning(graphics);
                break;
            case PAUSED:
                drawPaused(graphics);
                break;
            case GAME_OVER:
                drawGameOver(graphics);
                break;
        }
    }

    /* Set up. */
    @Override
    public void resume() {

    }

    /* Clean up. */
    @Override
    public void pause() {

    }

    /* Remove this Screen from use. */
    @Override
    public void dispose() {

    }

    /* Return whether or not touchEvent occured within the rectangle of top-left point (x, y) and of
    the given width and height. */
    private boolean touchInBounds(TouchEvent touchEvent, int x, int y, int width, int height) {
        if(touchEvent.x > x && touchEvent.x < (x + width - 1)
                && touchEvent.y > y && touchEvent.y < (y + height - 1))
            return true;
        return false;
    }

    private void drawNumber(int number, int x, int y, Graphics graphics) {
        graphics.drawPixmap(Assets.numbers, 40 * number, 0, x, y, 40, 40);
    }

    private void drawScore(int score, int x, int y, Graphics graphics) {
        for(int i = 0; i < 4 && score > 0; ++i) {
            drawNumber(score % 10, x - (i * 40), y, graphics);
            score /= 10;
        }
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        for(TouchEvent touchEvent : touchEvents)
            if(touchEvent.type == TouchEvent.TOUCH_UP)
                if(touchInBounds(touchEvent, 40, 200, 240, 80))
                    state = GameState.RUNNING;
    }

    private void updateRunning(float deltaTime, List<TouchEvent> touchEvents) {
        for(TouchEvent touchEvent : touchEvents) {
            if(touchEvent.type == TouchEvent.TOUCH_UP) {
                if(touchInBounds(touchEvent, 0, 0, 40, 40))
                    state = GameState.PAUSED;
                else if(touchInBounds(touchEvent, 0, 440, 40, 40))
                    world.snake.turnLeft();
                else if(touchInBounds(touchEvent, 280, 440, 40, 40))
                    world.snake.turnRight();
            }
        }

        world.update(deltaTime);

        if(world.gameEnded)
            state = GameState.GAME_OVER;
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        for(TouchEvent touchEvent : touchEvents) {
            if(touchEvent.type == TouchEvent.TOUCH_UP) {
                if(touchInBounds(touchEvent, 40, 160, 280, 80))
                    state = GameState.RUNNING;
                else if(touchInBounds(touchEvent, 40, 240, 280, 80))
                    game.setScreen(new MenuScreen(game));
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        for(TouchEvent touchEvent : touchEvents)
            if(touchEvent.type == TouchEvent.TOUCH_UP)
                if(touchInBounds(touchEvent, 0, 440, 40, 40)) {
                    Settings.addScore(world.score);
                    game.setScreen(new MenuScreen(game));
                }
    }

    private void drawReady(Graphics graphics) {
        graphics.drawPixmap(Assets.ready, 40, 200);
    }

    private void drawRunning(Graphics graphics) {
        Pixmap ink;

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.arrowLeft, 0, 440);
        graphics.drawPixmap(Assets.arrowRight, 280, 440);
        graphics.drawPixmap(Assets.pause, 0, 0);

        drawScore(world.score, 80, 440, graphics);

        switch(world.ink.type) {
            default:
            case 0:
                ink = Assets.ink1;
                break;
            case 1:
                ink = Assets.ink2;
                break;
            case 2:
                ink = Assets.ink3;
                break;
        }

        graphics.drawPixmap(ink, world.ink.x * 40, world.ink.y * 40);

        for(int i = 0, snakeLength = world.snake.parts.size(); i < snakeLength; ++i) {
            SnakePart part;
            Pixmap partPixmap;

            part = world.snake.parts.get(i);

            if(i == 0)
                switch (world.snake.direction) {
                    default:
                    case 0:
                        partPixmap = Assets.headUp;
                        break;
                    case 1:
                        partPixmap = Assets.headLeft;
                        break;
                    case 2:
                        partPixmap = Assets.headDown;
                        break;
                    case 3:
                        partPixmap = Assets.headRight;
                        break;
                }
            else
                partPixmap = Assets.tail;

            graphics.drawPixmap(partPixmap, part.x * 40, part.y * 40);
        }
    }

    private void drawPaused(Graphics graphics) {
        graphics.drawPixmap(Assets.resume, 40, 160);
        graphics.drawPixmap(Assets.quit, 40, 240);
    }

    private void drawGameOver(Graphics graphics) {
        graphics.drawPixmap(Assets.gameOver, 40, 200);
        graphics.drawPixmap(Assets.cancel, 0, 440);
    }
}
