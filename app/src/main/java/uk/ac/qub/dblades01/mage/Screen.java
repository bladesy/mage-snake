package uk.ac.qub.dblades01.mage;

/* Acts as a distinct section or screen of the overall game, consisting of its own components that
can be updated, then drawn. */
public abstract class Screen {
    protected final Game game;

    /* game is the Game object that this Screen acts as a section of. */
    public Screen(Game game) {
        this.game = game;
    }

    /* Update components. */
    public abstract void update(float deltaTime);
    /* Draw components to the frame buffer. */
    public abstract void draw(float deltaTime);

    /* Set up. */
    public abstract void resume();
    /* Clean up. */
    public abstract void pause();

    /* Remove this Screen from use. */
    public abstract void dispose();
}
