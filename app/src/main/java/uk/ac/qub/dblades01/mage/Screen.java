package uk.ac.qub.dblades01.mage;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);
    public abstract void draw(float deltaTime);

    public abstract void resume();
    public abstract void pause();

    public abstract void dispose();
}
