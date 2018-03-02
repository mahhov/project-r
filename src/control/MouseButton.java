package control;

public enum MouseButton {
    PRIMARY, SECONDARY, NONE;

    final int value;

    MouseButton() {
        value = ordinal();
    }
}