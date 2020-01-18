package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.omg.CORBA.INITIALIZE;

import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MainButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfig;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.controls.popup.ProVersionPopup;
import libgdx.dbapi.GameStatsDbApiService;
import libgdx.game.Game;
import libgdx.graphics.GraphicUtils;
import libgdx.implementations.skel.SkelGameButtonSize;
import libgdx.implementations.skel.SkelGameButtonSkin;
import libgdx.implementations.skel.SkelGameLabel;
import libgdx.implementations.skel.SkelGameRatingService;
import libgdx.implementations.skel.SkelGameSpecificResource;
import libgdx.resources.FontManager;
import libgdx.resources.MainResource;
import libgdx.resources.ResourcesManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.screens.AbstractScreen;
import libgdx.screens.model.CurrentGame;
import libgdx.screens.model.Question;
import libgdx.screens.service.QuestionService;
import libgdx.screens.service.StateManager;
import libgdx.utils.DateUtils;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.Utils;
import libgdx.utils.model.FontColor;

public class MainMenuScreen extends AbstractScreen {

    private CurrentGame currentGame;
    private StateManager stateManager;

    private final static String MAIN_TABLE_NAME = "MAIN_TABLE_NAME";
    private final static String LEFT_ARROW = "LEFT_ARROW";
    private final static String RIGHT_ARROW = "RIGHT_ARROW";

    @Override
    public void buildStage() {
        stateManager = new StateManager();
        initCurrentGameWithStateManager();
        new SkelGameRatingService(this).appLaunched();
    }

    private void createScreen(Question question) {
        if (Game.getInstance().getCurrentUser() != null) {
            new GameStatsDbApiService().incrementGameStatsQuestionsWon(Game.getInstance().getCurrentUser().getId(), Long.valueOf(DateUtils.getNowMillis()).toString());
        }
        Table allTable = new Table();
        allTable.setName(MAIN_TABLE_NAME);
        allTable.setFillParent(true);
        int screenWidth = ScreenDimensionsManager.getScreenWidth();
        Table headerTable = createHeaderTable();
        allTable.add(headerTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(20)).row();
        Table questionTable = createQuestionTable(question.getQuestion());
        allTable.add(questionTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(40)).row();
        Table answersTable = createAnswersButtons();
        allTable.add(answersTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(40));
        addActor(allTable);
        initBackForwardBtn();
    }

    private void initCurrentGameWithStateManager() {
        currentGame = new CurrentGame(new QuestionService().getQuestions());
        currentGame.setCurrentQuestion(stateManager.getCurrentQuestion());
        for (Question question : currentGame.getQuestions()) {
            if (stateManager.getResponse(question.getNr()) != -1) {
                question.setResponse(stateManager.getResponse(question.getNr()));
            }
        }
        createScreen(currentGame.getCurrentQuestion());
    }

    private void saveCurrentState() {
        stateManager.putCurrentQuestion(currentGame.getCurrentQuestionIndex());
        for (Question question : currentGame.getQuestions()) {
            if (question.getResponse() != -1)
                stateManager.putResponse(question.getNr(), question.getResponse());
        }
    }


    private boolean isGameOver(int qNr) {
        return qNr > currentGame.getQuestions().size() - 1;
    }

    private void startNewGame() {
        stateManager.reset(currentGame.getQuestions().size());
        currentGame = new CurrentGame(new QuestionService().getQuestions());
        currentGame.setCurrentQuestion(stateManager.getCurrentQuestion());
        refreshLevel();
    }

