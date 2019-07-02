package libgdx.screens;

import java.util.Arrays;
import java.util.List;

import libgdx.screen.ScreenType;
import libgdx.screens.mainmenu.GameOverScreen;
import libgdx.screens.mainmenu.MainMenuScreen;
import libgdx.screens.model.Question;

public enum ScreenTypeEnum implements ScreenType {

    MAIN_MENU_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    GAME_OVER_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new GameOverScreen((List<Question>) params[0]);
        }
    },

}