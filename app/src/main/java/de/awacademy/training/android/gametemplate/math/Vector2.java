package de.awacademy.training.android.gametemplate.math;

import java.util.Vector;

public class Vector2 {
    public int getX() {
        return x;
    }

    private int x;

    public int getY() {
        return y;
    }

    private int y;

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static int DistanceBetweenPoints(Vector2 point1, Vector2 point2){
        return (int)Math.sqrt(Math.pow(point1.getX()-point2.getX(), 2) +
                              Math.pow(point1.getY()-point2.getY(), 2));
    }
}
