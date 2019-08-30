package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.views.MainScreen.coin1IsPicked;
import static com.mygdx.game.views.MainScreen.coin2IsPicked;
import static com.mygdx.game.views.MainScreen.coin3IsPicked;
import static com.mygdx.game.views.MainScreen.coin4IsPicked;
import static com.mygdx.game.views.MainScreen.coin5IsPicked;
import static com.mygdx.game.views.MainScreen.coin6IsPicked;
import static com.mygdx.game.views.MainScreen.coin7IsPicked;
import static com.mygdx.game.views.MainScreen.coin8IsPicked;

public class InventoryScreen implements Screen {

    private MyGdxGame parent;
    private SpriteBatch spriteBatch;
    private Texture inventoryTexture;
    private Texture jellikTexture;
    private Stage stage;
    private Skin skin;
    private final int rows = 4;
    private final int columns = 4;
    private static boolean isSelectedFirst = false;
    private static ImageButton firstSelected;
    private Texture blueKey;
    private Texture redKey;
    private Texture greenKey;
    private Texture ingot;
    private int rowGap = 127, spaceX = 81, columnGap =100, spaceY = 162;
    private ImageButton[][] inventoryButtons;
    public static boolean bluekeyIsPicked = false;
    public static boolean redkeyIsPicked = false;
    public static boolean greenkeyIsPicked = false;

    public InventoryScreen(MyGdxGame main) {
        parent = main;
        spriteBatch = new SpriteBatch();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        stage = new Stage(new ScreenViewport());
        inventoryTexture = parent.assMan.manager.get("skin/invent.png");
        jellikTexture = parent.assMan.manager.get("images/Jellik.png");
        blueKey = parent.assMan.manager.get("images/blueKey.png");
        redKey = parent.assMan.manager.get("images/redKey.png");
        greenKey =  parent.assMan.manager.get("images/greenKey.png");
        ingot = parent.assMan.manager.get("images/ingot.png");

       // stage.setDebugAll(true);

        skin = parent.assMan.manager.get("skin/glassy-ui.json");



        inventoryButtons = new ImageButton[4][4];
        for (int i = 0; i < rows;i++){
            for (int j = 0; j < columns;j++){
                final ImageButton imageButton;
                if(j==0 && i ==0){

                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(redKey));
                     imageButton = new ImageButton(drawable);
                     imageButton.getImageCell().expand().fill();
                     imageButton.setVisible(false);
                }else if(j==1 && i ==0){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(blueKey));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==2 && i ==0){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(greenKey));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==3 && i ==0){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }//-----------------------------------------------
                else if(j==0 && i ==1){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==1 && i ==1){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==2 && i ==1){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==3 && i ==1){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }//-----------------------------------------------
                else if(j==0 && i ==2){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }else if(j==1 && i ==2){
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(ingot));
                    imageButton = new ImageButton(drawable);
                    imageButton.getImageCell().expand().fill();
                    imageButton.setVisible(false);
                }
                else{
                     imageButton = new ImageButton(skin);
                     imageButton.setVisible(false);
                }


                inventoryButtons[i][j] = imageButton;
                imageButton.setX(j*rowGap + spaceX);
                imageButton.setY(stage.getHeight()-i*columnGap-spaceY);
                imageButton.setHeight(93);
                imageButton.setWidth(93);


                imageButton.addListener(new DragListener(){
                    public void drag(InputEvent event, float x, float y, int pointer) {
                            imageButton.moveBy(x - imageButton.getWidth() / 2, y - imageButton.getHeight() / 2);
                    }
                    public void dragStop(InputEvent event, float x, float y, int pointer){
                        for (int k = 0;k<rows;k++) {
                            for (int h = 0;h<columns;h++){
                                if (imageButton.equals(inventoryButtons[k][h])){
                                    imageButton.setPosition(h*127 + 81,stage.getHeight()-k*100-162);
                                    break;
                                }
                            }
                        }
                    }
                });
                imageButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isSelectedFirst){
                            isSelectedFirst = false;
                            int firstX = getImageButtonX(firstSelected,inventoryButtons);
                            int firstY = getImageButtonY(firstSelected,inventoryButtons);
                            int secondX = getImageButtonX(imageButton,inventoryButtons);
                            int secondY = getImageButtonY(imageButton,inventoryButtons);
                            float firstSelectedX = firstSelected.getX();
                            float firstSelectedY = firstSelected.getY();
                            firstSelected.setPosition(imageButton.getX(),imageButton.getY());
                            imageButton.setPosition(firstSelectedX,firstSelectedY);
                            inventoryButtons[firstX][firstY] = imageButton;
                            inventoryButtons[secondX][secondY] = firstSelected;

                        }else{
                            firstSelected = imageButton;
                            isSelectedFirst = true;
                        }

                        super.clicked(event, x, y);
                    }
                });
                stage.addActor(imageButton);
            }
        }
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        changeScreenToGame();
        showInvetory();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        spriteBatch.begin();
        spriteBatch.draw(inventoryTexture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.draw();
    }

    private int getImageButtonX(ImageButton imageButton, ImageButton[][] inventoryButtons){
        for (int k = 0;k<rows;k++) {
            for (int h = 0;h<columns;h++){
                if (imageButton.equals(inventoryButtons[k][h])){

                    return h;
                }
            }
        }
        return 0;
    }
    private int getImageButtonY(ImageButton imageButton, ImageButton[][] inventoryButtons){
        for (int k = 0;k<rows;k++) {
            for (int h = 0;h<columns;h++){
                if (imageButton.equals(inventoryButtons[k][h])){
                    return k;
                }
            }
        }
        return 0;
    }

    public void showInvetory(){
        if(redkeyIsPicked){
            ImageButton imageButton = inventoryButtons[0][0];
            imageButton.setVisible(true);

        }
        if(bluekeyIsPicked){
            ImageButton imageButton = inventoryButtons[0][1];
            imageButton.setVisible(true);

        }
        if(greenkeyIsPicked){
            ImageButton imageButton = inventoryButtons[0][2];
            imageButton.setVisible(true);
        }
        if(coin1IsPicked){
            ImageButton imageButton = inventoryButtons[0][3];
            imageButton.setVisible(true);

        }//-----------------------------------------------
        if(coin2IsPicked){
            ImageButton imageButton = inventoryButtons[1][0];
            imageButton.setVisible(true);

        }
        if(coin3IsPicked){
            ImageButton imageButton = inventoryButtons[1][1];
            imageButton.setVisible(true);

        }
        if(coin4IsPicked){
            ImageButton imageButton = inventoryButtons[1][2];
            imageButton.setVisible(true);

        }
        if(coin5IsPicked){
            ImageButton imageButton = inventoryButtons[1][3];
            imageButton.setVisible(true);

        }//-----------------------------------------------
        if(coin6IsPicked){
            ImageButton imageButton = inventoryButtons[2][0];
            imageButton.setVisible(true);

        }
        if(coin7IsPicked){
            ImageButton imageButton = inventoryButtons[2][1];
            imageButton.setVisible(true);

        }
        if(coin8IsPicked){
            ImageButton imageButton = inventoryButtons[2][2];
            imageButton.setVisible(true);

        }

    }

    private void changeScreenToGame(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            parent.changeScreen(MyGdxGame.APPLICATION);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
