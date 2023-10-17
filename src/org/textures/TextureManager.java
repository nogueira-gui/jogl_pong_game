package org.textures;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.graphics.Color;
import org.graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class TextureManager {
    private static final ArrayList<TextureObject> textures = new ArrayList<>();

    public final static int TEXTURE_WIDTH = 64;

    private static File[] getFiles() {
        try {
            return new File(Objects.requireNonNull(TextureManager.class.getResource("/assets/")).toURI()).listFiles();
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    private static Texture extractTextureFromImage(BufferedImage image) {
        return AWTTextureIO.newTexture(Renderer.getProfile(), image, true);
    }

    public static void loadTextures() {
        try {
            System.out.println("Loading textures...");
            File[] files = getFiles();

            if (files == null) {
                return;
            }

            int index = 0;
            final int totalFiles = files.length;
            for (File file : files) {
                String filename = file.getName();

                if (file.isFile() && filename.endsWith(".png")) {
                    System.out.printf("\tLoading %s\n", filename);
                    BufferedImage image = ImageIO.read(file);

                    if (image != null) {
                        filename = filename.substring(0, filename.indexOf("."));

                        Map<Integer, Integer> imageMap = ColorExtractor.extractImagePixels(file);

                        int[] mostCommonColor = ColorExtractor.getMostCommonColor(imageMap);

                        Color predominantColor = new Color(mostCommonColor[0], mostCommonColor[1], mostCommonColor[2], 1);


                        textures.add(new TextureObject(filename, extractTextureFromImage(image), predominantColor));

                        image.flush();
                        index++;
                    }
                }
            }

            System.out.printf("%d/%d files loaded\n", index, totalFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Texture getTexture(String name) {
        for (TextureObject texture : textures) {
            if (texture.getTextureName().equals(name)) {
                return texture.getTexture();
            }
        }

        return null;
    }

    public static Color getPredominantColor(String name) {
        for (TextureObject texture : textures) {
            if (texture.getTextureName().equals(name)) {
                return texture.getPredominantColor();
            }
        }

        return new Color(255, 255, 255, 1);
    }
}
