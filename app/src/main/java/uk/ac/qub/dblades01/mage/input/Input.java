package uk.ac.qub.dblades01.mage.input;

import java.util.List;

/* Describes a class that provides polling access to the keyboard, touchscreen, and accelerometer of
the device - along with event-based access to the most recent touchscreen and keyboard events. */
public interface Input {
    /* Describes an interaction with the keyboard. */
    class KeyEvent {
        public static final int KEY_DOWN = 0, KEY_UP = 1;
        public int type, keyCode, keyChar;
    }

    /* Describes an interaction with the touchscreen. */
    class TouchEvent {
        public static final int TOUCH_DOWN = 0, TOUCH_DRAG = 1, TOUCH_UP = 2;
        public int type, x, y, pointerId;
    }

    /* Polling keyboard access. */
    boolean isKeyDown(int keyCode);

    /* Polling touchscreen access. */
    int getTouchX(int pointerId);
    int getTouchY(int pointerId);
    boolean isTouchDown(int pointerId);

    /* Polling accelerometer access. */
    float getAccelX();
    float getAccelY();
    float getAccelZ();

    /* Event-based access through event lists. */
    List<KeyEvent> getKeyEvents();
    List<TouchEvent> getTouchEvents();
}
