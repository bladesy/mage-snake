package uk.ac.qub.dblades01.mrnom;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;

public class GameScreen extends Screen {
    private enum GameState {
        READY,
        RUNNING,
        PAUSED,
        GAME_OVER;
    }

    int score;
    GameState state;
    World world;

    /* game is the Game object that this Screen acts as a section of. */
    public GameScreen(Game game) {
        super(game);

        score = 0;
        state = GameState.READY;
        world = new World();
    }

    /* Update components. */
    @Override
    public void update(float deltaTime) {

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
