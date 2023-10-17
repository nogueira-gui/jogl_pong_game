package org.entities;

import org.engine.GameLoop;
import org.graphics.Color;
import org.graphics.Graphics;
import org.objects.Circle;
import org.utils.Utils;
import org.world.World;

import java.util.Random;

public class Ball extends Circle {

    private float yInput = -1;
    private float xInput = 0;

    private boolean initFall = true;

    public Ball() {
        super(0, 0, 10, new Color(255, 255, 255, 255));
    }

    @Override
    public void update() {
        super.update();

        if (y <= BOTTOM_BOUNDARY) {
            World.decreasePlayerLife();
            World.resetPlayerPosition();
            resetBall();
        }

        if (y == UPPER_BOUNDARY) {
            yInput = -yInput;
        }

        if (x == LEFT_BOUNDARY || x == RIGHT_BOUNDARY) {
            xInput = -xInput;
        }

        float SPEED = 250f;
        y += yInput * SPEED * GameLoop.updateDelta();
        x += xInput * SPEED * GameLoop.updateDelta();
    }

    @Override
    public void render() {
        x = Utils.clamp(x, LEFT_BOUNDARY, RIGHT_BOUNDARY);
        y = Utils.clamp(y, BOTTOM_BOUNDARY - radius * 2, UPPER_BOUNDARY);

        Graphics.fillCircle(x, y, radius, color);
    }

    public void flipXDirection() {
        initFall = false;
    }

    public void flipYDirection() {
        if (initFall) {
            xInput = randomInitX();
            initFall = false;
        }

        yInput = -yInput;
    }

    private int randomInitX() {
        Random rand = new Random();
        int value = 0;

        while (value == 0) {
            value = rand.nextInt((1 - (-1)) + 1) + (-1);
        }

        return value;
    }

    private void resetBall() {
        x = 0;
        y = 0;

        xInput = 0;
        yInput = -1;

        initFall = true;
    }
}
