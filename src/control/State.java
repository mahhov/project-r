package control;

enum State {
    UP, DOWN, PRESSED, RELEASED;

    final int value;

    State() {
        value = ordinal();
    }
}