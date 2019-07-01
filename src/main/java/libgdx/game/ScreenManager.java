package libgdx.game;

import libgdx.campaign.CampaignLevel;
import libgdx.campaign.LettersCampaignLevelEnum;
import libgdx.screen.AbstractScreenManager;
import libgdx.screens.ScreenTypeEnum;

public class ScreenManager extends AbstractScreenManager {

    @Override
    public void showMainScreen() {
        showScreen(ScreenTypeEnum.MAIN_MENU_SCREEN);
//        showGameOver(213);
    }

    public void showGameOver(int finalScore) {
        showScreen(ScreenTypeEnum.GAME_OVER_SCREEN, finalScore);
    }
}
