package libgdx.implementations.skel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.game.Game;
import libgdx.resources.SpecificResource;

public enum SkelGameSpecificResource implements SpecificResource {

    // @formatter:off

    questions("questions/questions", I18NBundle.class),
    specific_labels("labels/labels", I18NBundle.class),
    i1("background/i1.png", Texture.class),
    i2("background/i2.png", Texture.class),
    i3("background/i3.png", Texture.class),
    i4("background/i4.png", Texture.class),
    i5("background/i5.png", Texture.class),
    leftarrow("buttons/leftarrow.png", Texture.class),
    rightarrow("buttons/rightarrow.png", Texture.class),
    info("info.png", Texture.class),

    b0("buttons/b0.png", Texture.class),
    b0c("buttons/b0c.png", Texture.class),
    b0f("buttons/b0f.png", Texture.class),
    b0r("buttons/b0r.png", Texture.class),
    b1("buttons/b1.png", Texture.class),
    b1c("buttons/b1c.png", Texture.class),
    b1f("buttons/b1f.png", Texture.class),
    b1r("buttons/b1r.png", Texture.class),
    b2("buttons/b2.png", Texture.class),
    b2c("buttons/b2c.png", Texture.class),
    b2f("buttons/b2f.png", Texture.class),
    b2r("buttons/b2r.png", Texture.class),
    b3("buttons/b3.png", Texture.class),
    b3c("buttons/b3c.png", Texture.class),
    b3f("buttons/b3f.png", Texture.class),
    b3r("buttons/b3r.png", Texture.class),
    b4("buttons/b4.png", Texture.class),
    b4c("buttons/b4c.png", Texture.class),
    b4f("buttons/b4f.png", Texture.class),
    b4r("buttons/b4r.png", Texture.class),

    empty_backr("background/empty_backr.png", Texture.class),;
    // @formatter:on

    private String path;
    private Class<?> classType;

    SkelGameSpecificResource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String getPath() {
        return Game.getInstance().getAppInfoService().getImplementationGameResourcesFolder() + path;
    }

}
