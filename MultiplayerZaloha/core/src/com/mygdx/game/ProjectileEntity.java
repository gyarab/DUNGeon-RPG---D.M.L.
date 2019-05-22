package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class ProjectileEntity {

    private int reach;
    private Vector2 projectilePosition;
    private Vector2 projectileDirection;
    private B2dModel model;
    private World world;
    private Body projectilesBody;
    private BodyFactory bodyFactory;

    public boolean removeProjectile = false;

    public ProjectileEntity(float x, float y) {
        float cursorLocationX = Gdx.input.getX();
        float cursorLocationY = Gdx.input.getY();

        model = new B2dModel();
        world = model.world;
        bodyFactory = BodyFactory.getInstance(world);
        projectilePosition = new Vector2(x,y);
        projectileDirection = new Vector2(new Vector2(cursorLocationX,cursorLocationY).sub(projectilePosition).nor());
        projectilesBody = bodyFactory.makeBoxPolyBody(projectilePosition.x+2.5f, projectilePosition.y , 2, 2, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody,true);

        reach = 32;

    }

    public void updateProjectile(float deltaTime) {

            projectilesBody.applyForceToCenter(projectileDirection.x,projectileDirection.y, true);
            projectilePosition.add(projectileDirection.x*deltaTime,projectileDirection.y*deltaTime);

            /*
            if (projectilePosition.y > reach){
                removeProjectile = true;

            }
            if (projectilePosition.x > reach){
                  removeProjectile = true;
            }
            if (projectilePosition.y< -reach){
                  removeProjectile = true;
            }
            if (projectilePosition.x <-reach){
                removeProjectile = true;

            }
             */

    }

    public void render (SpriteBatch batch, Texture texture) {
        batch.draw(texture, projectilesBody.getPosition().x -1 , projectilesBody.getPosition().y - 1, 2 , 2);
    }


    public Body getProjectilesBody() {
        return projectilesBody;
    }
}
