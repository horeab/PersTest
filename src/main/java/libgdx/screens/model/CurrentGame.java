package libgdx.screens.model;

import java.util.ArrayList;
import java.util.List;

public class CurrentGame {

	private int currentQuestion;

	private List<Question> questions = new ArrayList<Question>();

	public CurrentGame() {
	}

	public CurrentGame(List<Question> questions) {
		this.questions = questions;
		this.currentQuestion = 0;
	}

	public void reset() {
		this.currentQuestion = 0;
	}

	public int getCurrentQuestionIndex() {
		return currentQuestion;
	}

	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Question getCurrentQuestion() {
		return getQuestions().get(getCurrentQuestionIndex());
	}
}
