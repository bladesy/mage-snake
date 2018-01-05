package uk.ac.qub.dblades01.mage.input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;
import uk.ac.qub.dblades01.mage.input.Pool.PoolObjectFactory;

/* Implementation of the TouchHandler interface, that supports only one pointer on the screen at any
time - this pointer being the first in contact with the touchscreen, the pointer of id 0. */
public class SingleTouchHandler implements TouchHandler {
    private int touchX, touchY;
    private boolean touchDown;
    private float scaleX, scaleY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>(), touchEvents = new ArrayList<>();

    /* view is the View to be listened to for touch events, while scaleX and scaleY are the
    respective scalings for the axis of the frame buffer in comparison to the actual screen. */
    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> touchEventFactory;

        /* Specify how new TouchEvent objects should be created by touchEventPool. */
        touchEventFactory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<>(100, touchEventFactory);

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        view.setOnTouchListener(this);
    }

    /* Called when view receives a touch event in the form of motionEvent - create a new TouchEvent
    from motionEvent, adding it to the touchEventsBuffer of touch events received since
    getTouchEvents() was called last. */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchEvent touchEvent;

        /* Prevents other accesses of touchEventPool and touchEventsBuffer from other threads. */
        synchronized(this) {
            /* Obtain a TouchEvent instance with which to mirror the MotionEvent instance. */
            touchEvent = touchEventPool.newObject();

            /* touchEvent's type member should reflect motionEvent's action - this action also being
            used to update the state of the pointer being pressed down or not. */
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchDown = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = TouchEvent.TOUCH_DRAG;
                    touchDown = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchDown = false;
                    break;
            }

            /* Convert the coordinates of the touch in terms of the actual screen resolution into
            the resolution of the frame buffer. */
            touchEvent.x = touchX = (int) (motionEvent.getX() * scaleX);
            touchEvent.y = touchY = (int) (motionEvent.getY() * scaleY);

            touchEventsBuffer.add(touchEvent);
        }

        return true;
    }

    /* Return the current x position of the pointer - pointerId being unused. */
    @Override
    public int getTouchX(int pointerId) {
        return touchX;
    }

    /* Return the current y position of the pointer - pointerId being unused. */
    @Override
    public int getTouchY(int pointerId) {
        return touchY;
    }

    /* Return whether or not the pointer is currently being pressed down on the touchscreen -
    pointerId causing a return of false for any pointerId beyond 0. */
    @Override
    public boolean isTouchDown(int pointerId) {
        if(pointerId == 0)
            return touchDown;
        else
            return false;
    }

    /* Get a list of the latest touch events represented by TouchEvent objects. */
    @Override
    public List<TouchEvent> getTouchEvents() {
        /* Prevent touchEventPool and touchEventsBuffer access by other threads. */
        synchronized(this) {
            /* Free old TouchEvent objects from use. */
            for(int i = 0; i < touchEvents.size(); ++i)
                touchEventPool.freeObject(touchEvents.get(i));

            /* Remove old TouchEvent objects, filling touchEvents with new TouchEvent objects. */
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            /* Remove the latest TouchEvent objects from touchEventsBuffer, so that it may gather
            newer TouchEvent objects. */
            touchEventsBuffer.clear();
        }

        return touchEvents;
    }
}
