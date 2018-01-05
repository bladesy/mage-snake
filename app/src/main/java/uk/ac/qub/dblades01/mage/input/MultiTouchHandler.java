package uk.ac.qub.dblades01.mage.input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.input.Input.TouchEvent;
import uk.ac.qub.dblades01.mage.input.Pool.PoolObjectFactory;

/* Implementation of the TouchHandler interface, that supports multiple pointers on the screen at
any time. */
public class MultiTouchHandler implements TouchHandler {
    private static final int MAX_POINTER_INDEXES = 10;
    private int[] touchId = new int[MAX_POINTER_INDEXES],
            touchX = new int[MAX_POINTER_INDEXES],
            touchY = new int[MAX_POINTER_INDEXES];
    private boolean[] touchDown = new boolean[MAX_POINTER_INDEXES];
    private float scaleX, scaleY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>(), touchEvents = new ArrayList<>();

    /* view is the View to be listened to for touch events, while scaleX and scaleY are the
    respective scalings for the axis of the frame buffer in comparison to the actual screen. */
    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> touchEventFactory;

        /* Specify how touchEventPool should create new TouchEvent objects. */
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

    /* Called when view receives a touch event in the form of motionEvent - creates new TouchEvents
    from motionEvent, and adds them to the touchEventsBuffer of touch events received since
    getTouchEvents() was called last. */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int motionAction, motionPointerIndex, motionPointerCount;

        /* When dealing with multiple pointers, the action of the MotionEvent will contain both the
        involved pointer index (unpredictable index used by android that differs from the pointer
        id) and the action type itself. */
        motionAction = motionEvent.getActionMasked();
        motionPointerIndex = motionEvent.getActionIndex();
        motionPointerCount = motionEvent.getPointerCount();

        /* Prevent accesses to touchEventPool and touchEventsBuffer, and the touch arrays, by other
        threads. */
        synchronized(this) {
            /* Consider every possible pointer index in turn - this means that i represents the
            pointer index of this handler, and is also used to index the polling arrays. */
            for(int i = 0; i < MAX_POINTER_INDEXES; ++i) {
                int motionPointerId;
                TouchEvent touchEvent;

                /* Pointers that do not appear in motionEvent are not on the touchscreen, and so a
                TouchEvent should not be created for them - but the state for polling access does
                need to be updated. */
                if(i >= motionPointerCount) {
                    /* The current pointer index has no pointer id associated with it, so -1 will be
                    used to signal this. */
                    touchId[i] = -1;
                    touchDown[i] = false;
                    continue;
                }

                /* Only proceed to create a new TouchEvent when the current index did not send
                motionEvent if the action is ACTION_MOVE, as this means that multiple pointers may
                have moved. */
                if(i != motionPointerIndex && motionAction != MotionEvent.ACTION_MOVE)
                    continue;

                /* Get the pointer id for the current pointer index i. */
                motionPointerId = motionEvent.getPointerId(i);
                /* Create a new TouchEvent for the current pointer index i. */
                touchEvent = touchEventPool.newObject();

                /* Update the state for the pointer index i, based on the action of its pointer. */
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

                /* Convert the coordinates of the touch in terms of the actual screen resolution
                into the resolution of the frame buffer. */
                touchEvent.x = touchX[i] = (int) (motionEvent.getX(i) * scaleX);
                touchEvent.y = touchY[i] = (int) (motionEvent.getY(i) * scaleY);
                /* Store the pointer id associated with the pointer index i in the new TouchEvent. */
                touchEvent.pointerId = motionPointerId;

                touchEventsBuffer.add(touchEvent);
            }
        }

        return true;
    }

    /* Return the current x position of the pointer represented by pointerId. */
    @Override
    public int getTouchX(int pointerId) {
        int pointerIndex;

        pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return 0;
        else
            return touchX[pointerIndex];
    }

    /* Return the current y position of the pointer represented by pointerId. */
    @Override
    public int getTouchY(int pointerId) {
        int pointerIndex;

        pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return 0;
        else
            return touchY[pointerIndex];
    }

    /* Return whether or not the pointer represented by pointerId is currently being pressed down on
    the touchscreen. */
    @Override
    public boolean isTouchDown(int pointerId) {
        int pointerIndex;

        pointerIndex = getPointerIndex(pointerId);

        if(pointerIndex < 0 || pointerIndex >= MAX_POINTER_INDEXES)
            return false;
        else
            return touchDown[pointerIndex];
    }

    /* Return a list of the latest touch events represented by TouchEvent objects. */
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

    /* Return the pointer index that corresponds to pointerId. */
    private int getPointerIndex(int pointerId) {
        for(int i = 0; i < MAX_POINTER_INDEXES; ++i)
            if(touchId[i] == pointerId)
                return i;

        /* Signal that there is no pointer index that corresponds to pointerId. */
        return -1;
    }
}
