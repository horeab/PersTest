package libgdx.screens.model;

public class Question  {

	private String question;
	private int nr;
	private int response = -1;

	public Question(String question, int nr) {
		this.question = question;
		this.nr = nr;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

}
