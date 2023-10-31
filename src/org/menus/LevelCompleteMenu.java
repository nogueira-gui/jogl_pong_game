package org.menus;

import org.graphics.Graphics;
import org.graphics.TextColor;

public class LevelCompleteMenu {
    public static void render(int level) {
        Graphics.drawImage(0, 0, 1280, 720, 1, "semi_transparent_white_bg");

        String text = "Level" + level + "finalizado!";
        int letterSize = 72;
        int textSize = text.length() * letterSize;

        float x = (float) -textSize / 2;
        float y = 72;

        Graphics.writeText(x, y, letterSize, text, TextColor.BLACK);
        Graphics.writeText(x + 2, y - 2, letterSize, text, TextColor.WHITE);

        letterSize = 32;
        text = "Aperte espaco para continuar";
        textSize = text.length() * letterSize;

        x = (float) -textSize / 2;
        y -= 80;

        Graphics.writeText(x, y, letterSize, text, TextColor.BLACK);
        Graphics.writeText(x + 2, y - 2, letterSize, text, TextColor.WHITE);
    }
}
