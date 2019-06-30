package libgdx.implementations.iq;

import libgdx.resources.dimen.MainDimen;

public enum SkelGameButtonSize implements libgdx.controls.button.ButtonSize {

    NAV_BUTTON(MainDimen.horizontal_general_margin.getDimen() * 4.2f, MainDimen.horizontal_general_margin.getDimen() * 4.2f),

    HEADER_BUTTON(MainDimen.horizontal_general_margin.getDimen() * 8f, MainDimen.horizontal_general_margin.getDimen() * 3f);

    private float width;
    private float height;

    SkelGameButtonSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
