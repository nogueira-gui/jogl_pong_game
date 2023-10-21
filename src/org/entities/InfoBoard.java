package org.entities;

import org.engine.GameLoop;
import org.graphics.Color;
import org.graphics.Graphics;
import org.graphics.Screen;
import org.objects.Rectangle;
import org.textures.TextureManager;

public class InfoBoard extends Rectangle {
    public static final float BOARD_WIDTH = Screen.getScreenWidth();
    public static final float BOARD_HEIGHT = (float) Math.floor(Screen.getHalfScreenHeight() * 0.15);

    public static final float BOARD_X = Screen.getHalfScreenWidth() - (BOARD_WIDTH / 2);
    public static final float BOARD_Y = Screen.getHalfScreenHeight() - (BOARD_HEIGHT / 2);

    public InfoBoard() {
        super(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT, new Color(255, 255, 255, 1));
    }

    @Override
    public void update() {
    }

    public void render(Player player) {
        Graphics.fillRect(x, y, width, height, color);

        int letterSize = 42;

        // Player score
        Graphics.writeText( -BOARD_WIDTH / 2, BOARD_Y, letterSize, letterSize, String.valueOf(player.getScore()));

        // FPS Counter
        Graphics.writeText( (BOARD_WIDTH / 2) - letterSize * 2, BOARD_Y, letterSize, letterSize, String.valueOf(GameLoop.getFPS()));

        int TOTAL_LIVES = Player.TOTAL_LIVES;
        int REMAINING_LIVES = player.getRemainingLives();

        for (int i = 1; i <= REMAINING_LIVES; i++) {
            float TEXTURE_WIDTH = TextureManager.TEXTURE_WIDTH * 0.5f;

            float x = (i - 1) * TEXTURE_WIDTH - ((TEXTURE_WIDTH * TOTAL_LIVES) / 2);

            Graphics.drawImage(x, BOARD_Y, 0.5f, "heart");
        }
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }
}
