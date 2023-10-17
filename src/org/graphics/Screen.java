package org.graphics;

public class Screen {
    private static final int unitsWide = 1280;

    public static int getScreenWidth() {
        return Renderer.getWindow().getWidth();
    }

    public static int getScreenHeight() {
        return Renderer.getWindow().getHeight();
    }

    public static int getHalfScreenWidth() {
        return getScreenWidth() / 2;
    }

    public static int getHalfScreenHeight() {
        return getScreenHeight() / 2;
    }

    public static float getUnitsWide() {
        return unitsWide;
    }

    public static float getUnitsTall() {
        return ((float) getScreenHeight() / ((float) getScreenWidth() / unitsWide));
    }
}
