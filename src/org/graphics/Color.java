package org.graphics;

public class Color {
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public Color(int red, int green, int blue, float alpha) {
        this.red = parseColor(translateColorRange(red));
        this.green = parseColor(translateColorRange(green));
        this.blue = parseColor(translateColorRange(blue));
        this.alpha = parseColor(alpha);
    }

    private float translateColorRange(int value) {
        return (float) value / 255;
    }

    private float parseColor(float value) {
        return Math.max(0, Math.min(value, 1));
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public String toString() {
        return "R: " + getRed() + " G: " + getGreen() + " B: " + getBlue() + " A: " + getAlpha();
    }
}
