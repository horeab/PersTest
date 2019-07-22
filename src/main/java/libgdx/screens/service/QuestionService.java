package libgdx.screens.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import libgdx.campaign.QuestionCategory;
import libgdx.campaign.QuestionDifficulty;
import libgdx.game.Game;
import libgdx.screens.model.Question;

public class QuestionService {

    public List<Question> getQuestions() {
        List<String> questions = allQuestions();
        List<Question> result = new ArrayList<Question>();
        int i = 0;
        for (String s : questions) {
            Question question = new Question(s, i);
            result.add(question);
            i++;
        }
        return result;
    }

    public List<String> allQuestions() {
        List<String> set = new ArrayList<>();
        Scanner scanner = new Scanner(Gdx.files.internal(getQuestionFilePath()).readString());
        while (scanner.hasNextLine()) {
            java.lang.String line = scanner.nextLine();
            set.add(line);
        }
        scanner.close();
        return set;
    }

    private String getQuestionFilePath() {
        return Game.getInstance().getAppInfoService().getImplementationGameResourcesFolder() + "questions/" + Game.getInstance().getAppInfoService().getLanguage() + "_questions.txt";
    }

    public int calculateE(List<Question> questions) {
        int r = 20 + getResponse(0, questions) - getResponse(5, questions) + getResponse(10, questions) - getResponse(15, questions)
                + getResponse(20, questions) - getResponse(25, questions) + getResponse(30, questions) - getResponse(35, questions)
                + getResponse(40, questions) - getResponse(45, questions);
        return r;
    }

    public int calculateA(List<Question> questions) {
        int r = 14 - getResponse(1, questions) + getResponse(6, questions) - getResponse(11, questions) + getResponse(16, questions)
                - getResponse(21, questions) + getResponse(26, questions) - getResponse(31, questions) + getResponse(36, questions)
                + getResponse(41, questions) + getResponse(46, questions);
        return r;
    }

    public int calculateC(List<Question> questions) {
        int r = 14 + getResponse(2, questions) - getResponse(7, questions) + getResponse(12, questions) - getResponse(17, questions)
                + getResponse(22, questions) - getResponse(27, questions) + getResponse(32, questions) - getResponse(37, questions)
                + getResponse(42, questions) + getResponse(47, questions);
        return r;
    }

    public int calculateN(List<Question> questions) {
        int r = 38 - getResponse(3, questions) + getResponse(8, questions) - getResponse(13, questions) + getResponse(18, questions)
                - getResponse(23, questions) - getResponse(28, questions) - getResponse(33, questions) - getResponse(38, questions)
                - getResponse(43, questions) - getResponse(48, questions);
        return r;
    }

    public int calculateO(List<Question> questions) {
        int r = 8 + getResponse(4, questions) - getResponse(9, questions) + getResponse(14, questions) - getResponse(19, questions)
                + getResponse(24, questions) - getResponse(29, questions) + getResponse(34, questions) + getResponse(39, questions)
                + getResponse(44, questions) + getResponse(49, questions);
        return r;
    }

    private int getResponse(int questionNr, List<Question> questions) {
        return questions.get(questionNr).getResponse() + 1;
    }
}
