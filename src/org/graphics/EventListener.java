package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.textures.TextureManager;
import org.world.World;

public class EventListener implements GLEventListener {
    public static GL2 gl = null;
    public static GLUT glut = null;
    public static GLU glu = null;

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        gl.setSwapInterval(0);

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glClearColor(0, 0, 0, 1);

        createLights();

        TextureManager.loadTextures();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        Renderer.exit();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        glut = new GLUT();

        gl.glClearColor(0, 0, 0, 1);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        float[] lightPosition = { World.getBallX(), World.getBallY(), 100.0f, 1.0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);

        World.render();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        float halfWidth = Screen.getUnitsWide() / 2;
        float halfHeight = Screen.getUnitsTall() / 2;

        gl.glOrtho(-halfWidth, halfWidth, -halfHeight, halfHeight, -1280, 1280);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void createLights() {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glShadeModel(GL2.GL_FLAT);

        float[] lightPosition = { World.getBallX(), World.getBallY(), 50.0f, 1f };

        float[] ambientLight = { 0.5f, 0.5f, 0.5f, 0.5f };
        float[] specularLight = { 0.5f, 0.5f, 0.5f, 0.5f };

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);

        gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, 128);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specularLight, 0);
    }

    public static void turnOnTheLights() {
        gl.glEnable(GL2.GL_LIGHTING);
    }

    public static void turnOffTheLights() {
        gl.glDisable(GL2.GL_LIGHTING);
    }
}
