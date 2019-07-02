package libgdx.game;

import java.util.List;

import libgdx.campaign.CampaignLevel;
import libgdx.campaign.LettersCampaignLevelEnum;
import libgdx.screen.AbstractScreenManager;
import libgdx.screens.ScreenTypeEnum;
import libgdx.screens.model.Question;

public class ScreenManager extends AbstractScreenManager {

    @Override
    public void showMainScreen() {
        showScreen(ScreenTypeEnum.MAIN_MENU_SCREEN);
//        showGameOver(213);
    }

    public void showGameOver(List<Question> questions) {
        showScreen(ScreenTypeEnum.GAME_OVER_SCREEN, questions);
    }
}
