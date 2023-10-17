package org.objects;

import org.entities.InfoBoard;
import org.graphics.Color;
import org.graphics.Graphics;
import org.graphics.Screen;
import org.utils.Utils;

public abstract class Circle implements GameObject {
    protected float x;
    protected float y;
    protected final float radius;

    protected float LEFT_BOUNDARY = 0;
    protected float RIGHT_BOUNDARY = 0;
    protected float BOTTOM_BOUNDARY = 0;
    protected float UPPER_BOUNDARY = 0;

    protected final Color color;

    public Circle(float x, float y, float radius, Color color) {
        int halfScreenWidth = Screen.getHalfScreenWidth();
        int halfScreenHeight = Screen.getHalfScreenHeight();

        this.x = Utils.clamp(x, -halfScreenWidth, halfScreenWidth - radius);
        this.y = Utils.clamp(y, -halfScreenHeight, halfScreenHeight - radius);

        this.radius = radius;
        this.color = color;
    }

    @Override
    public void update() {
        calculateBoundaries();
    }

    @Override
    public void render() {
        x = Utils.clamp(x, LEFT_BOUNDARY, RIGHT_BOUNDARY);
        y = Utils.clamp(y, BOTTOM_BOUNDARY, UPPER_BOUNDARY);

        Graphics.fillCircle(x, y, radius, color);
    }

    @Override
    public void calculateBoundaries() {
        float halfScreenWidth = Screen.getUnitsWide() / 2;
        float halfScreenHeight = Screen.getUnitsTall() / 2;

        LEFT_BOUNDARY = -halfScreenWidth + radius;
        RIGHT_BOUNDARY = halfScreenWidth - radius;

        UPPER_BOUNDARY = halfScreenHeight - radius - InfoBoard.BOARD_HEIGHT;
        BOTTOM_BOUNDARY = -halfScreenHeight + radius;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }
}
