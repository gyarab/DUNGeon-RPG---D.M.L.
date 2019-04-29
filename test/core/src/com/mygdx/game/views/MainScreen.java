package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Box2DTutorial;
import com.mygdx.game.KeyboardController;
import com.mygdx.game.B2dModel;
import com.mygdx.game.ProjectileEntity;

import java.util.ArrayList;

import static com.mygdx.game.B2dModel.listOfProjectiles;

public class MainScreen implements Screen {

    private Box2DTutorial parent;
    private B2dModel model;
    private OrthographicCamera cam;
    private KeyboardController controller;
    Box2DDebugRenderer debugRenderer;
    private Texture playerTex;
    private Texture enemyTex;
    private Texture swordTex;
    private Texture projectileTex;
    private SpriteBatch spriteBatch;
    private Sprite swordSprite;
    //private TiledMap tiledMap;
   // private TiledMapRenderer tiledMapRenderer;



    public MainScreen(Box2DTutorial box2DTutorial){
        parent = box2DTutorial;
        controller = new KeyboardController();
        cam = new OrthographicCamera(40,30);
        model = new B2dModel(controller,cam,parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        spriteBatch = new SpriteBatch();
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();
        //tiledMap = new TmxMapLoader().load("RPGMap.tmx");
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        playerTex = parent.assMan.manager.get("images/player.png");
        enemyTex = parent.assMan.manager.get("images/enemy.png");
        swordTex = parent.assMan.manager.get("images/GoldenSpear.png");
        projectileTex = parent.assMan.manager.get("images/enemy.png");
        swordSprite = new Sprite(swordTex);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void render(float delta){
        cam.position.x = model.player.getPosition().x-1;
        cam.position.y = model.player.getPosition().y-1;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);

        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
        //tiledMapRenderer.setView(cam);
        //tiledMapRenderer.render();
        swordSprite.setRotation(model.sword.getAngle());

        ArrayList<ProjectileEntity> projectilesToRemove = new ArrayList<>();

        for(ProjectileEntity projectile: listOfProjectiles){
            projectile.updateProjectile(delta);
            if(projectile.removeProjectile)
                projectilesToRemove.add(projectile);

        }
        listOfProjectiles.removeAll(projectilesToRemove);

        spriteBatch.begin();

        for(ProjectileEntity projectile: listOfProjectiles){
            projectile.render(spriteBatch,projectileTex);
        }

        spriteBatch.draw(playerTex,model.player.getPosition().x-1,model.player.getPosition().y-1,2,2);
        spriteBatch.draw(enemyTex,model.enemy.getPosition().x-1,model.enemy.getPosition().y-1,2,2);
        spriteBatch.draw(swordSprite, model.sword.getPosition().x  ,model.sword.getPosition().y-0.5f,
        swordSprite.getOriginX(),swordSprite.getOriginY()-0.5f,2,2,swordSprite.getScaleX(),swordSprite.getScaleY(),swordSprite.getRotation() );

        spriteBatch.end();

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
        spriteBatch.dispose();
    }
}
