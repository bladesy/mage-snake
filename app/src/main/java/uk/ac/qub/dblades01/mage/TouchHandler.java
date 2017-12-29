package uk.ac.qub.dblades01.mage;

import android.view.View.OnTouchListener;

import java.util.List;

import uk.ac.qub.dblades01.mage.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
    int getTouchX(int pointerId);
    int getTouchY(int pointerId);
    boolean isTouchDown(int pointerId);
    List<TouchEvent> getTouchEvents();
}
