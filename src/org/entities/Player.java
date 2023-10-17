package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.engine.GameLoop;
import org.graphics.Color;
import org.graphics.Graphics;
import org.graphics.Screen;
import org.inputs.KeyboardListener;
import org.objects.Rectangle;
import org.utils.Utils;

public class Player extends Rectangle {
    public final static int PLAYER_WIDTH = 150;
    public final static int PLAYER_HEIGHT = 15;

    private int score = 0;
    private int lives = 5;

    public final static int TOTAL_LIVES = 5;
    public Player() {
        super(0,-Screen.getHalfScreenHeight() + 20,PLAYER_WIDTH,PLAYER_HEIGHT, new Color(255, 255, 255, 1));
    }

    @Override
    public void update() {
        super.update();

        float xInput = 0;
        float SPEED = 300f;

        if(KeyboardListener.getKey(KeyEvent.VK_A) || KeyboardListener.getKey(KeyEvent.VK_LEFT)) {
            xInput--;
        }

        if(KeyboardListener.getKey(KeyEvent.VK_D) || KeyboardListener.getKey(KeyEvent.VK_RIGHT)) {
            xInput++;
        }

        x += xInput * SPEED * GameLoop.updateDelta();
    }

    @Override
    public void render() {
        x = Utils.clamp(x, LEFT_BOUNDARY, RIGHT_BOUNDARY);

        Graphics.fillRect(x, y, width, height, color);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    public void addPoints(int point) {
        score += point;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingLives() {
        return lives;
    }

    public void decreaseLive() {
        lives--;
    }

    public void resetPlayerX() {
        this.x = 0;
    }

    public void resetScore() {
        this.score = 0;
    }

}
