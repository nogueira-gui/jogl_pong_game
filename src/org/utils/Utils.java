package org.utils;

public class Utils {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static int clamp (int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
