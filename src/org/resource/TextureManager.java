package org.resource;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

            int index = 0;
            final int totalFiles = files.length;
            for (File file : files) {
                String filename = file.getName();

                if (file.isFile() && filename.endsWith(".png")) {
                    System.out.printf("\tLoading %s\n", filename);
                    BufferedImage image = ImageIO.read(file);

                    if (image != null) {
                        filename = filename.substring(0, filename.indexOf("."));

                        textures.add(new TextureObject(filename, extractTextureFromImage(image)));

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
}
