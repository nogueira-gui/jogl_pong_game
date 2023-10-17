package org.engine;

import org.graphics.Renderer;

public class Main {
    public static void main(String[] args) {
        Renderer.init();
        GameLoop.start();
    }
}
