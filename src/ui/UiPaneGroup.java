package ui;

import control.KeyControl;

class UiPaneGroup {
    private int keyBind;
    private UiTextListPane[] panes;

    UiPaneGroup(int keyBind, UiTextListPane[] panes) {
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
        for (UiTextListPane pane : panes)
            if (!pane.isVisible())
                return false;
        return true;
    }

    private void showAll() {
        for (UiTextListPane pane : panes)
            pane.setVisible();
    }

    private void hideAll() {
        for (UiTextListPane pane : panes)
            pane.setInvisible();
    }
}