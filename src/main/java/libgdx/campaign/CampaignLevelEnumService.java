package libgdx.campaign;

import libgdx.implementations.skel.SkelGame;
import libgdx.implementations.skel.SkelGameButtonSkin;
import libgdx.resources.Resource;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;
import libgdx.utils.EnumUtils;


public class CampaignLevelEnumService {

    private CampaignStoreService campaignStoreService = new CampaignStoreService();
    private CampaignLevel campaignLevel;

    public CampaignLevelEnumService(CampaignLevel campaignLevel) {
        this.campaignLevel = campaignLevel;
    }

    public String getLabelText() {
        return getCategory() != null ? new SpecificPropertiesUtils().getQuestionCategoryLabel(getCategory()) : null;
    }

    public Resource getBackgroundTexture() {
        return EnumUtils.getEnumValue(Resource.class, "campaign_level_" + getDifficulty() + "_background");
    }

    public SkelGameButtonSkin getButtonSkin() {
        return EnumUtils.getEnumValue(SkelGameButtonSkin.class, "CAMPAIGN_LEVEL_" + getCategory());
    }

    public QuestionConfig getQuestionConfig() {
        QuestionDifficulty difficulty = (QuestionDifficulty) EnumUtils.getEnumValue(SkelGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum(), "_" + getDifficulty());
        QuestionConfig questionConfig;
        if (getCategory() != null) {
            QuestionCategory category = (QuestionCategory) EnumUtils.getEnumValue(SkelGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum(), "CAT" + getCategory());
            questionConfig = new QuestionConfig(difficulty, category);
        } else {
            questionConfig = new QuestionConfig(difficulty);
        }


        return questionConfig;
    }

    public GameTypeStage getGameTypeStage() {
        Integer category = getCategory();
        if (category != null && category > 4) {
            category = category - 5 * getDifficulty();
        }
        return EnumUtils.getEnumValue(GameTypeStage.class, "CAMPAIGN_LEVEL_" + getDifficulty() + (category != null ? "_" + category : ""));
    }

    private int getDifficulty() {
        return Integer.valueOf(campaignLevel.getName().split("_")[1]);
    }

    public Integer getCategory() {
        return getCategory(campaignLevel);
    }

    private static String[] getSplit(CampaignLevel campaignLevel) {
        return campaignLevel.getName().split("_");
    }

    private static Integer getCategory(CampaignLevel campaignLevel) {
        String[] split = getSplit(campaignLevel);
        return split.length == 3 ? Integer.valueOf(split[2]) : null;
    }

}
