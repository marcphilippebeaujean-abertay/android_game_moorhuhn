package de.awacademy.training.android.gametemplate.user_interface;

import android.content.res.Resources;

import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.sprites.Sprite;

public abstract class UiElement implements IDrawable {

    public void setPos(Vector2 pos){
        this.pos = pos;
    }

    @Override
    public int getPosX() {
        return pos.getX();
    }

    @Override
    public int getPosY() {
        return pos.getY();
    }

    protected Vector2 pos;

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    protected Sprite sprite;

    UiElement(Resources res, int spriteId, Vector2 pos) {
        this.sprite = Sprite.initialiseSprite(res, spriteId);
        this.pos = pos;
    }

    UiElement(Sprite sprite, Vector2 pos){
        this.sprite = sprite;
        this.pos = pos;
    }
}
