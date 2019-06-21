package de.awacademy.training.android.gametemplate.controllers;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

import de.awacademy.training.android.gametemplate.ILoopControllerInterface;
import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.R;
import de.awacademy.training.android.gametemplate.collider.CircleCollider;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.user_interface.Button;
import de.awacademy.training.android.gametemplate.user_interface.Icon;

public class AmmunitionController implements ILoopControllerInterface {

    private List<IDrawable> icons;
    private Button reloadButton;
    private int curAmmo;
    private int maxAmmo;
    private int reloadTime;
    private long reloadTimer;

    public AmmunitionController(Resources res, int maxAmmo, int reloadTime) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.reloadTimer = 0;
        this.curAmmo = maxAmmo;
        this.icons = new LinkedList<>();
        int buttomScreenMargin = 55;
        int iconSpacing = 120;
        for(int i = 0; i < curAmmo; i++){
            this.icons.add(new Icon(res,
                               R.drawable.snowball,
                               new Vector2(100, buttomScreenMargin + iconSpacing*i)));
        }
        Vector2 buttonPos = new Vector2(100, buttomScreenMargin + iconSpacing * icons.size());
        this.reloadButton = new Button(res, R.drawable.reload_icon, buttonPos, new CircleCollider(120));
        this.reloadButton.getSprite().setShouldDraw(false);
        this.icons.add(this.reloadButton);
    }

    public void onSnowballFired() {
        this.curAmmo--;
        this.drawIcons();
        if(this.curAmmo <= 0){
            this.initiateReload();
        }else{
            this.reloadButton.getSprite().setShouldDraw(true);
        }
    }

    public void initiateReload() {
        if(this.curAmmo < this.maxAmmo && !this.isReloading()) {
            this.reloadButton.getSprite().setShouldDraw(false);
            this.reloadTimer = this.reloadTime;
            for(IDrawable icon : icons) {
                icon.getSprite().setShouldDraw(false);
            }
        }
    }

    public boolean canFireSnowball() {
        return this.curAmmo > 0 && !this.isReloading();
    }

    private boolean isReloading() {
        return this.reloadTimer > 0;
    }

    @Override
    public void update(long dt){
        if(this.reloadTimer > 0) {
            this.reloadTimer -= dt;
            if(this.reloadTimer <= 0) {
                this.curAmmo = this.maxAmmo;
                this.drawIcons();
            }
        }
    }

    private void drawIcons() {
        for(int i = 0; i < maxAmmo; i++){
            boolean shouldDraw = i < curAmmo;
            this.icons.get(i).getSprite().setShouldDraw(shouldDraw);
        }
    }

    @Override
    public List<IDrawable> getDrawables() {
        return icons;
    }

    public boolean initiatedReloadAttempt(Vector2 touchVec) {
        if(this.reloadButton.clickedButton(touchVec)){
            this.initiateReload();
            return true;
        }
        return false;
    }
}
