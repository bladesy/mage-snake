package uk.ac.qub.dblades01.mage;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.Input.TouchEvent;
import uk.ac.qub.dblades01.mage.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler {
    private static final int MAX_POINTER_INDEXES = 10;
    private int[] touchId = new int[MAX_POINTER_INDEXES],
            touchX = new int[MAX_POINTER_INDEXES],
            touchY = new int[MAX_POINTER_INDEXES];
    private boolean[] touchDown = new boolean[MAX_POINTER_INDEXES];
    private float scaleX, scaleY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>(), touchEvents = new ArrayList<>();

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
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
        int motionAction, motionPointerIndex, motionPointerCount;

        motionAction = motionEvent.getActionMasked();
        motionPointerIndex = motionEvent.getActionIndex();
        motionPointerCount = motionEvent.getPointerCount();

        synchronized(this) {
            for(int i = 0; i < MAX_POINTER_INDEXES; ++i) {
                int motionPointerId;
                TouchEvent touchEvent;

                if(i >= motionPointerCount) {
                    touchId[i] = -1;
                    touchDown[i] = false;
                    continue;
                }

                if(i != motionPointerIndex && motionAction != MotionEvent.ACTION_MOVE)
                    continue;

                motionPointerId = motionEvent.getPointerId(i);
                touchEvent = touchEventPool.newObject();

                switch(motionAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchId[i] = motionPointerId;
                        touchDown[i] = true;
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchId[i] = motionPointerId;
                        touchDown[i] = true;
                        touchEvent.type = TouchEvent.TOUCH_DRAG;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        touchId[i] = -1;
                        touchDown[i] = false;
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        break;
                }

                touchEvent.pointerId = motionPointerId;
                touchEvent.x = touchX[i] = (int) (motionEvent.getX(i) * scaleX);
                touchEvent.y = touchY[i] = (int) (motionEvent.getY(i) * scaleY);

                touchEventsBuffer.add(touchEvent);
            }
        }

        return true;
    }

    @Override
    public int getTouchX(int pointerId) {
        int pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return 0;
        else
            return touchX[pointerIndex];
    }

    @Override
    public int getTouchY(int pointerId) {
        int pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return 0;
        else
            return touchY[pointerIndex];
    }

    @Override
    public boolean isTouchDown(int pointerId) {
        int pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return false;
        else
            return touchDown[pointerIndex];
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

    private int getPointerIndex(int pointerId) {
        for(int i = 0; i < MAX_POINTER_INDEXES; ++i)
            if(touchId[i] == pointerId)
                return i;

        return -1;
    }
}
