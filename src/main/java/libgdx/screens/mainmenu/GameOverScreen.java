package libgdx.screens.mainmenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MainButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.BackButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.game.Game;
import libgdx.graphics.GraphicUtils;
import libgdx.implementations.skel.SkelGameButtonSize;
import libgdx.implementations.skel.SkelGameLabel;
import libgdx.implementations.skel.SkelGameSpecificResource;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.screens.AbstractScreen;
import libgdx.screens.SkelDimen;
import libgdx.screens.model.Question;
import libgdx.screens.service.QuestionService;
import libgdx.utils.ActorPositionManager;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.Utils;
import libgdx.utils.model.RGBColor;

public class GameOverScreen extends AbstractScreen {

    private static int MAX_VAL = 40;

    private List<Question> questions;
    private QuestionService questionService;

    public GameOverScreen(List<Question> questions) {
        this.questions = questions;
        this.questionService = new QuestionService();
    }

    @Override
    public void buildStage() {
        setBackgroundColor(RGBColor.WHITE);
        Table table = new Table();
        table.setFillParent(true);
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            MyButton backButton = new BackButtonBuilder().createScreenBackButton(this);
            backButton.setX(MainDimen.horizontal_general_margin.getDimen());
            backButton.setY(ScreenDimensionsManager.getScreenHeight() - MainDimen.vertical_general_margin.getDimen() - backButton.getHeight());
            addActor(backButton);
        }
        Table infosTable = createInfosTable();
        table.add(infosTable).width(infosTable.getWidth()).height(infosTable.getHeight()).row();
        ActorPositionManager.setActorCenterHorizontalOnScreen(infosTable);
        addActor(table);
    }

    public static void displayInAppPurchasesPopup(Runnable redirectAfterBoughtScreen) {
        Game.getInstance().getInAppPurchaseManager().displayInAppPurchasesPopup(MainGameLabel.l_extracontent.getText(), redirectAfterBoughtScreen);
    }

    private Table createInfoIconTable() {
        Table table = new Table();
        Image icon = GraphicUtils.getImage(SkelGameSpecificResource.info);
        float dimen = SkelDimen.info_icon.getDimen();
        table.add().growX();
        table.add(icon).padRight(MainDimen.horizontal_general_margin.getDimen()).align(Align.right).width(dimen).height(dimen);
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setSingleLineLabel().setFontScale(FontManager.getBigFontDim())
                .setText(SkelGameLabel.infoText.getText()).build()));
        table.add().growX();
        return table;
    }

    private Table createInfosTable() {

        int e = questionService.calculateE(questions);
        int a = questionService.calculateA(questions);
        int c = questionService.calculateC(questions);
        int n = questionService.calculateN(questions);
        int o = questionService.calculateO(questions);

        Table table = new Table();
        addInfoTable(table, e, 1);
        addInfoTable(table, a, 2);
        addInfoTable(table, c, 3);
        addInfoTable(table, n, 4);
        addInfoTable(table, o, 5);
        Table infoIconTable = createInfoIconTable();
        table.add(infoIconTable).width(infoIconTable.getWidth()).padTop(MainDimen.vertical_general_margin.getDimen() * 2).height(infoIconTable.getHeight()).row();
        return table;
    }

    private void addInfoTable(Table table, int score, int index) {
        Table infoTable1 = createInfoTable(SkelGameLabel.valueOf("fact" + index + "label").getText(), SkelGameLabel.valueOf("fact" + index + "descr").getText(), score, index);
        table.add(infoTable1).width(infoTable1.getWidth()).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
    }

    private Table createInfoTable(String btnText, final String descr, int score, int index) {
        float allWidth = ScreenDimensionsManager.getScreenWidthValue(40);
        float percent = (float) score / MAX_VAL * 100;
        float fullScoreTableWidth = percent / 100 * allWidth;

        Table infoTable = new Table();
        MyButton btn = new ButtonBuilder().setSingleLineText(btnText, FontManager.getBigFontDim()).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.INFO_BUTTON).build();
        infoTable.add(btn).width(btn.getWidth()).height(btn.getHeight());
        final AbstractScreen screen = this;
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                final MyPopup popup = popup();
                if (Utils.isValidExtraContent()) {
                    popup.addToPopupManager();
                } else {
                    displayInAppPurchasesPopup(new Runnable() {
                        @Override
                        public void run() {
                            popup.addToPopupManager();
                        }
                    });
                }
            }

            private MyPopup popup() {
                return new MyPopup(screen) {
                    @Override
                    protected void addButtons() {
                        MyButton okButton = new ButtonBuilder("OK", FontManager.getBigFontDim()).setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.HEADER_BUTTON).build();
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
                        return descr;
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
                        return ScreenDimensionsManager.getScreenWidthValue(70);
                    }
                };
            }

        });
        Table emptyScoreTable = new Table();
        emptyScoreTable.setBackground(GraphicUtils.getNinePatch(SkelGameSpecificResource.empty_backr));
        Table fullScoreTable = new Table();
        fullScoreTable.setBackground(GraphicUtils.getNinePatch(SkelGameSpecificResource.valueOf("i" + index)));
        infoTable.add(fullScoreTable).width(fullScoreTableWidth);
        infoTable.add(emptyScoreTable).width(allWidth - fullScoreTableWidth);
        return infoTable;
    }

    @Override
    public void onBackKeyPress() {
        Game.getInstance().getScreenManager().showMainScreen();
    }

}
