package ui;

import character.Human;
import control.KeyButton;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import map.Map;

public class UiDrawerGame extends UiDrawer {
    private static final float CENTER_RECT_SIZE = .01f, CENTER_RECT_COLOR[] = new float[] {.1f, .5f, .3f, 1};
    private static final float[] BACK_COLOR = new float[] {.3f, .3f, .3f, .5f};

    private Human human;

    private UiHud hud;
    private UiStats stats;
    private UiEquipment equipment;
    private UiExperience experience;
    private UiInventory inventory;
    private UiGlows glows;
    private UiCrafting crafting;
    private UiModuleCrafting moduleCrafting;
    private UiForge forge;
    private UiMap map;
    private UiTextBox textBox;
    private UiPaneGroup[] paneGroups;
            
    /* -- PANES --
        Stats & Equipment (V)
        Stats & Experience (C)
        Inventory & Equipment (I)
        Glows & Crafting (B)
        Glows & Module Crafting (G)
        Inventory & Forge (N)
        Map (M)
     */

    public UiDrawerGame(Human human, Map map, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(keyControl, mousePosControl, mouseButtonControl);

        this.human = human;

        rects.addRect(CENTER_RECT_COLOR).setCoordinates(-CENTER_RECT_SIZE, CENTER_RECT_SIZE, CENTER_RECT_SIZE, -CENTER_RECT_SIZE);

        hud = new UiHud(rects, texts, human);

        stats = new UiStats(BACK_COLOR, rects, texts, human.getStats());
        equipment = new UiEquipment(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human, human.getEquipment());
        experience = new UiExperience(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getExperience());
        inventory = new UiInventory(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human, human.getInventory());
        glows = new UiGlows(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getGlows());
        crafting = new UiCrafting(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getCrafting());
        moduleCrafting = new UiModuleCrafting(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getModuleCrafting());
        forge = new UiForge(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, human.getForge());
        this.map = new UiMap(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, map);
        textBox = new UiTextBox(BACK_COLOR, rects, texts, human.getLog());

        equipment.setUiInventory(inventory);
        inventory.setUiEquipment(equipment);
        crafting.setUiGlow(glows);
        moduleCrafting.setUiGlow(glows);

        paneGroups = new UiPaneGroup[7];
        int i = 0;
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_V, new UiPane[] {stats, equipment});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_C, new UiPane[] {stats, experience});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_I, new UiPane[] {inventory, equipment});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_B, new UiPane[] {glows, crafting});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_G, new UiPane[] {glows, moduleCrafting});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_N, new UiPane[] {inventory, forge});
        paneGroups[i++] = new UiPaneGroup(KeyButton.KEY_M, new UiPane[] {this.map});
    }

    @Override
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
        moduleCrafting.update();
        forge.update();
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
        moduleCrafting.setInvisible();
        forge.setInvisible();
        map.setInvisible();
    }
}