package org.textures;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ColorExtractor {
    public static int[] getMostCommonColor(Map<Integer, Integer> map) {
        if (map == null) {
            return new int[] { 255, 255, 255 };
        }

        Map.Entry<Integer, Integer> mostCommonColorEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (mostCommonColorEntry == null) {
            return new int[] { 255, 255, 255 };
        }

        int colorValue = mostCommonColorEntry.getKey();

        return getRGBArr(colorValue);
    }

    public static int[] getRGBArr(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 0xFF;

        return new int[] { red, green, blue };
    }

    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];

        int tolerance = 10;

        return (Math.abs(rgDiff) <= tolerance || Math.abs(rbDiff) <= tolerance);
    }

    public static Map<Integer, Integer> extractImagePixels(File file) {
        try {
            ImageInputStream is = ImageIO.createImageInputStream(file);

            Iterator<ImageReader> iter = ImageIO.getImageReaders(is);

            if (!iter.hasNext()) {
                return null;
            }

            ImageReader imageReader = iter.next();
            imageReader.setInput(is);

            BufferedImage image = imageReader.read(0);

            int width = image.getWidth();
            int height = image.getHeight();

            Map<Integer, Integer> map = new HashMap<>();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = image.getRGB(x, y);
                    int[] pixelRGB = getRGBArr(pixel);

                    if (!isGray(pixelRGB)) {
                        Integer counter = map.get(pixel);

                        if (counter == null) {
                            counter = 0;
                        }

                        counter++;
                        map.put(pixel, counter);
                    }
                }
            }

            return map;
        } catch (IOException ignored) {
            return null;
        }
    }
}
