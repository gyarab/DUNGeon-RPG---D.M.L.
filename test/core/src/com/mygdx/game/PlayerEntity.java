package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerEntity {

    private int healthPoints;
    private int mana;
    private int level;
    private int xp;
    private Body player;
    private World world;
    private int x = 1;
    private int y = 1;
    private B2dModel model;
    private BodyFactory bodyFactory ;


    public PlayerEntity(){
        model = new B2dModel();
        world = model.world;
        bodyFactory = BodyFactory.getInstance(world);;
        player = bodyFactory.makeBoxPolyBody(x, y, 2, 2, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody,true);


    }

    public Body getBody(){
        return player;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getMana() {
        return mana;
    }

    public void setMana  (int manaPoints) {
        this.mana = manaPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
