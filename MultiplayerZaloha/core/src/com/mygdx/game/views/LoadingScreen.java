package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class LoadingScreen implements Screen {
    private MyGdxGame parent;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    public final int SOUND = 0;
    public final int MUSIC = 1;
    private BitmapFont font;

    private int currentLoadingStage = 0;

    public float countDown = 1f;
    public float stateTime = 0f;



    public LoadingScreen(MyGdxGame main){
        parent = main;
        spriteBatch = new SpriteBatch();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        //TODO stahnout font
        //font = new BitmapFont(Gdx.files.internal("font-export.fnt"));

    }

    @Override
    public void show() {

        parent.assMan.queueAddBackground();

        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

        System.out.println("Loading images....");
        stateTime = 0f;

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        backgroundTexture =  parent.assMan.manager.get("skin/EternalBackground.png");
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();


        if (parent.assMan.manager.update()) {
            currentLoadingStage+= 1;
            switch(currentLoadingStage){

                case SOUND:
                    String loadingSounds = "Loading Sounds....";
                    System.out.println(loadingSounds);
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    String loadingFonts = "Loading fonts....";
                    System.out.println(loadingFonts);
                    parent.assMan.queueAddMusic();
                    break;
                case 2:
                    String finished = "Finished";
                    System.out.println(finished);
                    break;
            }
            if (currentLoadingStage >2){
                countDown -= delta;
                currentLoadingStage = 2;
                if(countDown < 0){
                    parent.changeScreen(MyGdxGame.MENU);
                }
            }
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