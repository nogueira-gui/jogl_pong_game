package org.textures;

import com.jogamp.opengl.util.texture.Texture;
import org.graphics.Color;

public class TextureObject {
    private final String textureName;
    private final Texture texture;
    private final Color predominantColor;

    public TextureObject(String name, Texture texture, Color color) {
        this.textureName = name;
        this.texture = texture;
        this.predominantColor = color;
    }

    public String getTextureName() {
        return textureName;
    }

    public Texture getTexture() {
        return texture;
    }

    public Color getPredominantColor() {
        return  predominantColor;
    }
}
