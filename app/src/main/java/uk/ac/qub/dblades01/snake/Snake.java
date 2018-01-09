package uk.ac.qub.dblades01.snake;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;

public class Snake extends Game {
    /* Return the LoadingScreen as the first Screen that the Game should run. */
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
