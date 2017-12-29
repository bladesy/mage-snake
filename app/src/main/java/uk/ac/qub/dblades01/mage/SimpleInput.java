package uk.ac.qub.dblades01.mage;

import android.content.Context;
import android.view.View;

import java.util.List;

public class SimpleInput implements Input {
    AccelerometerHandler accelerometerHandler;
    KeyboardHandler keyboardHandler;
    TouchHandler touchHandler;

    public SimpleInput(Context context, View view, float scaleX, float scaleY) {
        accelerometerHandler = new AccelerometerHandler(context);
        keyboardHandler = new KeyboardHandler(view);
        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        return keyboardHandler.isKeyDown(keyCode);
    }

    @Override
    public int getTouchX(int pointerId) {
        return touchHandler.getTouchX(pointerId);
    }

    @Override
    public int getTouchY(int pointerId) {
        return touchHandler.getTouchY(pointerId);
    }

    @Override
    public boolean isTouchDown(int pointerId) {
        return touchHandler.isTouchDown(pointerId);
    }

    @Override
    public float getAccelX() {
        return accelerometerHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelerometerHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelerometerHandler.getAccelZ();
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyboardHandler.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}
