package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mygdx.game.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.B2dModel.listOfProjectiles;

public class MainScreen implements Screen {

    private MyGdxGame parent;
    private B2dModel model;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private Box2DDebugRenderer debugRenderer;
    private Texture playerTex;
    private Texture enemyTex;
    private Texture swordTex;
    private Texture projectileTex;
    private SpriteBatch spriteBatch;
    private Sprite swordSprite;
    private PlayerEntity player;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private final float UPDATE_TIME = 1/60f;
    float timer;
    private Socket socket;
    private String id;
    private Texture playerShip;
    private Texture friendlyPlayer;
    HashMap<String, PlayerEntity> friendlyPlayers;
    private float playerHeight =4;
    private float playerWidth = 4;


    public MainScreen(MyGdxGame main){
        parent = main;
        controller = new KeyboardController();
        cam = new OrthographicCamera(40,30);
        model = new B2dModel(controller,cam,parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        connectSocket();
        configSocketEvents();
        playerShip = new Texture("images/playerShip.png");
        friendlyPlayer = parent.assMan.manager.get("images/Jellik.png");
        friendlyPlayers = new HashMap<String, PlayerEntity>();
        spriteBatch = new SpriteBatch();
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();
        tiledMap = new TmxMapLoader().load("ActualMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/40f);
        playerTex = parent.assMan.manager.get("images/ugandaBoi.png");
        enemyTex = parent.assMan.manager.get("images/enemy.png");
        swordTex = parent.assMan.manager.get("images/GoldenSpear.png");
        projectileTex = parent.assMan.manager.get("images/fireball.png");
        player = new PlayerEntity(playerTex);
        swordSprite = new Sprite(swordTex);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void render(float delta){
        handleInput(Gdx.graphics.getDeltaTime());
        updateServer(Gdx.graphics.getDeltaTime());
        cam.position.x = model.player.getPosition().x-1;
        cam.position.y = model.player.getPosition().y-1;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);

        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
        swordSprite.setRotation(model.sword.getAngle());

        ArrayList<ProjectileEntity> projectilesToRemove = new ArrayList<ProjectileEntity>();

        for(ProjectileEntity projectile: listOfProjectiles){
            projectile.updateProjectile(delta);
            if(projectile.removeProjectile)
                projectilesToRemove.add(projectile);

        }
        listOfProjectiles.removeAll(projectilesToRemove);

        spriteBatch.begin();
        if(player!=null){
            player.draw(spriteBatch);
            player.setSize(playerWidth,playerHeight);

        }
        for(HashMap.Entry<String,PlayerEntity> entry: friendlyPlayers.entrySet()){
            entry.getValue().draw(spriteBatch);
        }

        for(ProjectileEntity projectile: listOfProjectiles){
            projectile.render(spriteBatch,projectileTex);
        }

        spriteBatch.draw(player,model.player.getPosition().x-2,model.player.getPosition().y-2,playerWidth, playerHeight);
        //spriteBatch.draw(enemyTex,model.enemy.getPosition().x-1,model.enemy.getPosition().y-1,2,2);
        spriteBatch.draw(swordSprite, model.sword.getPosition().x  ,model.sword.getPosition().y-0.5f,
        swordSprite.getOriginX(),swordSprite.getOriginY()-0.5f,2,2,swordSprite.getScaleX(),swordSprite.getScaleY(),swordSprite.getRotation() );

        spriteBatch.end();

    }
    private void handleInput(float deltaTime) {
        if(player != null){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                player.setPosition(player.getX()-200*deltaTime,player.getY());
            }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                player.setPosition(player.getX()+200*deltaTime,player.getY());
            }
        }

    }

    public void updateServer(float dt){
        timer += dt;
        if(timer >= UPDATE_TIME && player != null && player.hasMoved()){
            JSONObject data = new JSONObject();
            try{
                data.put("x",player.getX());
                data.put("y",player.getY());
                socket.emit("playerMoved",data);
            }catch(JSONException e){
                Gdx.app.log("SocketIO", "Error updating server");
            }
        }
    }

    public void connectSocket(){
        try {
            socket= IO.socket("http://localhost:8080");
            socket.connect();

        }catch (Exception e ){
            System.out.println(e);
        }
    }

    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO","Connected");
                player = new PlayerEntity(playerTex);


            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("SocketIO","My ID:" + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting PlayerID");
                }

            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    String playerId = data.getString("id");
                    Gdx.app.log("SocketIO","New Player connected:" + id);
                    friendlyPlayers.put(playerId,new PlayerEntity(friendlyPlayer));
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New PlayerID");
                }

            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO","New Player disconnected:" + id);
                    friendlyPlayers.remove(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New PlayerID");
                }

            }
        }).on("playerMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    String playerId = data.getString("id");
                    Double x = data.getDouble("x");
                    Double y = data.getDouble("y");

                    if(friendlyPlayers.get(playerId) != null){
                        friendlyPlayers.get(playerId).setPosition(x.floatValue(), y.floatValue());
                    }

                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New PlayerID");
                }

            }
        }).on("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray)  args[0];
                try{
                    for(int i = 0; i< objects.length(); i++){
                        PlayerEntity coopPlayer = new PlayerEntity(friendlyPlayer);
                        Vector2 position = new Vector2();
                        position.x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                        position.y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                        coopPlayer.setPosition(position.x,position.y);

                        friendlyPlayers.put(objects.getJSONObject(i).getString("id"),coopPlayer);

                    }

                }catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error getting player's position");
                }
            }
        });
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
        playerShip.dispose();
        friendlyPlayer.dispose();
    }
}



