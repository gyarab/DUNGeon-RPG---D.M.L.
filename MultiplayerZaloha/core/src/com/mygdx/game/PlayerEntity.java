package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerEntity extends Sprite {

    private Body player;
    private Vector2 playerPosition;
    private World world;
    private int x = 1;
    private int y = 1;
    private B2dModel model;
    private BodyFactory bodyFactory ;

    public PlayerEntity(Texture texture){

        super(texture);
        playerPosition = new Vector2(getX(),getY());


    }

    public PlayerEntity(){

        model = new B2dModel();
        world = model.world;
        bodyFactory = BodyFactory.getInstance(world);
        player = bodyFactory.makeBoxPolyBody(x, y, 2, 2, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody,true);


    }

    public Body getBody(){
        return player;
    }

    public boolean hasMoved(){
        if(playerPosition.x != getX()||playerPosition.y !=getY()){
            playerPosition.x = getX();
            playerPosition.y = getY();
            return true;
        }
        return false;
    }

}
