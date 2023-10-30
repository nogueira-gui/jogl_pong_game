package org.engine;

import org.graphics.Renderer;

public class Main {
    public static void main(String[] args) {
        System.setProperty("jnlp.newt.window.icons", "assets\\heart.png assets\\heart.png");

        Renderer.init();
        GameLoop.start();
    }
}
