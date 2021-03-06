package uk.ac.qub.dblades01.mrnom.screens;

import java.util.List;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;
import uk.ac.qub.dblades01.mrnom.Assets;
import uk.ac.qub.dblades01.mrnom.Settings;

/* Shows a scoreboard of the highest scores achieved in the game on the device. */
public class ScoreScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public ScoreScreen(Game game) {
        super(game);
    }

    /* Handle button presses. */
    @Override
    public void update(float deltaTime) {
        Input input;
        List<TouchEvent> touchEvents;

        input = game.getInput();
        touchEvents = input.getTouchEvents();
        input.getKeyEvents();

        for(TouchEvent touchEvent : touchEvents) {
            if(touchEvent.type == TouchEvent.TOUCH_UP) {
                if(touchInBounds(touchEvent, 0, 440, 40, 40)) {
                    game.setScreen(new MenuScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
            }
        }
    }

    /* Draw the scoreboard. */
    @Override
    public void draw(float deltaTime) {
        Graphics graphics;

        graphics = game.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.highscores, 40, 40);
        graphics.drawPixmap(Assets.arrowLeft, 0, 440);

        for(int i = 1; i <= 5; ++i)
            drawScore(i, Settings.highscores[i - 1], graphics);
    }

    /* Necessary overrides. */
    @Override
    public void resume() {

    }
    @Override
    public void pause() {

    }
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

    /* Draw number with top-left position at World coordinates (x, y), using graphics. */
    private void drawNumber(int number, int x, int y, Graphics graphics) {
        graphics.drawPixmap(Assets.numbers, 40 * number, 0, x, y, 40, 40);
    }

    /* Draw score where its position on the scoreboard is place, using graphics. */
    private void drawScore(int place, int score, Graphics graphics) {
        int y;

        y = 120 + (40 * place);

        drawNumber(place, 40, y, graphics);

        for(int i = 0; i < 4 && score > 0; ++i) {
            drawNumber(score % 10, 240 - (i * 40), y, graphics);
            score /= 10;
        }
    }
}
