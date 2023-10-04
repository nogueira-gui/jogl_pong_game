package main;

import graphics.Window;

import java.util.Scanner;

public class Client {
    public static void main(String []args) {
        System.out.println("┌───────── MENU ─────────┐");
        System.out.println("│ 1. Host Game           │");
        System.out.println("│ 2. Client Access       │");
        System.out.println("│ 3. Offline Access      │");
        System.out.println("└────────────────────────┘");
        Window window = new Window();
        window.init();
    }
}
