package ui;

import control.KeyButton;
import control.KeyControl;

class UiPaneGroup {
    private KeyButton keyBind;
    private UiPane[] panes;

    UiPaneGroup(KeyButton keyBind, UiPane[] panes) {
        this.keyBind = keyBind;
        this.panes = panes;
    }

    boolean shouldOpen(KeyControl keyControl) {
        if (!keyControl.isKeyPressed(keyBind))
            return false;

        if (!isActive())
            return true;

        hideAll();

        return false;
    }

    private boolean isActive() {
        for (UiPane pane : panes)
            if (!pane.isVisible())
                return false;
        return true;
    }

    void showAll() {
        for (UiPane pane : panes)
            pane.setVisible();
    }

    private void hideAll() {
        for (UiPane pane : panes)
            pane.setInvisible();
    }
}