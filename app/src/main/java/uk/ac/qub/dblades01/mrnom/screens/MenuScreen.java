package uk.ac.qub.dblades01.mrnom.screens;

import java.util.List;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;
import uk.ac.qub.dblades01.mrnom.Assets;
import uk.ac.qub.dblades01.mrnom.Settings;

/* The screen from which other screens are accessed. */
public class MenuScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public MenuScreen(Game game) {
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
                if(touchInBounds(touchEvent, 40, 200, 240, 80)) {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                else if(touchInBounds(touchEvent, 40, 280, 240, 80)) {
                    game.setScreen(new ScoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                else if(touchInBounds(touchEvent, 40, 360, 240, 80)) {
                    game.setScreen(new HelpScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                else if(touchInBounds(touchEvent, 0, 440, 40, 480)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
            }
        }
    }

    /* Draw menu buttons. */
    @Override
    public void draw(float deltaTime) {
        Graphics graphics;

        graphics = game.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.title, 40, 40);
        graphics.drawPixmap(Assets.play, 40, 200);
        graphics.drawPixmap(Assets.highscores, 40, 280);
        graphics.drawPixmap(Assets.help, 40, 360);

        if(Settings.soundEnabled)
            graphics.drawPixmap(Assets.mute, 0, 440);
        else
            graphics.drawPixmap(Assets.unmute, 0, 440);
    }

    /* Set up. */
    @Override
    public void resume() {

    }

    /* Save the current settings and scores. */
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
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
