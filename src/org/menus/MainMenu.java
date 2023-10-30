package org.menus;

import com.jogamp.newt.event.KeyEvent;
import org.engine.GameLoop;
import org.graphics.Graphics;
import org.graphics.Renderer;
import org.graphics.Screen;
import org.graphics.TextColor;
import org.inputs.KeyboardListener;
import org.utils.Utils;
import org.world.World;

public class MainMenu {
    public static int currentOption = 0;
    public static void update() {
        if (KeyboardListener.getKey(KeyEvent.VK_UP)) {
            currentOption--;
        }

        if (KeyboardListener.getKey(KeyEvent.VK_DOWN)) {
            currentOption++;
        }

        if (KeyboardListener.getKey(KeyEvent.VK_ENTER)) {
            switch (currentOption) {
                case 0:
                    World.gameStarted = true;

                    GameLoop.setFPS(60);

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    Renderer.exit();
                    break;
                default:
                    break;
            }
        }

        currentOption = Utils.clamp(currentOption, 0, 3);
    }

    public static void render() {
        // DEBUG
//        Graphics.fillRect(0, 0, 1, Screen.getUnitsTall(), new Color(255,0,0,1));
//        Graphics.fillRect(0, 0, Screen.getUnitsWide(), 1, new Color(255,0,0,1));

        Graphics.drawImage(0, 0, 20, "menu_background");

        drawGameName();

        drawOptions(currentOption);
    }

    private static void drawGameName() {
        String gameName = "PONG";
        String subtitle = "Insert coin to play";
        int letterSize = 128;
        int textSize = gameName.length() * letterSize;

        float x = (float) -textSize / 2;
        float y = Screen.getHalfScreenHeight() - 200;

        Graphics.writeText(x, y, letterSize, gameName, TextColor.BLACK);
        Graphics.writeText(x + 5, y - 5, letterSize, gameName, TextColor.WHITE);

        letterSize = 25;
        textSize = subtitle.length() * letterSize;

        x = (float) -textSize / 2;
        y -= 54;

        Graphics.writeText(x, y, letterSize, subtitle, TextColor.BLACK);
        Graphics.writeText(x + 2, y - 2, letterSize, subtitle, TextColor.WHITE);
    }

    private static void drawOptions(float choosenOption) {
        int letterSize = 32;
        int textSize;
        String[] options = { "Jogar", "Comandos", "Sobre nos", "Sair" };
        float x, y = 0;

        int index = 0;
        do {
            textSize = options[index].length() * letterSize;

            x = (float) -textSize / 2;

            if (index == 0) {
                y = (float) -Screen.getHalfScreenHeight() / 4;
            }

            Graphics.writeText(x, y, letterSize, options[index], TextColor.BLACK);
            Graphics.writeText(x + 2, y - 2, letterSize, options[index], TextColor.WHITE);

            if (index == choosenOption) {
                Graphics.drawImage(x - 20, y, 0.7f, "right_arrow");
            }

            y -= letterSize * 2;

            index++;
        } while (index < options.length);
    }

    public static void changeOption(int newOption) {
        newOption = Utils.clamp(newOption, 0, 3);

        currentOption = newOption;
    }
}
