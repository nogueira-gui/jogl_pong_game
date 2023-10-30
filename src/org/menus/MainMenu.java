package org.menus;

import com.jogamp.newt.event.KeyEvent;
import org.engine.GameLoop;
import org.graphics.*;
import org.inputs.KeyboardListener;
import org.utils.Utils;
import org.world.World;

public class MainMenu {
    public static int currentOption = 0;
    private static boolean onMainMenu = true;
    public static void update() {
        if (onMainMenu && KeyboardListener.getKey(KeyEvent.VK_UP)) {
            currentOption--;
        }

        if (onMainMenu && KeyboardListener.getKey(KeyEvent.VK_DOWN)) {
            currentOption++;
        }

        if (onMainMenu && KeyboardListener.getKey(KeyEvent.VK_ENTER)) {
            switch (currentOption) {
                case 0:
                    World.gameStarted = true;
                    onMainMenu = false;

                    GameLoop.setFPS(60);

                    break;
                case 1, 2:
                    onMainMenu = false;

                    break;
                case 3:
                    Renderer.exit();
                    break;
                default:
                    break;
            }
        }

        if (!onMainMenu && (KeyboardListener.getKey(KeyEvent.VK_ESCAPE) || KeyboardListener.getKey(KeyEvent.VK_BACK_SPACE))) {
            onMainMenu = true;
        }

        currentOption = Utils.clamp(currentOption, 0, 3);
    }

    public static void render() {
        if (onMainMenu) {
            Graphics.drawImage(0, 0, 20, "menu_background");

            drawGameName();

            drawOptions(currentOption);
            return;
        }

        if (currentOption == 1) {
            drawCommandsMenu();
        }

        if (currentOption == 2) {
            drawAboutUsMenu();
        }
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

    private static void drawOptions(float chosenOption) {
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

            if (index == chosenOption) {
                Graphics.drawImage(x - 20, y, 0.7f, "right_arrow");
            }

            y -= letterSize * 2;

            index++;
        } while (index < options.length);
    }

    private static void drawCommandsMenu() {
        int letterSize = 32;
        int textSize = "Comandos".length() * letterSize;

        float x = (float) (-textSize / 2) - (Screen.getHalfScreenWidth() - textSize);
        float y = Screen.getHalfScreenHeight() - 100;

        Graphics.drawImage(0, 0, 20, "submenu_background");
        Graphics.writeText(x, y, letterSize, "Comandos", TextColor.BLACK);
        Graphics.writeText(x + 2, y - 2, letterSize, "Comandos", TextColor.WHITE);

        y -= 30;
        Graphics.fillRect(0, y, Screen.getScreenWidth() * 0.9f, 3, new Color(255, 255, 255, 1));

        y -= 40;
        letterSize = 16;
        Graphics.writeText(x, y, letterSize, "A / Seta esquerda - Movimenta a barra para a esquerda", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "A / Seta esquerda - Movimenta a barra para a esquerda", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "D / Seta direita - Movimenta a barra para a direita", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "D / Seta direita - Movimenta a barra para a direita", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Espaco - Lanca a bola no inicio da rodada", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Espaco - Lanca a bola no inicio da rodada", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "ESC - Pausa/Continua jogo", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "ESC - Pausa/Continua jogo", TextColor.WHITE);

        y -= 60;
        Graphics.writeText(x, y, letterSize, "Ctrl C - Fecha o jogo em qualquer tela", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Ctrl C - Fecha o jogo em qualquer tela", TextColor.WHITE);

        y = -Screen.getHalfScreenHeight() + 130;
        Graphics.fillRect(0, y, Screen.getScreenWidth() * 0.9f, 3, new Color(255, 255, 255, 1));

        y -= 40;
        letterSize = 24;
        Graphics.writeText(x, y, letterSize, "ESC / Backspace - Voltar para menu", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "ESC / Backspace - Voltar para menu", TextColor.WHITE);
    }

    private static void drawAboutUsMenu() {
        int letterSize = 32;
        int textSize = "Sobre nos".length() * letterSize;

        float x = (float) (-textSize / 2) - (Screen.getHalfScreenWidth() - textSize);
        float y = Screen.getHalfScreenHeight() - 100;

        Graphics.drawImage(0, 0, 20, "submenu_background");
        Graphics.writeText(x, y, letterSize, "Sobre nos", TextColor.BLACK);
        Graphics.writeText(x + 2, y - 2, letterSize, "Sobre nos", TextColor.WHITE);

        y -= 30;
        Graphics.fillRect(0, y, Screen.getScreenWidth() * 0.9f, 3, new Color(255, 255, 255, 1));

        y -= 40;
        letterSize = 16;
        Graphics.writeText(x, y, letterSize, "Ashlley Assis - / Guide writer", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Ashlley Assis - / Guide writer", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Guilherme Nogueira - / Desenvolvedor", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Guilherme Nogueira - / Desenvolvedor", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Gustavo Aciem - 125111361436 / Design", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Gustavo Aciem - 125111361436 / Design", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Lucas Mello - 125111358721 / Desenvolvedor", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Lucas Mello - 125111358721 / Desenvolvedor", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Riley Ramalho - 125111369125 / Texture maker", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Riley Ramalho - 125111369125 / Texture maker", TextColor.WHITE);

        y -= 40;
        Graphics.writeText(x, y, letterSize, "Victor Hugo - / Design", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "Victor Hugo - / Design", TextColor.WHITE);

        y = -Screen.getHalfScreenHeight() + 130;
        Graphics.fillRect(0, y, Screen.getScreenWidth() * 0.9f, 3, new Color(255, 255, 255, 1));

        y -= 40;
        letterSize = 24;
        Graphics.writeText(x, y, letterSize, "ESC / Backspace - Voltar para menu", TextColor.BLACK);
        Graphics.writeText(x, y, letterSize, "ESC / Backspace - Voltar para menu", TextColor.WHITE);
    }
}
