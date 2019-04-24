package com.mygdx.game;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;


public class KeyboardController implements InputProcessor {

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public boolean isMouse1Down, isMouse2Down,isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2(0,0);

    @Override
    public boolean keyDown(int keycode) {

        boolean keyProcessed = false;
        switch (keycode)
        {
            case Keys.A:
                left = true;
                keyProcessed = true;
                break;
            case Keys.D:
                right = true;
                keyProcessed = true;
                break;
            case Keys.W:
                up = true;
                keyProcessed = true;
                break;
            case Keys.S:
                down = true;
                keyProcessed = true;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Keys.A:
                left = false;
                keyProcessed = true;
                break;
            case Keys.D:
                right = false;
                keyProcessed = true;
                break;
            case Keys.W:
                up = false;
                keyProcessed = true;
                break;
            case Keys.S:
                down = false;
                keyProcessed = true;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;

        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        isDragged = false;

        if(button == 0){
            isMouse1Down = false;
        }else if(button == 1){
            isMouse2Down = false;
        }else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;

        return false;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        mouseLocation.x = screenX;
        mouseLocation.y = screenY;

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
