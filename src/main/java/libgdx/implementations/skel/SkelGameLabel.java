package libgdx.implementations.skel;

import libgdx.game.Game;
import libgdx.resources.gamelabel.GameLabelUtils;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;

public enum SkelGameLabel implements libgdx.resources.gamelabel.GameLabel {
    agree,
    neutral,
    disagree,
    fact1label,
    fact2label,
    fact3label,
    fact4label,
    fact5label,
    fact1descr,
    fact2descr,
    fact3descr,
    fact4descr,
    fact5descr,
    infoText,
    infoBtn,
    startagain,
    infoTextPopup,;

    @Override
    public String getText(Object... params) {
        String language = Game.getInstance().getAppInfoService().getLanguage();
        return GameLabelUtils.getText(getKey(), language, SpecificPropertiesUtils.getLabelFilePath(), params);
    }

    @Override
    public String getKey() {
        return name().toLowerCase();
    }
}
