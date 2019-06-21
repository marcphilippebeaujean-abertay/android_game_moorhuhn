package de.awacademy.training.android.gametemplate.game_objects;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.collider.ICollider;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.sprites.Sprite;

public abstract class AbstractGameObject implements IDrawable {
    public Sprite getSprite() {
        return sprite;
    }

    protected Sprite sprite;
    protected ICollider collider;

    public int getPosX() {
        return posX;
    }

    protected int posX;

    public int getPosY() {
        return posY;
    }

    protected int posY;

    public abstract void update(long deltaTime);

    AbstractGameObject(Vector2 pos, Resources res, int spriteId) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.sprite = Sprite.initialiseSprite(res, spriteId);
    }

    public boolean collidedWithTouch(Vector2 touchPosition){
        return collider.collidingWithPoint(new Vector2(this.posX, this.posY),
                                           touchPosition);
    }
}
