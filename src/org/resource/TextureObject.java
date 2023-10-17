package org.resource;

import com.jogamp.opengl.util.texture.Texture;

public class TextureObject {
    private final String textureName;
    private final Texture texture;

    public TextureObject(String name, Texture texture) {
        this.textureName = name;
        this.texture = texture;
    }

    public String getTextureName() {
        return textureName;
    }

    public Texture getTexture() {
        return texture;
    }
}
