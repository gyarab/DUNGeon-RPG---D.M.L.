package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class ProjectileEntity {

    private int reach;
    public static final int SPEED = 2;
    private Vector2 projectileDirection;
    private World world;
    private Body projectilesBody;
    private BodyFactory bodyFactory;
    private B2dModel model;

    float projectilePositionX, projectilePositionY;

    public boolean removeProjectile = false;

    public ProjectileEntity(float x, float y) {
        model = new B2dModel();
        world = model.world;
        bodyFactory = BodyFactory.getInstance(world);
        this.projectilePositionX = x;
        this.projectilePositionY = y;
        //TODO dodelat pro mys
        //projectilesBody = bodyFactory.makeBoxPolyBody(x, y, 2, 2, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody,true);

        reach = 32;

    }

    public void updateProjectile(float deltaTime) {
        projectilePositionY += SPEED * deltaTime;
        if (projectilePositionY > reach){
            removeProjectile = true;
        }
       /* if (projectilePositionY > reach){
            removeProjectile = true;
          if (projectilePositionX > reach){
            removeProjectile = true;
          if (projectilePositionY < -reach){
            removeProjectile = true;
          if (projectilePositionX < -reach){
            removeProjectile = true;

        }*/


    }

    public void render (SpriteBatch batch, Texture texture) {
        batch.draw(texture, projectilePositionX, projectilePositionY, 2 , 2);
    }






}
