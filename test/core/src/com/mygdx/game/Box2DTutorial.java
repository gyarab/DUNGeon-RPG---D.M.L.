package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.desktop.AppPreferences;
import com.mygdx.game.views.*;


public class Box2DTutorial extends Game {

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	public B2dAssetManager assMan = new B2dAssetManager();
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public AppPreferences getPreferences() {
		return this.preferences;
	}

	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		preferences = new AppPreferences();

		assMan.queueAddMusic();
		assMan.manager.finishLoading();
		playingSong = assMan.manager.get("music/Vitas_7th_Element.mp3");
		//playingSong.play();
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {


	}
		public void changeScreen(int screen){
			switch(screen){
				case MENU:
					if(menuScreen == null) menuScreen = new MenuScreen(this);
					this.setScreen(menuScreen);
					break;
				case PREFERENCES:
					if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
					this.setScreen(preferencesScreen);
					break;
				case APPLICATION:
					if(mainScreen == null) mainScreen = new MainScreen(this);
					this.setScreen(mainScreen);
					break;
				case ENDGAME:
					if(endScreen == null) endScreen = new EndScreen(this);
					this.setScreen(endScreen);
					break;
			}
		}


}
