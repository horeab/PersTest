package libgdx.resources;

import com.badlogic.gdx.graphics.Texture;

import libgdx.game.Game;

public enum Resource implements Res {

    // @formatter:off

    title_background("game/title_background.png", Texture.class),
    title_rays("game/title_rays.png", Texture.class),;

    //CAMPAIGN///////////////////////////////////////////////////////////////////////////////////////
    // @formatter:on

    private String path;
    private Class<?> classType;

    Resource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public String getPath() {
        return Game.getInstance().getAppInfoService().getResourcesFolder() + path;
    }

    public String getRawPath() {
        return path;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }


}
