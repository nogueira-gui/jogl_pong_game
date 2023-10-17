package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import org.resource.TextureManager;

public class Graphics {

    private static final GL2 gl = EventListener.gl;

    public static void fillRect(float x, float y, float width, float height, Color color) {
        float x1 = x - (width / 2);
        float y1 = y - (height / 2);
        float x2 = x1 + width;
        float y2 = y1 + height;

        gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(x1, y1);
            gl.glVertex2f(x2, y1);
            gl.glVertex2f(x2, y2);
            gl.glVertex2f(x1, y2);
        gl.glEnd();
        gl.glFlush();
    }

    public static void fillCircle(float centerX, float centerY, float radius, Color color) {
        final int CIRCLE_SEGMENTS = 720;
        float angleIncrement = (float) (2 * Math.PI / CIRCLE_SEGMENTS);

        gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(centerX, centerY);

        for (double i = 0; i < CIRCLE_SEGMENTS; i += 0.1) {
            double angle = i * angleIncrement;

            double x = radius * Math.cos(angle) + centerX;
            double y = radius * Math.sin(angle) + centerY;

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
        gl.glFlush();
    }

    public static void drawImage(float x, float y, float scaleFactor, String imageName) {
        float width = 64 * scaleFactor;
        float height = 64 * scaleFactor;

        Texture texture = TextureManager.getTexture(imageName);

        if (texture != null) {
            gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());

            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        }

        gl.glColor4f(1, 1, 1, 1);

        float x1 = x - (width / 2);
        float y1 = y - (height / 2);
        float x2 = x1 + width;
        float y2 = y1 + height;

        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(x1, y1);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(x2, y1);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(x2, y2);

        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(x1, y2);

        gl.glEnd();

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        gl.glFlush();

    }

    public static void writeText(float x, float y, float width, float height, String text) {
        int position = 1;
        for (String c : text.toLowerCase().split("")) {
            Texture texture = TextureManager.getTexture("letter_" + c);

            if (texture != null) {
                gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());

                texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            }

            gl.glColor4f(1, 1, 1, 1);

            float x1 = (float) ((x + position * width / 1.5) - (width / 2));
            float y1 = y - (height / 2);
            float x2 = x1 + width;
            float y2 = y1 + height;

            gl.glBegin(GL2.GL_QUADS);

            gl.glTexCoord2f(0, 1);
            gl.glVertex2f(x1, y1);

            gl.glTexCoord2f(1, 1);
            gl.glVertex2f(x2, y1);

            gl.glTexCoord2f(1, 0);
            gl.glVertex2f(x2, y2);

            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(x1, y2);

            gl.glEnd();
            gl.glFlush();

            gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
            position++;
        }
    }
}
