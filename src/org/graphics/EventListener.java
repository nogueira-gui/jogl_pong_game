package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import org.textures.TextureManager;
import org.world.World;

public class EventListener implements GLEventListener {
    public static GL2 gl = null;

    public static int vSync = 1;

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();

        gl.setSwapInterval(vSync);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(0,0,0,1);

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);

        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        TextureManager.loadTextures();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        Renderer.exit();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        World.render();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        float halfWidth = Screen.getUnitsWide() / 2;
        float halfHeight = Screen.getUnitsTall() / 2;

        gl.glOrtho(-halfWidth, halfWidth, -halfHeight, halfHeight, -1, 1);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public static void toggleVSync() {
        if (vSync == 1) {
            vSync = 0;
        } else {
            vSync = 1;
        }
    }
}
