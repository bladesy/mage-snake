package uk.ac.qub.dblades01.mrnom.screens;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.audio.Audio;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.graphics.Graphics.PixmapFormat;
import uk.ac.qub.dblades01.mrnom.Assets;
import uk.ac.qub.dblades01.mrnom.Settings;

/* Loads all of the game assets before any other screen is ran - therefore, it must be the start
screen. */
public class LoadingScreen extends Screen {
    /* game is the Game object that this Screen acts as a section of. */
    public LoadingScreen(Game game) {
        super(game);
    }

    /* This update should load all of the assets into Assets, only being called once before the
    MenuScreen is set. */
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
        Assets.mute = graphics.newPixmap("images/mute.png", PixmapFormat.RGB565);
        Assets.unmute = graphics.newPixmap("images/unmute.png", PixmapFormat.RGB565);
        Assets.ruleHeading = graphics.newPixmap("images/ruleHeading.png", PixmapFormat.RGB565);
        Assets.rule1 = graphics.newPixmap("images/rule1.png", PixmapFormat.RGB565);
        Assets.rule2 = graphics.newPixmap("images/rule2.png", PixmapFormat.RGB565);
        Assets.rule3 = graphics.newPixmap("images/rule3.png", PixmapFormat.RGB565);
        Assets.arrowLeft = graphics.newPixmap("images/arrowLeft.png", PixmapFormat.RGB565);
        Assets.arrowRight = graphics.newPixmap("images/arrowRight.png", PixmapFormat.RGB565);
        Assets.headLeft = graphics.newPixmap("images/headLeft.png", PixmapFormat.RGB565);
        Assets.headRight = graphics.newPixmap("images/headRight.png", PixmapFormat.RGB565);
        Assets.headUp = graphics.newPixmap("images/headUp.png", PixmapFormat.RGB565);
        Assets.headDown = graphics.newPixmap("images/headDown.png", PixmapFormat.RGB565);
        Assets.tail = graphics.newPixmap("images/tail.png", PixmapFormat.RGB565);
        Assets.ink1 = graphics.newPixmap("images/ink1.png", PixmapFormat.RGB565);
        Assets.ink2 = graphics.newPixmap("images/ink2.png", PixmapFormat.RGB565);
        Assets.ink3 = graphics.newPixmap("images/ink3.png", PixmapFormat.RGB565);
        Assets.numbers = graphics.newPixmap("images/numbers.png", PixmapFormat.RGB565);
        Assets.ready = graphics.newPixmap("images/ready.png", PixmapFormat.RGB565);
        Assets.pause = graphics.newPixmap("images/pause.png", PixmapFormat.RGB565);
        Assets.resume = graphics.newPixmap("images/resume.png", PixmapFormat.RGB565);
        Assets.quit = graphics.newPixmap("images/quit.png", PixmapFormat.RGB565);
        Assets.gameOver = graphics.newPixmap("images/gameOver.png", PixmapFormat.RGB565);
        Assets.cancel = graphics.newPixmap("images/cancel.png", PixmapFormat.RGB565);

        /* Load Sound assets. */
        Assets.click = audio.newSound("audio/click.wav");
        Assets.bite = audio.newSound("audio/bite.wav");
        Assets.splash = audio.newSound("audio/splash.wav");

        /* Load Settings. */
        Settings.load(game.getFileIO());

        /* Move on to the next screen. */
        game.setScreen(new MenuScreen(game));
    }

    /* Necessary overrides. */
    @Override
    public void draw(float deltaTime) {
    }
    @Override
    public void resume() {

    }
    @Override
    public void pause() {

    }
    @Override
    public void dispose() {

    }
}
