package ui;

import control.KeyControl;

class UiPaneGroup {
    private int keyBind;
    private UiPane[] panes;

    UiPaneGroup(int keyBind, UiPane[] panes) {
        this.keyBind = keyBind;
        this.panes = panes;
    }

    void handleKeyPress(KeyControl keyControl) {
        if (!keyControl.isKeyPressed(keyBind))
            return;

        boolean active = isActive();

        if (!active)
            showAll();
        else
            hideAll();
    }

    private boolean isActive() {
        for (UiPane pane : panes)
            if (!pane.isVisible())
                return false;
        return true;
    }

    private void showAll() {
        for (UiPane pane : panes)
            pane.setVisible();
    }

    private void hideAll() {
        for (UiPane pane : panes)
            pane.setInvisible();
    }
}