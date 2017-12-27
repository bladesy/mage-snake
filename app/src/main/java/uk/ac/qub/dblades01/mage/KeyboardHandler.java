package uk.ac.qub.dblades01.mage;

import android.view.View;
import android.view.View.OnKeyListener;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.dblades01.mage.Input.KeyEvent;
import uk.ac.qub.dblades01.mage.Pool.PoolObjectFactory;

public class KeyboardHandler implements OnKeyListener {
    private boolean[] keysDown = new boolean[128];
    private Pool<KeyEvent> keyEventPool;
    private List<KeyEvent> keyEventsBuffer = new ArrayList<>(), keyEvents = new ArrayList<>();

    public KeyboardHandler(View view) {
        PoolObjectFactory<KeyEvent> keyEventFactory;

        keyEventFactory = new PoolObjectFactory<KeyEvent>() {
            @Override
            public KeyEvent createObject() {
                return new KeyEvent();
            }
        };
        keyEventPool = new Pool<>(100, keyEventFactory);

        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public boolean onKey(View view, int keyCode, android.view.KeyEvent viewKeyEvent) {
        KeyEvent keyEvent;

        if(viewKeyEvent.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized(this) {
            keyEvent = keyEventPool.newObject();

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

    public boolean isKeyDown(int keyCode) {
        if(keyCode < 0 && keyCode > 127)
            return false;
        return keysDown[keyCode];
    }

    public List<KeyEvent> getKeyEvents() {
        synchronized(this) {
            for(int i = 0; i < keyEvents.size(); ++i)
                keyEventPool.freeObject(keyEvents.get(i));

            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();

            return keyEvents;
        }
    }
}
