package org.inputs;

import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private static final boolean[] keys = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        if ((InputEvent.AUTOREPEAT_MASK & e.getModifiers()) == 0) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ((InputEvent.AUTOREPEAT_MASK & e.getModifiers()) == 0) {
            keys[e.getKeyCode()] = false;
        }
    }

    public static boolean getKey(int code) {
        return keys[code];
    }
}
