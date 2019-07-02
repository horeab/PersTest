package libgdx.campaign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import libgdx.implementations.skel.SkelGame;
import libgdx.utils.EnumUtils;

public class QuestionConfig {

    private List<String> l = new ArrayList<>();
    private List<String> c = new ArrayList<>();

    public QuestionConfig() {
        this(new ArrayList<QuestionDifficulty>(Arrays.asList(EnumUtils.getValues((SkelGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum())))),
                new ArrayList<QuestionCategory>(Arrays.asList(EnumUtils.getValues(SkelGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum()))));
    }

    public QuestionConfig(QuestionCategory questionCategory) {
        this(Collections.singletonList(questionCategory));
    }

    public QuestionConfig(QuestionDifficulty questionDifficulty) {
        this(Collections.singletonList(questionDifficulty), new ArrayList<QuestionCategory>(Arrays.asList(EnumUtils.getValues(SkelGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum()))));
    }

    public QuestionConfig(QuestionDifficulty QuestionDifficulty, QuestionCategory questionCategory) {
        this(Collections.singletonList(QuestionDifficulty), Collections.singletonList(questionCategory));
    }


    public QuestionConfig(List<QuestionCategory> questionCategory) {
        this(new ArrayList<QuestionDifficulty>(Arrays.asList(EnumUtils.getValues(SkelGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum()))), questionCategory);
    }

    public QuestionConfig(QuestionDifficulty QuestionDifficulty, List<QuestionCategory> questionCategory) {
        this(Collections.singletonList(QuestionDifficulty), questionCategory);
    }

    private QuestionConfig(List<QuestionDifficulty> questionDifficulty, List<QuestionCategory> questionCategory) {
        for (QuestionDifficulty item : questionDifficulty) {
            this.l.add(item.name());
        }
        for (QuestionCategory item : questionCategory) {
            this.c.add(item.name());
        }
    }

    public List<String> getQuestionDifficultyStringList() {
        return l;
    }

    public List<String> getQuestionCategoryStringList() {
        return c;
    }

    public RandomCategoryAndDifficulty getRandomCategoryAndDifficulty() {
        QuestionDifficulty randomQuestionDifficulty = getRandomQuestionDifficulty();
        List<QuestionCategory> categories = new QuestionConfigFileHandler().getQuestionCategoriesForDifficulty(randomQuestionDifficulty);
        Collections.shuffle(categories);
        for (QuestionCategory category : new ArrayList<>(categories)) {
            if (!c.contains(category.name())) {
                categories.remove(category);
            }
        }
        return new RandomCategoryAndDifficulty(categories.get(0), randomQuestionDifficulty);
    }

    private QuestionDifficulty getRandomQuestionDifficulty() {
        ArrayList<String> list = new ArrayList<String>(l);
        Collections.shuffle(list);
        return (QuestionDifficulty) EnumUtils.getEnumValue(SkelGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum(), list.get(0));
    }

    private QuestionCategory getRandomQuestionCategory() {
        ArrayList<String> list = new ArrayList<String>(c);
        Collections.shuffle(list);
        return (QuestionCategory) EnumUtils.getEnumValue(SkelGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum(), list.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionConfig that = (QuestionConfig) o;
        return Objects.equals(l, that.l) &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, c);
    }
}
