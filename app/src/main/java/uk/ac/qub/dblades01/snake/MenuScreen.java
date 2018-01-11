package uk.ac.qub.dblades01.snake;

import java.util.List;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;

public class MenuScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public MenuScreen(Game game) {
        super(game);
    }

    /* Update components. */
    @Override
    public void update(float deltaTime) {
        Input input;
        List<TouchEvent> touchEvents;

        input = game.getInput();
        touchEvents = input.getTouchEvents();
        input.getKeyEvents();

        for(TouchEvent touchEvent : touchEvents) {
            if(touchEvent.type == TouchEvent.TOUCH_UP) {
                if(touchInBounds(touchEvent, 40, 200, 240, 80)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new GameScreen(game));
                }
                else if(touchInBounds(touchEvent, 40, 280, 240, 80)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new ScoreScreen(game));
                }
                else if(touchInBounds(touchEvent, 40, 360, 240, 80)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new HelpScreen(game));
                }
            }
        }
    }

    /* Draw components to the frame buffer. */
    @Override
    public void draw(float deltaTime) {
        Graphics graphics;

        graphics = game.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.title, 40, 40);
        graphics.drawPixmap(Assets.play, 40, 200);
        graphics.drawPixmap(Assets.highscores, 40, 280);
        graphics.drawPixmap(Assets.help, 40, 360);
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
}