    private void goToLevel(final int qNr) {
        currentGame.setCurrentQuestion(qNr);
        if (qNr == 13 && !Utils.isValidExtraContent()) {
            new ProVersionPopup(Game.getInstance().getAbstractScreen()).addToPopupManager();
        } else if (qNr == 30 || qNr == 46) {
            Game.getInstance().getAppInfoService().showPopupAd(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        if (isGameOver(qNr)) {
            goToGameOver();
        } else {
            refreshLevel();
        }

    }

    private void refreshLevel() {
        Group root = Game.getInstance().getAbstractScreen().getStage().getRoot();
        root.findActor(MAIN_TABLE_NAME).remove();
        createScreen(currentGame.getCurrentQuestion());

        initBackForwardBtn();
    }

    private void clickAnswer(final MyButton btn, final int anwserNr) {
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                answerClick(anwserNr);
            }
        });
    }

    private void answerClick(int answerNr) {
        currentGame.getCurrentQuestion().setResponse(answerNr);
        goToLevel(currentGame.getCurrentQuestionIndex() + 1);
    }

    private void initBackForwardBtn() {
        Group root = Game.getInstance().getAbstractScreen().getStage().getRoot();
        Image leftArrow = root.findActor(LEFT_ARROW);
        Image rightArrow = root.findActor(RIGHT_ARROW);
        leftArrow.setVisible(true);
        rightArrow.setVisible(true);
        if (currentGame.getCurrentQuestionIndex() == 0) {
            leftArrow.setVisible(false);
        }
        if (currentGame.getCurrentQuestionIndex() == currentGame.getQuestions().size() - 1 || currentGame.getCurrentQuestion().getResponse() == -1) {
            rightArrow.setVisible(false);
        }
        clickDirection(leftArrow, currentGame.getCurrentQuestionIndex() - 1);
        clickDirection(rightArrow, currentGame.getCurrentQuestionIndex() + 1);
    }

    private void clickDirection(Image btn, final int qNr) {
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(qNr);
            }
        });
    }

    private void goToGameOver() {
        screenManager.showGameOver(currentGame.getQuestions());
    }

    private Table createHeaderTable() {
        Table table = new Table();
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        if (!Utils.isValidExtraContent()) {
            final Image mug = GraphicUtils.getImage(MainResource.mug);
            float mugDimen = dimen * 4;
            mug.setWidth(mugDimen);
            mug.setHeight(mugDimen);
            new ActorAnimation(mug, Game.getInstance().getAbstractScreen()).animateZoomInZoomOut();
            table.add(mug).padLeft(dimen).padBottom(dimen).width(mugDimen).height(mugDimen);
            mug.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameOverScreen.displayInAppPurchasesPopup(new Runnable() {
                        @Override
                        public void run() {
                            refreshLevel();
                        }
                    });
                }
            });
        }


        float btnFontScale = FontManager.calculateMultiplierStandardFontSize(1.6f);
        MyButton newGame = new ButtonBuilder().setSingleLineText(SkelGameLabel.startagain.getText(), btnFontScale).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startNewGame();
            }

        });
        MyButton info = new ButtonBuilder().setSingleLineText(SkelGameLabel.infoBtn.getText(), btnFontScale).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
        final AbstractScreen screen = this;
        info.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyPopup popup = popup();
                popup.addToPopupManager();
            }

            private MyPopup popup() {
                return new MyPopup(screen) {
                    @Override
                    protected void addButtons() {
                        MyButton okButton = new ButtonBuilder("OK", FontManager.getBigFontDim()).setButtonSkin(MainButtonSkin.DEFAULT)
                                .setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
                        okButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                hide();
                            }
                        });
                        addButton(okButton);
                    }

                    @Override
                    protected String getLabelText() {
                        return SkelGameLabel.infoTextPopup.getText();
                    }

                    @Override
                    protected MyWrappedLabel getLabel() {
                        MyWrappedLabel label = super.getLabel();
                        label.getLabels().get(0).getStyle().font.getData().markupEnabled = true;
                        return label;
                    }

                    @Override
                    protected MyWrappedLabelConfigBuilder getInfoLabelConfigBuilder() {
                        return super.getInfoLabelConfigBuilder().setFontScale(FontManager.getBigFontDim());
                    }

                    @Override
                    public float getPrefWidth() {
                        return ScreenDimensionsManager.getScreenWidthValue(80);
                    }
                };
            }

        });
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(((currentGame.getCurrentQuestionIndex() + 1) + "/" + currentGame.getQuestions().size())).setFontScale(FontManager.calculateMultiplierStandardFontSize(2.7f)).setSingleLineLabel().build())).pad(dimen);
        table.add().growX();
        table.add(newGame).width(newGame.getWidth() * 1.7f).height(newGame.getHeight());
        table.add(info).pad(dimen).width(info.getWidth()).height(info.getHeight());

        return table;
    }

    private Table createQuestionTable(String question) {
        Table table = new Table();
        Image image1 = GraphicUtils.getImage(SkelGameSpecificResource.leftarrow);
        image1.setName(LEFT_ARROW);
        Image image2 = GraphicUtils.getImage(SkelGameSpecificResource.rightarrow);
        image2.setName(RIGHT_ARROW);
        SkelGameButtonSize navButton = SkelGameButtonSize.NAV_BUTTON;
        table.add(image1).width(ScreenDimensionsManager.getScreenWidthValue(8)).width(navButton.getWidth()).height(navButton.getHeight());
        float questionsWidth = ScreenDimensionsManager.getScreenWidthValue(75);
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setWidth(questionsWidth).setFontScale(getFontScale(question))
                .setText(question).build()))
                .width(questionsWidth);
        table.add(image2).width(ScreenDimensionsManager.getScreenWidthValue(8)).width(navButton.getWidth()).height(navButton.getHeight());
        return table;
    }

    private float getFontScale(String question) {
        return FontManager.calculateMultiplierStandardFontSize(question.length() > 40 ? 3f : 4f);
    }

    private Table createAnswersButtons() {
        Table table = new Table();
        int totalButtons = 5;
        int screenWidth = ScreenDimensionsManager.getScreenWidth();
        float btnSideDimen = screenWidth / totalButtons;
        float infoSideDimen = screenWidth / 3;
        float horizontalGeneralMarginDimen = MainDimen.horizontal_general_margin.getDimen();
        btnSideDimen = btnSideDimen - horizontalGeneralMarginDimen * 2;
        Table infoTable = new Table();
        infoTable.add(new MyWrappedLabel(getAnswerInfoLabel(SkelGameLabel.disagree.getText()))).width(infoSideDimen);
        infoTable.add(new MyWrappedLabel(getAnswerInfoLabel(SkelGameLabel.neutral.getText()))).width(infoSideDimen);
        infoTable.add(new MyWrappedLabel(getAnswerInfoLabel(SkelGameLabel.agree.getText()))).width(infoSideDimen);
        table.add(infoTable).width(screenWidth).row();
        Table btnTable = new Table();
        for (int i = 0; i < totalButtons; i++) {
            MyButton btn = new ButtonBuilder("").setButtonSkin(getAnswerSkin(i)).setFixedButtonSize(SkelGameButtonSize.NAV_BUTTON).build();
            btnTable.add(btn).pad(horizontalGeneralMarginDimen).width(btnSideDimen).height(btnSideDimen);
            clickAnswer(btn, i);
        }
        table.add(btnTable);
        return table;
    }

    private SkelGameButtonSkin getAnswerSkin(int i) {
        String suffix = "";
        if (currentGame.getCurrentQuestion().getResponse() != -1) {
            suffix = "f";
            if (currentGame.getCurrentQuestion().getResponse() == i) {
                suffix = "r";
            }
        }
        return SkelGameButtonSkin.valueOf("b" + i + suffix);
    }

    private MyWrappedLabelConfig getAnswerInfoLabel(String text) {
        return new MyWrappedLabelConfigBuilder().setTextColor(FontColor.GRAY).setFontScale(FontManager.calculateMultiplierStandardFontSize(2f)).setText(text).build();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Utils.createChangeLangPopup();
    }

    @Override
    public void onBackKeyPress() {
        saveCurrentState();
        Gdx.app.exit();
    }

}
