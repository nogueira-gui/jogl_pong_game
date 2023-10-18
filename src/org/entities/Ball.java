package org.entities;

import org.engine.GameLoop;
import org.graphics.Color;
import org.graphics.Graphics;
import org.objects.Circle;
import org.utils.Utils;
import org.world.World;

import java.util.Random;

public class Ball extends Circle {

    private float yInput = 0;
    private float xInput = 0;

    private boolean isBallCollided = false;
    private boolean ballThrown = false;
    private boolean initFall = true;

    private int hitCount = 0;

    public Ball() {
        super(0, 0, 10, new Color(255, 255, 255, 255));
    }

    @Override
    public void update() {
        super.update();

        if (!ballThrown) {
            x = World.getPlayerX();
            y = World.getPlayerY() + (radius * 2) - 3;

            return;
        }

        if (y <= BOTTOM_BOUNDARY) {
            World.decreasePlayerLife();
            World.resetPlayerPosition();
            resetBall();
        }

        if (y == UPPER_BOUNDARY) {
            yInput = -yInput;
            hitCount++;
        }

        if (x == LEFT_BOUNDARY || x == RIGHT_BOUNDARY) {
            xInput = -xInput;
        }

        float HORIZONTAL_SPEED = 250f;
        float VERTICAL_SPEED = 250f;

        y += yInput * VERTICAL_SPEED * GameLoop.updateDelta();
        x += xInput * HORIZONTAL_SPEED * GameLoop.updateDelta();
    }

    @Override
    public void render() {
        x = Utils.clamp(x, LEFT_BOUNDARY, RIGHT_BOUNDARY);
        y = Utils.clamp(y, BOTTOM_BOUNDARY - radius * 2, UPPER_BOUNDARY);

        Graphics.fillCircle(x, y, radius, color);
    }

    @Override
    public void calculateBoundaries() {
        super.calculateBoundaries();

        if (!ballThrown) {
            LEFT_BOUNDARY += ((float) Player.PLAYER_WIDTH / 2) - radius;
            RIGHT_BOUNDARY -= ((float) Player.PLAYER_WIDTH / 2) - radius;
        }
    }

    public void flipXDirection() {
    }

    public void flipYDirection() {
        if (ballThrown) {
            isBallCollided = true;
        }

        if (isBallCollided && ballThrown && initFall) {
            xInput = randomInitX();
            initFall = false;
        }

        yInput = -yInput;
        hitCount++;
    }

    private int randomInitX() {
        Random rand = new Random();
        int value = 0;

        while (value == 0) {
            value = rand.nextInt((1 - (-1)) + 1) + (-1);
        }

        return value;
    }

    public void resetBall() {
        x = 0;
        y = 0;

        xInput = 0;
        yInput = 0;

        isBallCollided = false;
        ballThrown = false;
        initFall = true;
    }

    public void throwBall() {

        if (!ballThrown && !World.ignoreNextThrow) {
            yInput++;
            ballThrown = true;
        }
    }

    public int getHitCount() {
        return hitCount;
    }
}
