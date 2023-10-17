package org.graphics;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;

public class WindowListener extends WindowAdapter {
    @Override
    public void windowResized(WindowEvent e) {
        int newWidth = Screen.getScreenWidth();
        int newHeight = Screen.getScreenHeight();

        if (newWidth < Renderer.MIN_SCREEN_WIDTH) {
            Renderer.setSize(Renderer.MIN_SCREEN_WIDTH, newHeight);
        }

        if (newHeight < Renderer.MIN_SCREEN_HEIGHT) {
            Renderer.setSize(newWidth, Renderer.MIN_SCREEN_HEIGHT);
        }
    }
}
