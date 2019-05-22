package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import java.util.ArrayList;

public class B2dModel {

    public World world;
    private Body bodys;
    private KeyboardController controller;
    private B2dAssetManager assMan;
    public Body player;
    public Body sword;
    public Body enemy;
    private OrthographicCamera camera;
    private Sound ping;
    private Sound boing;
    private PlayerEntity playerEntity;
    private boolean swordIsUp;
    public static ArrayList<ProjectileEntity> listOfProjectiles;

    public static final int BOING_SOUND = 0;
    public static final int PING_SOUND = 1;

    public boolean isSwimming = false;

    public B2dModel(){

    }

    public B2dModel(KeyboardController cont,  OrthographicCamera cam, B2dAssetManager b2dAssetManager){
        assMan = b2dAssetManager;
        camera=cam;
        controller = cont;
        world = new World(new Vector2(0,0), true);
        world.clearForces();

        world.setContactListener(new B2dContactListener(this));
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        playerEntity = new PlayerEntity();
        player = playerEntity.getBody();
        //enemy = bodyFactory.makeBoxPolyBody(4, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody,false);
        sword = bodyFactory.makeBoxPolyBody(2,0,1,1,BodyFactory.STEEL,BodyDef.BodyType.DynamicBody,false);
        attachWeapon(player, sword);

        listOfProjectiles = new ArrayList<ProjectileEntity>();


        assMan.queueAddSounds();

        assMan.manager.finishLoading();

        ping = assMan.manager.get("sounds/ping.wav", Sound.class);
        boing = assMan.manager.get("sounds/boing.wav", Sound.class);

    }

    private void attachWeapon(Body bodyA, Body bodyB){

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = false;
        revoluteJointDef.localAnchorA.set(2,0);
        world.createJoint(revoluteJointDef);
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


    public void logicStep(float delta){
        if(controller.isMouse1Down && pointIntersectsBody(player,controller.mouseLocation)){
            System.out.println("Player was clicked");
        }
        if(controller.isMouse1Down){
            sword.setTransform(sword.getPosition(),-90*BodyFactory.DEGTORAD);
            swordIsUp = false;
        }
        else if (!swordIsUp){
            sword.setTransform(sword.getPosition(),0);
        }

        if(controller.left){
           player.applyForceToCenter(-10,0,true);

        }else if(controller.right){
            player.applyForceToCenter(10,0,true);

        }else if(controller.up){
            player.applyForceToCenter(0,10,true);

        }else if(controller.down){
            player.applyForceToCenter(0,-10,true);

        }else{
            player.setLinearVelocity(0,0);
        }

        if(Gdx.input.justTouched()){
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
            listOfProjectiles.add(new ProjectileEntity(player.getPosition().x +2,player.getPosition().y+2));
        }

        world.step(delta , 3, 3);
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0);
        camera.unproject(mousePos);
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
