package org.graphics;

public enum TextColor {
    BLACK("black"),
    WHITE("white");

    private final String color;

    TextColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
