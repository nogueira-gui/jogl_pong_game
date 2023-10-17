package org.inputs;

import com.jogamp.newt.event.KeyEvent;
import org.engine.GameLoop;
import org.graphics.Renderer;
import org.world.World;

public class RendererInputControl extends KeyboardListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if (getKey(KeyEvent.VK_CONTROL)) {
            if (getKey(KeyEvent.VK_R)) {
                World.resetWorld(true);
                GameLoop.resumeGame();
            }

            if (getKey(KeyEvent.VK_ESCAPE) || getKey(KeyEvent.VK_C)) {
                close();
            }

            return;
        }

        if (getKey(KeyEvent.VK_SPACE)) {
            World.throwBall();
        }

        if (getKey(KeyEvent.VK_F11)) {
            toggleFullscreen();
        }

        if (getKey(KeyEvent.VK_ESCAPE) || GameLoop.isPaused() && getKey(KeyEvent.VK_SPACE)) {
            if (World.isGameLost() || World.isGameWon()) {
                return;
            }

            if (World.ignoreNextThrow) {
                World.ignoreNextThrow = false;
            }

            if (GameLoop.isPaused()) {
                GameLoop.resumeGame();
            } else {
                GameLoop.pauseGame();
            }
        }
    }

    private void close() {
        Renderer.exit();
    }

    private void toggleFullscreen() {
        Renderer.toggleFullscreen();
    }
}
