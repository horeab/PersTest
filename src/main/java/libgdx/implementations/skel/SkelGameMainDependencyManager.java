package libgdx.implementations.skel;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import libgdx.constants.GameIdEnum;
import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.controls.popup.RatingService;
import libgdx.game.MainDependencyManager;
import libgdx.game.ScreenManager;
import libgdx.resources.Resource;
import libgdx.resources.ResourceService;
import libgdx.screens.AbstractScreen;
import libgdx.screens.service.QuestionService;
import libgdx.transactions.TransactionsService;
import libgdx.utils.Utils;

public class SkelGameMainDependencyManager extends MainDependencyManager<ScreenManager, AbstractScreen, SkelGameLabel, Resource, GameIdEnum> {

    @Override
    public Class<Resource> getMainResourcesClass() {
        return Resource.class;
    }

    @Override
    public Class<SkelGameLabel> getGameLabelClass() {
        return SkelGameLabel.class;
    }

    @Override
    public Class<GameIdEnum> getGameIdClass() {
        return GameIdEnum.class;
    }

    @Override
    public ResourceService createResourceService() {
        return new SkelGameResourceService();
    }

    @Override
    public String getExtraFontChars() {
        List<String> allQuestions = new QuestionService().allQuestions();
        return Utils.getStringLetters(allQuestions);
    }

    @Override
    public RatingService createRatingService(AbstractScreen abstractScreen) {
        return new SkelGameRatingService(abstractScreen);
    }

    @Override
    public ScreenManager createScreenManager() {
        return new ScreenManager();
    }

    @Override
    public InventoryTableBuilderCreator createInventoryTableBuilderCreator() {
        throw new RuntimeException("Transactions not implemented for Game");
    }

    @Override
    public TransactionsService getTransactionsService() {
        throw new RuntimeException("Transactions not implemented for Game");
    }
}
