package de.awacademy.training.android.gametemplate.collider;

import de.awacademy.training.android.gametemplate.math.Vector2;

public interface ICollider {
    boolean collidingWithPoint(Vector2 objectOrigin, Vector2 collisionPoint);
}
