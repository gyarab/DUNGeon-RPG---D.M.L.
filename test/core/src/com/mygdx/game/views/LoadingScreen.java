package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Box2DTutorial;

public class LoadingScreen implements Screen {
    private Box2DTutorial parent;
    private SpriteBatch sb;
    private Texture backgroundTexture;
    public final int SOUND = 0;
    public final int MUSIC = 1;

    private int currentLoadingStage = 0;

    public float countDown = 1f;
    public float stateTime = 0f;



    public LoadingScreen(Box2DTutorial box2dTutorial){
        parent = box2dTutorial;
        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

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
        sb.begin();
        sb.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();

        if (parent.assMan.manager.update()) {
            currentLoadingStage+= 1;
            switch(currentLoadingStage){

                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddMusic();
                    break;
                case 2:
                    System.out.println("Finished");
                    break;
            }
            if (currentLoadingStage >2){
                countDown -= delta;
                currentLoadingStage = 2;
                if(countDown < 0){
                    parent.changeScreen(Box2DTutorial.MENU);
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