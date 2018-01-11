package uk.ac.qub.dblades01.snake;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.audio.Audio;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.graphics.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public LoadingScreen(Game game) {
        super(game);
    }

    /* Update components. */
    @Override
    public void update(float deltaTime) {
        Graphics graphics;
        Audio audio;

        graphics = game.getGraphics();
        audio = game.getAudio();

        /* Load Pixmap assets. */
        Assets.background = graphics.newPixmap("images/background.png", PixmapFormat.RGB565);
        Assets.title = graphics.newPixmap("images/title.png", PixmapFormat.RGB565);
        Assets.play = graphics.newPixmap("images/play.png", PixmapFormat.RGB565);
        Assets.highscores = graphics.newPixmap("images/highscores.png", PixmapFormat.RGB565);
        Assets.help = graphics.newPixmap("images/help.png", PixmapFormat.RGB565);

        /* Load Sound assets. */
        Assets.click = audio.newSound("audio/click.wav");

        /* Move on to the next screen. */
        game.setScreen(new MenuScreen(game));
    }

    /* Draw components to the frame buffer. */
    @Override
    public void draw(float deltaTime) {
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
