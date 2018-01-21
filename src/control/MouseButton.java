package control;

public enum MouseButton {
    PRIMARY, SECONDARY, NONE;

    final int value;

    MouseButton() {
        this.value = ordinal();
    }
}