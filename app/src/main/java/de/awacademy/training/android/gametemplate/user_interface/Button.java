package de.awacademy.training.android.gametemplate.user_interface;

import android.content.res.Resources;

import de.awacademy.training.android.gametemplate.collider.ICollider;
import de.awacademy.training.android.gametemplate.math.Vector2;

public class Button extends UiElement {

    ICollider collider;

    public Button(Resources res, int spriteId, Vector2 pos, ICollider collider){
        super(res, spriteId, pos);
        this.collider = collider;
    }

    public boolean clickedButton(Vector2 touchVec){
        return collider.collidingWithPoint(this.pos, touchVec);
    }
}
