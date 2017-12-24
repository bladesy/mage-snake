package uk.ac.qub.dblades01.mage;

import java.util.List;

public interface Input {
    class KeyEvent {
        public static final int KEY_DOWN = 0, KEY_UP = 1;
        public int type, keyCode, keyChar;
    }

    class TouchEvent {
        public static final int TOUCH_DOWN = 0, TOUCH_UP = 1, TOUCH_DRAG = 2;
        public int type, x, y, pointer;
    }

    boolean isKeyDown(int keyCode);

    int getTouchX(int pointer);
    int getTouchY(int pointer);
    boolean isTouchDown(int pointer);

    float getAccelX();
    float getAccelY();
    float getAccelZ();

    List<KeyEvent> getKeyEvents();
    List<TouchEvent> getTouchEvents();
}
