package uk.ac.qub.dblades01.mrnom;

import java.util.List;

import uk.ac.qub.dblades01.mage.Game;
import uk.ac.qub.dblades01.mage.Screen;
import uk.ac.qub.dblades01.mage.graphics.Graphics;
import uk.ac.qub.dblades01.mage.input.Input;
import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;

public class HelpScreen extends Screen {
    private int currentRule;

    /* game is the Game object that this Screen acts as a section of. */
    public HelpScreen(Game game) {
        super(game);

        currentRule = 0;
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
                if(touchInBounds(touchEvent, 0, 440, 40, 40)) {
                    currentRule = modulo(currentRule - 1, 4);
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                if(touchInBounds(touchEvent, 280, 440, 40, 40)) {
                    currentRule = modulo(currentRule + 1, 4);
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
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
        graphics.drawPixmap(Assets.ruleHeading, 40, 40);
        graphics.drawPixmap(Assets.arrowLeft, 0, 440);
        graphics.drawPixmap(Assets.arrowRight, 280, 440);

        switch(currentRule) {
            case 0:
                drawRule1(graphics);
                break;
            case 1:
                drawRule2(graphics);
                break;
            case 2:
                drawRule3(graphics);
                break;
            case 3:
                game.setScreen(new MenuScreen(game));
                break;
        }
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

    private int modulo(int expression, int modulus) {
        return ((expression % modulus) + modulus) % modulus;
    }

    private void drawRule1(Graphics graphics) {
        graphics.drawPixmap(Assets.rule1, 40, 320);
        graphics.drawPixmap(Assets.ink1, 80, 120);
        graphics.drawPixmap(Assets.headLeft, 120, 120);
        graphics.drawPixmap(Assets.tail, 160, 120);
        graphics.drawPixmap(Assets.tail, 200, 120);
        graphics.drawPixmap(Assets.tail, 200, 160);
        graphics.drawPixmap(Assets.tail, 200, 200);
        graphics.drawPixmap(Assets.tail, 160, 200);
        graphics.drawPixmap(Assets.tail, 120, 200);
        graphics.drawPixmap(Assets.tail, 120, 240);
    }

    private void drawRule2(Graphics graphics) {
        graphics.drawPixmap(Assets.rule2, 40, 320);
        graphics.drawPixmap(Assets.headDown, 120, 200);
        graphics.drawPixmap(Assets.tail, 120, 160);
        graphics.drawPixmap(Assets.tail, 120, 120);
        graphics.drawPixmap(Assets.tail, 160, 120);
        graphics.drawPixmap(Assets.tail, 200, 120);
        graphics.drawPixmap(Assets.tail, 200, 160);
        graphics.drawPixmap(Assets.tail, 200, 200);
        graphics.drawPixmap(Assets.tail, 200, 240);
        graphics.drawPixmap(Assets.tail, 160, 240);
        graphics.drawPixmap(Assets.tail, 120, 240);
        graphics.drawPixmap(Assets.tail, 80, 240);
        graphics.drawPixmap(Assets.tail, 40, 240);
    }

    private void drawRule3(Graphics graphics) {
        graphics.drawPixmap(Assets.rule3, 40, 320);
        graphics.drawPixmap(Assets.tail, 40, 160);
        graphics.drawPixmap(Assets.tail, 80, 160);
        graphics.drawPixmap(Assets.tail, 80, 200);
        graphics.drawPixmap(Assets.tail, 120, 200);
        graphics.drawPixmap(Assets.tail, 160, 200);
        graphics.drawPixmap(Assets.tail, 200, 200);
        graphics.drawPixmap(Assets.tail, 200, 160);
        graphics.drawPixmap(Assets.headRight, 240, 160);
    }
}
