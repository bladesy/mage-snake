package uk.ac.qub.dblades01.mage;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.Input.TouchEvent;
import uk.ac.qub.dblades01.mage.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler {
    private int touchX, touchY;
    private boolean touchDown;
    private float scaleX, scaleY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>(), touchEvents = new ArrayList<>();

    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> touchEventFactory;

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchEvent touchEvent;

        synchronized(this) {
            touchEvent = touchEventPool.newObject();

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

            touchEvent.x = touchX = (int) (motionEvent.getX() * scaleX);
            touchEvent.y = touchY = (int) (motionEvent.getY() * scaleY);

            touchEventsBuffer.add(touchEvent);
        }

        return true;
    }

    @Override
    public int getTouchX(int pointerId) {
        return touchX;
    }

    @Override
    public int getTouchY(int pointerId) {
        return touchY;
    }

    @Override
    public boolean isTouchDown(int pointerId) {
        if(pointerId == 0)
            return touchDown;
        else
            return false;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized(this) {
            for(int i = 0; i < touchEvents.size(); ++i)
                touchEventPool.freeObject(touchEvents.get(i));

            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
        }

        return touchEvents;
    }
}
