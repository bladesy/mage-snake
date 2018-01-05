package uk.ac.qub.dblades01.mage.input;

import android.view.View;
import android.view.View.OnKeyListener;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.input.Input.KeyEvent;
import uk.ac.qub.dblades01.mage.input.Pool.PoolObjectFactory;

/* Provides polling access to the state of the keys, along with access to a list of the most recent
key events. */
public class KeyboardHandler implements OnKeyListener {
    private boolean[] keysDown = new boolean[128];
    private Pool<KeyEvent> keyEventPool;
    private List<KeyEvent> keyEventsBuffer = new ArrayList<>(), keyEvents = new ArrayList<>();

    /* view is the View to be listened to for key events. */
    public KeyboardHandler(View view) {
        PoolObjectFactory<KeyEvent> keyEventFactory;

        /* Create an anonymous class based on the PoolObjectFactory, to create KeyEvent objects. */
        keyEventFactory = new PoolObjectFactory<KeyEvent>() {
            @Override
            public KeyEvent createObject() {
                return new KeyEvent();
            }
        };
        keyEventPool = new Pool<>(100, keyEventFactory);

        view.setOnKeyListener(this);
        /* If the view is not focused, the key events will not go to view, and thus will not be
        handled. */
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /* Called when view receives an android key event - create a new custom key event from it,
    adding it to the keyEventsBuffer of key events received since getKeyEvents() was called last. */
    @Override
    public boolean onKey(View view, int keyCode, android.view.KeyEvent viewKeyEvent) {
        KeyEvent keyEvent;

        /* Ignore ACTION_MULTIPLE events, as they are not required to be handled. */
        if(viewKeyEvent.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        /* Synchronisation ensures that the UI thread that calls this method will be the only thread
        accessing the keyEventPool and keyEventsBuffer for this block. */
        synchronized(this) {
            /* As there are many key events that will be raised, the keyEventPool will try to
            recycle instances of previously used custom keyEvent objects. */
            keyEvent = keyEventPool.newObject();

            /* Set the custom key event action to reflect that of the raised android key event, and
            update the key's state for polling accordingly. */
            switch(viewKeyEvent.getAction()) {
                case android.view.KeyEvent.ACTION_DOWN:
                    if(keyEvent.keyCode >= 0 && keyEvent.keyCode <= 127) {
                        keyEvent.type = KeyEvent.KEY_DOWN;
                        keysDown[keyCode] = true;
                    }
                    break;
                case android.view.KeyEvent.ACTION_UP:
                    if(keyEvent.keyCode >= 0 && keyEvent.keyCode <= 127) {
                        keyEvent.type = KeyEvent.KEY_UP;
                        keysDown[keyCode] = false;
                    }
                    break;
            }

            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = viewKeyEvent.getUnicodeChar();

            keyEventsBuffer.add(keyEvent);
        }

        return false;
    }

    /* Return whether or not the key that corresponds to the passed in unicode keyCode is currently
    being pressed down. */
    public boolean isKeyDown(int keyCode) {
        /* Return false if the keycode is invalid */
        if(keyCode < 0 && keyCode > 127)
            return false;
        return keysDown[keyCode];
    }

    /* Get a list of the latest key events represented by custom KeyEvent objects. */
    public List<KeyEvent> getKeyEvents() {
        /* Prevent keyEventPool and keyEventsBuffer access by other threads. */
        synchronized(this) {
            /* Free old custom KeyEvent objects from use. */
            for(int i = 0; i < keyEvents.size(); ++i)
                keyEventPool.freeObject(keyEvents.get(i));

            /* Remove old custom KeyEvent objects, filling keyEvents with new custom KeyEvent
            objects. */
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            /* Remove the latest custom KeyEvent objects from keyEventsBuffer, so that it may gather
            newer custom KeyEvent objects. */
            keyEventsBuffer.clear();
        }

        return keyEvents;
    }
}
