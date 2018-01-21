package control;

enum MouseButton {
    PRIMARY, SECONDARY;

    final int value;

    MouseButton() {
        this.value = ordinal();
    }
}