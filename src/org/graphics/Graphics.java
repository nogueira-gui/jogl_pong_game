package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import org.textures.TextureManager;

public class Graphics {

    private static final GL2 gl = EventListener.gl;
    public static void fillRect(float x, float y, float width, float height, Color color) {
        float x1 = x - (width / 2);
        float y1 = y - (height / 2);
        float x2 = x1 + width;
        float y2 = y1 + height;

        float z = 0;

        gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);
                gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
                gl.glVertex3f(x1, y1, z);
                gl.glVertex3f(x2, y1, z);
                gl.glVertex3f(x2, y2, z);
                gl.glVertex3f(x1, y2, z);
            gl.glEnd();
        gl.glPopMatrix();
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
            double z = 2;

            gl.glVertex3d(x, y, z);
        }
        gl.glEnd();
        gl.glFlush();
    }

    public static void drawImage(float x, float y, float scaleFactor, String imageName) {
        gl.glDisable(GL2.GL_DEPTH_TEST);

        float width = 64 * scaleFactor;
        float height = 64 * scaleFactor;

        Texture texture = TextureManager.getTexture(imageName);
        Color color = TextureManager.getPredominantColor(imageName);

        if (texture != null) {
            gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());

            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        }


        gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        float x1 = x - (width / 2);
        float y1 = y - (height / 2);
        float x2 = x1 + width;
        float y2 = y1 + height;

        float z = 2;

        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(x1, y1, z);

        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(x2, y1, z);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(x2, y2, z);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(x1, y2, z);

        gl.glEnd();

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        gl.glFlush();

        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    public static void writeText(float x, float y, float width, float height, String text) {
        gl.glDisable(GL2.GL_DEPTH_TEST);

        int position = 1;
        for (String c : text.toLowerCase().split("")) {
            String letter = "letter_" + c;

            Texture texture = TextureManager.getTexture(letter);
            Color color = TextureManager.getPredominantColor(letter);

            if (texture != null) {
                gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());

                texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            }

            gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

            float x1 = ((x + position * width / 1.5f) - (width / 2));
            float y1 = y - (height / 2);
            float x2 = x1 + width;
            float y2 = y1 + height;

            float z = 2;

            gl.glBegin(GL2.GL_QUADS);

            gl.glTexCoord2f(0, 1);
            gl.glVertex3f(x1, y1, z);

            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(x2, y1, z);

            gl.glTexCoord2f(1, 0);
            gl.glVertex3f(x2, y2, z);

            gl.glTexCoord2f(0, 0);
            gl.glVertex3f(x1, y2, z);

            gl.glEnd();
            gl.glFlush();

            gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
            position++;
        }

        gl.glEnable(GL2.GL_DEPTH_TEST);
    }
}
