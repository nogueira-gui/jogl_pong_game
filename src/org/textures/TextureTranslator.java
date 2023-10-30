package org.textures;

public class TextureTranslator {
    public static String translate(String input) {
        return switch (input) {
            case "-" -> "dash";
            case "'" -> "apostrophe";
            case "." -> "dot";
            case "!" -> "exclamation";
            case "/" -> "slash";
            default -> input;
        };
    }
}
