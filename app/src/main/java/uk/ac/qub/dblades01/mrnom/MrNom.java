package uk.ac.qub.dblades01.mrnom;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;

public class MrNom extends Game {
    /* Return the LoadingScreen as the first Screen that the Game should run. */
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
