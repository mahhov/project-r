package control;

public enum KeyButton {
    KEY_W, KEY_A, KEY_S, KEY_D,
    KEY_Q, KEY_E,
    KEY_R, KEY_F, KEY_Z, KEY_X,
    KEY_SHIFT, KEY_SPACE,
    KEY_ENTER,
    KEY_C, KEY_V, KEY_I, KEY_B, KEY_M;

    final int value;

    KeyButton() {
        this.value = ordinal();
    }
}