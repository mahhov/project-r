package world;

import util.LList;
import util.MathNumbers;

class DynamicCell {
    private LList<WorldElement> elements;

    DynamicCell() {
        elements = new LList<>();
    }

    LList<WorldElement>.Node add(WorldElement element) {
        return elements.addTail(element);
    }

    void remove(LList<WorldElement>.Node elementNode) {
        elements.remove(elementNode);
    }

    boolean empty() {
        return elements.size() == 0;
    }

    WorldElement checkHit(float x, float y, float z, float range) {
        for (WorldElement element : elements) {
            float dx = element.getX() - x;
            float dy = element.getY() - y;
            float dz = element.getZ() - z;
            if (MathNumbers.magnitude(dx, dy, dz) < range + element.getSize() / 2) // todo : use mag Sqred for performance
                return element;
        }
        return null;
    }
}