package org.objects;

import org.graphics.Color;
import org.graphics.Graphics;
import org.graphics.Screen;
import org.utils.Utils;

public abstract class Rectangle implements GameObject {
    protected float x;
    protected final float y;

    protected final float width;
    protected final float height;

    protected float LEFT_BOUNDARY = 0;
    protected float RIGHT_BOUNDARY = 0;
    protected float UPPER_BOUNDARY = 0;
    protected float BOTTOM_BOUNDARY = 0;

    protected final Color color;

    public Rectangle(float x, float y, float width, float height, Color color) {
        int halfScreenWidth = Screen.getHalfScreenWidth();
        int halfScreenHeight = Screen.getHalfScreenHeight();

        this.x = Utils.clamp(x, -halfScreenWidth, halfScreenWidth - (height / 2));
        this.y = Utils.clamp(y, -halfScreenHeight, halfScreenHeight - (height / 2));

        this.width = width;
        this.height = height;

        this.color = color;
    }

    @Override
    public void update() {
        calculateBoundaries();
    }

    @Override
    public void render() {
        Graphics.fillRect(x, y, width, height, color);
    }

    @Override
    public void calculateBoundaries() {
        float halfScreenWidth = Screen.getUnitsWide() / 2;
        float halfScreenHeight = Screen.getUnitsTall() / 2;

        LEFT_BOUNDARY = -halfScreenWidth + width / 2;
        RIGHT_BOUNDARY = halfScreenWidth - width / 2;

        UPPER_BOUNDARY = -halfScreenHeight + height / 2;
        BOTTOM_BOUNDARY = halfScreenHeight - height / 2;
    }
}
