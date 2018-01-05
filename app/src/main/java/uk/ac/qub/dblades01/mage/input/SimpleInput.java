package uk.ac.qub.dblades01.mage.input;

import android.content.Context;
import android.view.View;

import java.util.List;

/* A simple implementation of the Input interface */
public class SimpleInput implements Input {
    AccelerometerHandler accelerometerHandler;
    KeyboardHandler keyboardHandler;
    TouchHandler touchHandler;

    /* context provides access to the device sensors, view is the View to be listened to for touch
    and key events, while scaleX and scaleY are the respective scalings for the axis of the frame
    buffer in comparison to the actual screen. */
    public SimpleInput(Context context, View view, float scaleX, float scaleY) {
        accelerometerHandler = new AccelerometerHandler(context);
        keyboardHandler = new KeyboardHandler(view);
        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    /* Return whether or not the key represented by the passed in unicode keyCode is being pressed
    down. */
    @Override
    public boolean isKeyDown(int keyCode) {
        return keyboardHandler.isKeyDown(keyCode);
    }

    /* Return the current x position of the pointer represented by pointerId. */
    @Override
    public int getTouchX(int pointerId) {
        return touchHandler.getTouchX(pointerId);
    }

    /* Return the current y position of the pointer represented by pointerId. */
    @Override
    public int getTouchY(int pointerId) {
        return touchHandler.getTouchY(pointerId);
    }

    /* Return whether or not the pointer represented by pointerId is currently being pressed down on
    the touchscreen. */
    @Override
    public boolean isTouchDown(int pointerId) {
        return touchHandler.isTouchDown(pointerId);
    }

    /* Return the x acceleration of the device. */
    @Override
    public float getAccelX() {
        return accelerometerHandler.getAccelX();
    }

    /* Return the y acceleration of the device. */
    @Override
    public float getAccelY() {
        return accelerometerHandler.getAccelY();
    }

    /* Return the z acceleration of the device. */
    @Override
    public float getAccelZ() {
        return accelerometerHandler.getAccelZ();
    }

    /* Return a KeyEvent list of the most recent key events. */
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyboardHandler.getKeyEvents();
    }

    /* Return a TouchEvent list of the most recent touch events. */
    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}
