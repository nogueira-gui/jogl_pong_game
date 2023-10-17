package org.inputs;

import com.jogamp.newt.event.KeyEvent;
import org.engine.GameLoop;
import org.graphics.EventListener;
import org.graphics.Renderer;
import org.world.World;

public class RendererInputControl extends KeyboardListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if (getKey(KeyEvent.VK_CONTROL)) {
            if (getKey(KeyEvent.VK_SHIFT)){
                if (getKey(KeyEvent.VK_EQUALS)) {
                    World.increaseBlockStep();

                    if (GameLoop.isPaused()) {
                        GameLoop.resumeGame();

                        GameLoop.pauseGame(20);
                    }
                }

                if (getKey(KeyEvent.VK_MINUS)) {
                    World.decreaseBlockStep();

                    if (GameLoop.isPaused()) {
                        GameLoop.resumeGame();

                        GameLoop.pauseGame(20);
                    }
                }
            }

            if (getKey(KeyEvent.VK_R)) {
                World.resetWorld();
                GameLoop.resumeGame();
            }

            if (getKey(KeyEvent.VK_S)) {
                EventListener.toggleVSync();
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
            if (World.isGameLost()) {
                return;
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
