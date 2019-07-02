package libgdx.screens.service;

import libgdx.preferences.PreferencesService;

public class StateManager {

	private static String SHARED_PREFS_NAME = "lastState";
	private PreferencesService preferencesService = new PreferencesService(SHARED_PREFS_NAME);

	public static String CURRENT_QUESTION = "CURRENT_QUESTION";
	public static String QUESTION_ANSWER = "QUESTION_ANSWER";
	public static String INTRO_SHOWN = "INTRO_SHOWN";


	public int getCurrentQuestion() {
		return preferencesService.getPreferences().getInteger(CURRENT_QUESTION, 0);
	}

	public void putCurrentQuestion(int currentQuestion) {
		putValue(CURRENT_QUESTION, currentQuestion);
	}

	public void putResponse(int question, int response) {
		putValue(QUESTION_ANSWER + question, response);
	}

	public int getResponse(int question) {
		return preferencesService.getPreferences().getInteger(QUESTION_ANSWER + question, -1);
	}

	public boolean isIntroShown() {
		return preferencesService.getPreferences().getBoolean(INTRO_SHOWN, false);
	}

	public void putIntroShown() {
		putValue(INTRO_SHOWN, true);
	}

	private void putValue(String fieldName, int value) {
		preferencesService.putInteger(fieldName, value);
	}

	private void putValue(String fieldName, boolean value) {
		preferencesService.putBoolean(fieldName, value);
	}

	public void reset(int maxQNr) {
		putCurrentQuestion(0);
		for (int i = 0; i < maxQNr; i++) {
			putResponse(i, -1);
		}
	}
}
