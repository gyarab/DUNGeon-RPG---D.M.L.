package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.views.*;


public class MyGdxGame extends Game {

    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private InventoryScreen inventoryScreen;
    private EndScreen endScreen;
    private boolean musicIsPlaying;
    private AppPreferences preferences;
    public B2dAssetManager assMan = new B2dAssetManager();
    private Music playingSong;
    private float lastVolume;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int INVENTORY = 3;
    public final static int ENDSCREEN = 4;


    public AppPreferences getPreferences() {
        return this.preferences;
    }

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        preferences = new AppPreferences();

        assMan.queueAddMusic();
        assMan.manager.finishLoading();
        playingSong = assMan.manager.get("music/Vitas_7th_Element.mp3");
        playingSong.setLooping(true);
        playingSong.play();
        lastVolume = getPreferences().getMusicVolume();

    }

    public void changeMusicState(boolean on) {
        musicIsPlaying = on;
        if (on) playingSong.play();
        else playingSong.pause();
    }

    public void setVolume(float volume) {
		playingSong.setVolume(volume);
		getPreferences().setMusicVolume(volume);

    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {

    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if (mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case INVENTORY:
                if(inventoryScreen == null) inventoryScreen = new InventoryScreen(this);
                this.setScreen(inventoryScreen);
                break;
            case ENDSCREEN:
                if( endScreen== null)endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;

        }
    }

    public void setLastVolume(float lastVolume) {
        this.lastVolume = lastVolume;
    }

    public float getLastVolume() {
        return lastVolume;
    }
}



