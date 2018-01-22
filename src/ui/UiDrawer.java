package ui;

import character.Human;
import control.KeyButton;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import map.Map;
import shape.Rects;
import shape.Texts;

public class UiDrawer {
    static float SMALL_MARGIN = .02f, MEDIUM_MARGIN = .1f;
    private static float CENTER_RECT_SIZE = .01f, CENTER_RECT_COLOR[] = new float[] {.1f, .5f, .3f, 1};
    static final float[] BACK_COLOR = new float[] {.3f, .3f, .3f, .5f};

    private Human human;
    private Rects rects;
    private Texts texts;

    private UiHud hud;
    private UiStats stats;
    private UiEquipment equipment;
    private UiExperience experience;
    private UiInventory inventory;
    private UiGlows glows;
    private UiCrafting crafting;
    private UiMap map;
    private UiTextBox textBox;
    private Texts.Text fpsText;
    private UiPaneGroup[] paneGroups;
            
    /* -- PANES --
        Stats & Equipment (V)
        Stats & Experience (C)
        Inventory & Equipment (I)
        Glows & Crafting (B)
        Map (M)
     */

    // controls
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    public UiDrawer(Human human, Map map, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.human = human;
        rects = new Rects(200); // todo allow dynamic growing size
        texts = new Texts(1000);

        // center crosshair
        rects.addRect(CENTER_RECT_COLOR).setCoordinates(-CENTER_RECT_SIZE, CENTER_RECT_SIZE, CENTER_RECT_SIZE, -CENTER_RECT_SIZE);

        // hud
        hud = new UiHud(rects, texts, human);

        // panes
        stats = new UiStats(BACK_COLOR, rects, texts, human.getStats());
        equipment = new UiEquipment(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human, human.getEquipment());
        experience = new UiExperience(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getExperience());
        inventory = new UiInventory(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human, human.getInventory());
        glows = new UiGlows(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getGlows());
        crafting = new UiCrafting(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human, human.getCrafting());
        this.map = new UiMap(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, map);
        textBox = new UiTextBox(BACK_COLOR, rects, texts, human.getLog());

        equipment.setUiInventory(inventory);
        inventory.setUiEquipment(equipment);
        crafting.setUiGlow(glows);

        paneGroups = new UiPaneGroup[5];
        int i = 0;
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_V, new UiPane[] {stats, equipment});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_C, new UiPane[] {stats, experience});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_I, new UiPane[] {inventory, equipment});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_B, new UiPane[] {glows, crafting});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_M, new UiPane[] {this.map});

        // fps
        fpsText = texts.addText();
        fpsText.setCoordinates(-1, 1, .95f);

        // control
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    public void update() {
        if (human.isFollowZoom())
            hud.hide();
        else
            hud.show();

        for (UiPaneGroup paneGroup : paneGroups)
            if (paneGroup.shouldOpen(keyControl)) {
                hideAll();
                paneGroup.showAll();
                break;
            }

        stats.update();
        equipment.update();
        experience.update();
        inventory.update();
        glows.update();
        crafting.update();
        map.update();

        if (keyControl.isKeyPressed(KeyButton.KEY_ENTER))
            textBox.toggle();
        textBox.update();

        rects.doneAdding();
        texts.doneAdding();
    }

    private void hideAll() {
        stats.setInvisible();
        equipment.setInvisible();
        experience.setInvisible();
        inventory.setInvisible();
        glows.setInvisible();
        crafting.setInvisible();
        map.setInvisible();
    }

    public void updateFps(int fps) {
        fpsText.setText("fps " + fps);
    }

    public void draw() {
        rects.draw();
    }

    public void drawText() {
        texts.draw();
    }
}