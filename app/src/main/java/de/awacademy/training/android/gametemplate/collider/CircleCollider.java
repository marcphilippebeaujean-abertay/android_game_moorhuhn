package de.awacademy.training.android.gametemplate.collider;

import de.awacademy.training.android.gametemplate.math.Vector2;

public class CircleCollider implements ICollider {
    private int radius;

    public CircleCollider(int radius){
        this.radius = radius;
    }

    public boolean collidingWithPoint(Vector2 objectOrigin, Vector2 collisionPoint){
        return Vector2.DistanceBetweenPoints(objectOrigin,
                                             collisionPoint) < (radius / 2);
    }
}
