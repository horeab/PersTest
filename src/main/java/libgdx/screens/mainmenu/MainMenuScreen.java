package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MainButtonSize;
import libgdx.controls.button.MainButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfig;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.graphics.GraphicUtils;
import libgdx.implementations.iq.SkelGameButtonSize;
import libgdx.implementations.iq.SkelGameButtonSkin;
import libgdx.implementations.iq.SkelGameLabel;
import libgdx.implementations.iq.SkelGameRatingService;
import libgdx.implementations.iq.SkelGameSpecificResource;
import libgdx.resources.FontManager;
import libgdx.resources.MainResource;
import libgdx.resources.ResourcesManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.screens.AbstractScreen;
import libgdx.utils.ScreenDimensionsManager;

public class MainMenuScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        new SkelGameRatingService(this).appLaunched();
        Table allTable = new Table();
        allTable.setFillParent(true);
        int screenWidth = ScreenDimensionsManager.getScreenWidth();
        Table headerTable = createHeaderTable();
        allTable.add(headerTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(20)).row();
        Table questionTable = createQuestionTable();
        allTable.add(questionTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(40)).row();
        Table answersTable = createAnswersButtons();
        allTable.add(answersTable).width(screenWidth).height(ScreenDimensionsManager.getScreenHeightValue(40));
        addActor(allTable);
    }

    private Table createHeaderTable() {
        Table table = new Table();
        float btnFontScale = FontManager.calculateMultiplierStandardFontSize(1.6f);
        MyButton newGame = new ButtonBuilder("Start again", btnFontScale).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }

        });
        MyButton info = new ButtonBuilder("Info", btnFontScale).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
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
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(("13/42")).setFontScale(FontManager.calculateMultiplierStandardFontSize(2.7f)).setSingleLineLabel().build())).pad(dimen);
        table.add().growX();
        table.add(newGame).width(newGame.getWidth()).height(newGame.getHeight());
        table.add(info).pad(dimen).width(info.getWidth()).height(info.getHeight());
        return table;
    }

    private Table createQuestionTable() {
        Table table = new Table();
        Image image1 = GraphicUtils.getImage(SkelGameSpecificResource.leftarrow);
        Image image2 = GraphicUtils.getImage(SkelGameSpecificResource.rightarrow);
        SkelGameButtonSize navButton = SkelGameButtonSize.NAV_BUTTON;
        table.add(image1).width(ScreenDimensionsManager.getScreenWidthValue(8)).width(navButton.getWidth()).height(navButton.getHeight());
        float questionsWidth = ScreenDimensionsManager.getScreenWidthValue(75);
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setWidth(questionsWidth).setFontScale(FontManager.calculateMultiplierStandardFontSize(4f))
                .setText("I pay attention to details").build()))
                .width(questionsWidth);
        table.add(image2).width(ScreenDimensionsManager.getScreenWidthValue(8)).width(navButton.getWidth()).height(navButton.getHeight());
        return table;
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
            MyButton btn = new ButtonBuilder("").setButtonSkin(SkelGameButtonSkin.valueOf("b" + i)).setFixedButtonSize(SkelGameButtonSize.NAV_BUTTON).build();
            btnTable.add(btn).pad(horizontalGeneralMarginDimen).width(btnSideDimen).height(btnSideDimen);
        }
        table.add(btnTable);
        return table;
    }

    private MyWrappedLabelConfig getAnswerInfoLabel(String text) {
        return new MyWrappedLabelConfigBuilder().setTextStyle(ResourcesManager.getLabelGrey()).setFontScale(FontManager.calculateMultiplierStandardFontSize(2f)).setText(text).build();
    }

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }

}
