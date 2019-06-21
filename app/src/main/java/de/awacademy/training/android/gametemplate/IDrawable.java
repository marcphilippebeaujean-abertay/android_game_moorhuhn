package de.awacademy.training.android.gametemplate;

import de.awacademy.training.android.gametemplate.sprites.Sprite;

public interface IDrawable {
    Sprite getSprite();
    int getPosX();
    int getPosY();
}
