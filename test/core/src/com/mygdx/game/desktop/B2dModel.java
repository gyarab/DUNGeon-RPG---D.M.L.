package com.mygdx.game.desktop;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.B2dAssetManager;
import com.mygdx.game.B2dContactListener;
import com.mygdx.game.KeyboardController;

public class B2dModel {
    public World world;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    private KeyboardController controller;
    private B2dAssetManager assMan;
    public Body player;
    private Body water;
    private OrthographicCamera camera;
    private Sound ping;
    private Sound boing;

    public static final int BOING_SOUND = 0;
    public static final int PING_SOUND = 1;

    public boolean isSwimming = false;

    public B2dModel(KeyboardController cont,  OrthographicCamera cam, B2dAssetManager assetManager){
        assMan = assetManager;
        camera=cam;
        controller = cont;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener(this));
        createFloor();
        //createObject();
        //createMovingObject();

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody,false);

        water =  bodyFactory.makeBoxPolyBody(1, -8, 40, 16, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody,false);
        water.setUserData("IAMTHESEA");

        bodyFactory.makeAllFixturesSensors(water);

        assMan.queueAddSounds();

        assMan.manager.finishLoading();

        ping = assMan.manager.get("sounds/ping.wav", Sound.class);
        boing = assMan.manager.get("sounds/boing.wav", Sound.class);

    }



    private void createObject(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);

        bodyd = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyd.createFixture(shape, 0.0f);

        shape.dispose();
    }

    private void createFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);
        bodys = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);
        bodys.createFixture(shape, 0.0f);
        shape.dispose();
    }

    private void createMovingObject(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-12);

        bodyk = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyk.createFixture(shape, 0.0f);

        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }

    public void logicStep(float delta){
        if(controller.isMouse1Down && pointIntersectsBody(player,controller.mouseLocation)){
            System.out.println("Player was clicked");
        }
        if(controller.left){
            player.applyForceToCenter(-10, 0,true);
        }else if(controller.right){
            player.applyForceToCenter(10, 0,true);
        }else if(controller.up){
            player.applyForceToCenter(0, 10,true);
        }else if(controller.down){
            player.applyForceToCenter(0, -10,true);
        }

        if(isSwimming){
            player.applyForceToCenter(0, 40, true);
        }
        world.step(delta , 3, 3);
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
        camera.unproject(mousePos); // convert from screen potition to world position
        if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
            return true;
        }
        return false;
    }

    public void playSound(int sound){
        switch(sound){
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
        }
    }

}