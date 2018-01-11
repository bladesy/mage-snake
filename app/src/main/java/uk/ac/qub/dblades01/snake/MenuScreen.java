package uk.ac.qub.dblades01.snake;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;

public class MenuScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public MenuScreen(Game game) {
        super(game);
    }

    /* Update components. */
    @Override
    public void update(float deltaTime) {

    }

    /* Draw components to the frame buffer. */
    @Override
    public void draw(float deltaTime) {
        Graphics graphics;

        graphics = game.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
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
}
