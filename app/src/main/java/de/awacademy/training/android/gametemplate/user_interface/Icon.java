package de.awacademy.training.android.gametemplate.user_interface;

import android.content.res.Resources;

import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.sprites.Sprite;

public class Icon extends UiElement {
    public Icon(Resources res, int spriteId, Vector2 pos) {
        super(res, spriteId, pos);
    }
}
