package de.awacademy.training.android.gametemplate.controllers;

import android.content.Context;
import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.awacademy.training.android.gametemplate.ILoopControllerInterface;
import de.awacademy.training.android.gametemplate.R;
import de.awacademy.training.android.gametemplate.game_objects.AbstractGameObject;
import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.game_objects.TargetObject;
import de.awacademy.training.android.gametemplate.math.Vector2;

public class TargetController implements ILoopControllerInterface {

    public List<AbstractGameObject> getTargets() {
        return targets;
    }

    private List<AbstractGameObject> targets;
    private long spawnTimer;
    private long lifetimeDecrement;
    private long spawnRateLowerBound;
    private long spawnRateUpperBound;
    private long minUpperRate;

    public TargetController(Resources resources, Context context) {
        this.targets = new LinkedList<>();
        for(int i = 0; i < 10; i++){
            this.targets.add(new TargetObject(resources, context, R.drawable.target, 120, 2000));
        }

        this.spawnRateLowerBound = 300;
        this.minUpperRate = 1500;
        this.spawnRateUpperBound = 4000;
        this.lifetimeDecrement = 400;
        this.spawnTimer = this.generateSpawnTime();
    }

    public void update(long dt){
        if(this.spawnTimer <= 0) {
            this.spawnTarget();
        }
        this.spawnTimer -= dt;
        for(AbstractGameObject target : this.targets){
            target.update(dt);
        }
    }

    private void spawnTarget(){
        this.spawnTimer = this.generateSpawnTime();
        for(AbstractGameObject obj : this.targets){
            if(obj.getSprite().shouldDraw()) continue;
            TargetObject targetObj = (TargetObject)obj;
            targetObj.resetTarget();
            break;
        }
    }

    public boolean hitTarget(Vector2 touchVec){
        for (AbstractGameObject gameObj : this.targets) {
            // Kollision mit Objekten ignorieren, die nicht gezeichnet werden sollen
            if(!gameObj.getSprite().shouldDraw()) continue;
            boolean collision = gameObj.collidedWithTouch(touchVec);
            if (collision) {
                gameObj.getSprite().setShouldDraw(false);
                return true;
            }
        }
        return false;
    }

    public long generateSpawnTime() {
        Random rand = new Random();
        long spawnTime = rand.nextInt((int)spawnRateUpperBound-(int)spawnRateLowerBound) + spawnRateLowerBound;
        spawnRateUpperBound = Math.max(spawnRateUpperBound-lifetimeDecrement, minUpperRate);
        return spawnTime;
    }

    @Override
    public List<IDrawable> getDrawables(){
        return new LinkedList<IDrawable>(targets);
    }
}
