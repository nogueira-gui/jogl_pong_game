package org.objects;

public interface GameObject {
    void update();

    void render();

    void calculateBoundaries();

    float getX();

    float getY();
}
