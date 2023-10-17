package org.entities;

import org.graphics.Color;
import org.objects.Rectangle;

public class Block extends Rectangle {
    public final static int BLOCK_WIDTH = 75;
    public final static int BLOCK_HEIGHT = 20;

    private boolean isDestroyed = false;

    public Block(float x, float y, Color color) {
        super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT, color);
    }

    public void destroy() {
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public void render() {
        if (!isDestroyed) {
            super.render();
        }
    }
}
