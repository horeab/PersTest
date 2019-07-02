package libgdx.implementations.skel;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.Res;

public enum SkelGameButtonSkin implements libgdx.controls.button.ButtonSkin {

    b0(SkelGameSpecificResource.b0, SkelGameSpecificResource.b0c, SkelGameSpecificResource.b0, SkelGameSpecificResource.b0, null),
    b0f(SkelGameSpecificResource.b0f, SkelGameSpecificResource.b0c, SkelGameSpecificResource.b0f, SkelGameSpecificResource.b0f, null),
    b0r(SkelGameSpecificResource.b0r, SkelGameSpecificResource.b0c, SkelGameSpecificResource.b0r, SkelGameSpecificResource.b0r, null),

    b1(SkelGameSpecificResource.b1, SkelGameSpecificResource.b1c, SkelGameSpecificResource.b1, SkelGameSpecificResource.b1, null),
    b1f(SkelGameSpecificResource.b1f, SkelGameSpecificResource.b1c, SkelGameSpecificResource.b1f, SkelGameSpecificResource.b1f, null),
    b1r(SkelGameSpecificResource.b1r, SkelGameSpecificResource.b1c, SkelGameSpecificResource.b1r, SkelGameSpecificResource.b1r, null),

    b2(SkelGameSpecificResource.b2, SkelGameSpecificResource.b2c, SkelGameSpecificResource.b2, SkelGameSpecificResource.b2, null),
    b2f(SkelGameSpecificResource.b2f, SkelGameSpecificResource.b2c, SkelGameSpecificResource.b2f, SkelGameSpecificResource.b2f, null),
    b2r(SkelGameSpecificResource.b2r, SkelGameSpecificResource.b2c, SkelGameSpecificResource.b2r, SkelGameSpecificResource.b2r, null),

    b3(SkelGameSpecificResource.b3, SkelGameSpecificResource.b3c, SkelGameSpecificResource.b3, SkelGameSpecificResource.b3, null),
    b3f(SkelGameSpecificResource.b3f, SkelGameSpecificResource.b3c, SkelGameSpecificResource.b3f, SkelGameSpecificResource.b3f, null),
    b3r(SkelGameSpecificResource.b3r, SkelGameSpecificResource.b3c, SkelGameSpecificResource.b3r, SkelGameSpecificResource.b3r, null),

    b4(SkelGameSpecificResource.b4, SkelGameSpecificResource.b4c, SkelGameSpecificResource.b4, SkelGameSpecificResource.b4, null),
    b4f(SkelGameSpecificResource.b4f, SkelGameSpecificResource.b4c, SkelGameSpecificResource.b4f, SkelGameSpecificResource.b4f, null),
    b4r(SkelGameSpecificResource.b4r, SkelGameSpecificResource.b4c, SkelGameSpecificResource.b4r, SkelGameSpecificResource.b4r, null),
    ;

    SkelGameButtonSkin(Res imgUp, Res imgDown, Res imgChecked, Res imgDisabled, Color buttonDisabledFontColor) {
        this.imgUp = imgUp;
        this.imgDown = imgDown;
        this.imgChecked = imgChecked;
        this.imgDisabled = imgDisabled;
        this.buttonDisabledFontColor = buttonDisabledFontColor;
    }

    private Res imgUp;
    private Res imgDown;
    private Res imgChecked;
    private Res imgDisabled;
    private Color buttonDisabledFontColor;

    @Override
    public Drawable getImgUp() {
        return GraphicUtils.getImage(imgUp).getDrawable();
    }

    @Override
    public Drawable getImgDown() {
        return GraphicUtils.getImage(imgDown).getDrawable();
    }

    @Override
    public Drawable getImgChecked() {
        return GraphicUtils.getImage(imgChecked).getDrawable();
    }

    @Override
    public Drawable getImgDisabled() {
        return GraphicUtils.getImage(imgDisabled).getDrawable();
    }

    @Override
    public Color getButtonDisabledFontColor() {
        return buttonDisabledFontColor;
    }
}
