package de.awacademy.training.android.gametemplate.controllers;

import java.util.LinkedList;
import java.util.List;

import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.ILoopControllerInterface;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.user_interface.DrawText;

public class PointController implements ILoopControllerInterface {
    private List<IDrawable> drawables;
    private DrawText scoreDisplay;
    private DrawText pointerDisplay;
    private long pointerDisplayTimer;

    public int getUserScore() {
        return userScore;
    }

    public void incrementScore(Vector2 touchVec) {
        this.pointerDisplayTimer = 500;
        this.pointerDisplay.setPos(touchVec);
        this.pointerDisplay.changeTextContent(""+ 100*this.multiplier);
        this.userScore += 100*this.multiplier;
        this.multiplier++;
        this.scoreDisplay.changeTextContent("SCORE: " + this.userScore);
    }

    private int userScore;

    public void resetMultiplier() {
        this.hidePointerDisplay();
        this.multiplier = 1;
    }

    private int multiplier;

    public PointController() {
        this.userScore = 0;
        this.multiplier = 1;
        this.drawables = new LinkedList<>();

        this.scoreDisplay = new DrawText("SCORE: " + 0,
                                          new Vector2(350, 50),
                                   50.0f, 0xFF00FF00);
        this.drawables.add(this.scoreDisplay);

        this.pointerDisplay = new DrawText(" ",
                                            new Vector2(0, 0),
                                                50.0f, 0xFFFFFFFF);
        this.drawables.add(this.pointerDisplay);
    }

    public void hidePointerDisplay() {
        this.pointerDisplay.getSprite().setShouldDraw(false);
    }

    public void update(long dt){
        pointerDisplayTimer -= dt;
        if(pointerDisplayTimer < 0) {
            hidePointerDisplay();
        }
    }

    public List<IDrawable> getDrawables() {
        return this.drawables;
    }
}
