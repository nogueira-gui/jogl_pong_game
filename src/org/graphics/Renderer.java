package org.graphics;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import org.inputs.KeyboardListener;
import org.inputs.RendererInputControl;

import java.awt.*;

public class Renderer {
    private static GLWindow window = null;
    private static final GLProfile profile = GLProfile.get(GLProfile.GL2);

    public static final int screenWidth = 1280;
    public static final int screenHeight = 720;

    public static final int MIN_SCREEN_WIDTH = 1280;
    public static final int MIN_SCREEN_HEIGHT = 720;

    private static boolean isFullscreen = false;

    public static void init() {
        GLProfile.initSingleton();

        GLCapabilities caps = new GLCapabilities(profile);

        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(false);

        window.addWindowListener(new WindowListener());
        window.addGLEventListener(new EventListener());

        window.addKeyListener(new KeyboardListener());
        window.addKeyListener(new RendererInputControl());

        window.setFullscreen(isFullscreen);
        window.requestFocus();

        centerWindow();

        window.setVisible(true);
    }

    public static void toggleFullscreen() {
        if (isFullscreen) {
            window.setFullscreen(false);
            window.setSize(1280, 720);
            centerWindow();
        } else {
            window.setFullscreen(true);
        }

        isFullscreen = !isFullscreen;
    }

    public static void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();

        int x = (int) (screenSize.getWidth() - windowWidth) / 2;
        int y = (int) (screenSize.getHeight() - windowHeight) / 2;

        window.setPosition(x, y);
    }

    public static void exit() {
        System.exit(0);
    }

    public static void render() {
        if (window == null) return;

        window.display();
    }

    public static void setSize(int width, int height) {
        window.setSize(width, height);
    }

    public static GLWindow getWindow() {
        return window;
    }

    public static GLProfile getProfile() {
        return profile;
    }
}
