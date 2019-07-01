package libgdx.screens;

import java.util.Arrays;

import libgdx.screen.ScreenType;
import libgdx.screens.mainmenu.GameOverScreen;
import libgdx.screens.mainmenu.MainMenuScreen;

public enum ScreenTypeEnum implements ScreenType {

    MAIN_MENU_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    GAME_OVER_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new GameOverScreen(Arrays.asList());
        }
    },

}