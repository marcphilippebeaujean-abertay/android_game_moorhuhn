package de.awacademy.training.android.gametemplate.game_objects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;
import de.awacademy.training.android.gametemplate.collider.CircleCollider;
import de.awacademy.training.android.gametemplate.math.ScreenHelper;
import de.awacademy.training.android.gametemplate.math.Vector2;

public class TargetObject extends AbstractGameObject {

    private int minPlacementX;
    private int minPlacementY;
    private int maxPlacementX;
    private int maxPlacementY;
    private long lifeTimer;
    private long maxLifeTime;

    public TargetObject(Resources res,
                        Context context,
                        int spriteId,
                        int colliderRadius,
                        long maxLifeTime) {
        super(new Vector2(100, 100), res, spriteId);
        // Größe des Bildschirms benutzen, um Platzierungslimitationen zu definieren
        int screenWidth = ScreenHelper.getScreenDim(context).getX();
        int screenHeight =ScreenHelper.getScreenDim(context).getY();
        this.minPlacementX = colliderRadius + 100;
        this.minPlacementY = colliderRadius + 50;
        this.maxPlacementX = screenWidth - colliderRadius;
        this.maxPlacementY = screenHeight - (colliderRadius * 2);
        this.collider = new CircleCollider(colliderRadius);
        this.lifeTimer = 0;
        this.maxLifeTime = maxLifeTime;
        this.sprite.setShouldDraw(false); // Am Anfang das Zielobjekt verstecken
    }

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void resetTarget(){
        Random rand = new Random();
        int x = rand.nextInt(this.maxPlacementX-this.minPlacementX) + this.minPlacementX;
        int y = rand.nextInt(this.maxPlacementY-this.minPlacementY) + this.minPlacementY;
        this.posX = x;
        this.posY = y;
        this.lifeTimer = this.maxLifeTime;
        this.sprite.setShouldDraw(true);
    }

    @Override
    public void update(long dt) {
        if(this.lifeTimer > 0) {
            this.lifeTimer -= dt;
        } else {
            if(this.sprite.shouldDraw()){
                this.sprite.setShouldDraw(false);
            }
        }
    }
}
