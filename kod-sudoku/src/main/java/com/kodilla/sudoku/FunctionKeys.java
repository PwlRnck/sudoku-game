package com.kodilla.sudoku;

public enum FunctionKeys {
    KEYBOARD("k", "keyboard input"),
    FILE("f", "file input"),
    AGAIN("a", "try again"),
    END("f", "finish keyboard input"),
    QUIT("q", "quit the game");

    private final String key;
    private final String keyFunction;

    FunctionKeys(String key, String keyFunction) {
        this.key = key;
        this.keyFunction = keyFunction;
    }

    public String key() {
        return key;
    }

    public String keyFunction() {
        return keyFunction;
    }

}
