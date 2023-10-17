package org.engine;

import org.graphics.Renderer;
import org.world.World;

public class GameLoop {
    private final static int NANOSECOND = 1000000000;
    private final static int MICROSECOND = 1000000;

    public static boolean running = false;
    private static boolean paused = false;

    private final static int targetFPS = 60;
    private final static int targetTime = NANOSECOND / targetFPS;
    private static int currentFPS = 0;

    private static int updates = 0;
    private static final int MAX_UPDATES = 5;

    private static long lastUpdateTime = 0;

    public static void start() {
        Thread thread = new Thread(() -> {
            running = true;
            lastUpdateTime = System.nanoTime();

            int fps = 0;
            long lastFpsCheck = System.nanoTime();

            while (running) {
                if (paused) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    lastUpdateTime = System.nanoTime();

                    continue;
                }

                long currentTime = System.nanoTime();

                updates = 0;

                while(currentTime - lastUpdateTime > targetTime) {
                    World.update();

                    lastUpdateTime += targetTime;
                    updates++;

                    if (updates > MAX_UPDATES) {
                        break;
                    }
                }

                Renderer.render();

                fps++;
                if (System.nanoTime() >= lastFpsCheck + NANOSECOND) {
                    currentFPS = fps;
                    fps = 0;
                    lastFpsCheck = System.nanoTime();
                }

                long timeTaken = System.nanoTime() - currentTime;

                if (targetTime > timeTaken) {
                    try {
                        Thread.sleep((targetTime - timeTaken) / MICROSECOND);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.setName("Game loop");
        thread.start();
    }

    public static float updateDelta() {
        return 1.0f / NANOSECOND * targetTime;
    }

    public static int getFPS() {
        return currentFPS;
    }

    public static void pauseGame() {
        paused = true;
    }

    public static void pauseGame(int millis) {
        paused = false;

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        paused = true;
    }

    public static void resumeGame() {
        paused = false;
    }

    public static boolean isPaused() {
        return paused;
    }
}