package uk.ac.qub.dblades01.mage.input;

import android.view.View.OnTouchListener;

import java.util.List;

import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;

/* Describes a class that provides polling access to the state of pointers on the touchscreen, along
with event-based access to the most recent touch events. As this interface extends OnTouchListener,
all TouchHandlers are listeners for touch events. */
public interface TouchHandler extends OnTouchListener {
    int getTouchX(int pointerId);
    int getTouchY(int pointerId);
    boolean isTouchDown(int pointerId);
    List<TouchEvent> getTouchEvents();
}
