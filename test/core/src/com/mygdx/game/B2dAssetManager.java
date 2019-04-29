package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {

    public final AssetManager manager = new AssetManager();

    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";
    public final String sword = "images/GoldenSpear.png";
    public final String skin = "skin/glassy-ui.json";
    public final String playingSong = "music/Vitas_7th_Element.mp3";
    public final String background = "skin/EternalBackground.png";

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }
    public void queueAddImages(){
        manager.load(playerImage, Texture.class);
        manager.load(enemyImage, Texture.class);
        manager.load(sword, Texture.class);

    }

    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, params);
        manager.load(background,Texture.class);
    }

    public void queueAddBackground(){
        manager.load(background,Texture.class);
    }


    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound,Sound.class);
    }

}
