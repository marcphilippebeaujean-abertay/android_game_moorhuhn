package de.awacademy.training.android.gametemplate.controllers;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.LinkedList;
import java.util.List;

import de.awacademy.training.android.gametemplate.GameCanvas;
import de.awacademy.training.android.gametemplate.ILoopControllerInterface;
import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.math.ScreenHelper;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.user_interface.DrawText;

/**
 * The game controller is responsible for updating the game canvas and containing
 * game objects (sprites, background, etc.).
 */
public class GameController extends Thread {

    private boolean running;
    private GameCanvas gameCanvas;
    private SurfaceHolder surfaceHolder;

    public List<ILoopControllerInterface> getGameLoopCtrls() {
        return gameLoopCtrls;
    }
    private List<ILoopControllerInterface> gameLoopCtrls;
    private AmmunitionController ammoController;
    private PointController pointController;
    private TargetController targetController;

    private DrawText gameTimerDrawText;
    private long gameTime = 60000;

    public GameController(GameCanvas gameCanvas, SurfaceHolder surfaceHolder)  {
        this.gameCanvas = gameCanvas;
        this.surfaceHolder = surfaceHolder;
        this.gameLoopCtrls = new LinkedList<>();
        this.ammoController = new AmmunitionController(gameCanvas.getResources(), 5, 2000);
        this.pointController = new PointController();
        this.targetController = new TargetController(gameCanvas.getResources(), gameCanvas.getContext());

        Vector2 screenDim = ScreenHelper.getScreenDim(gameCanvas.getContext());
        this.gameTimerDrawText = new DrawText(""+ (int)(this.gameTime / 1000),
                                                new Vector2((screenDim.getX() / 2) + 30, 50),
                                                100, 0xFFFFFFFF);

        gameLoopCtrls.add(pointController);
        gameLoopCtrls.add(targetController);
        gameLoopCtrls.add(ammoController);
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas)  {
                    //this.gameCanvas.update();
                    this.gameCanvas.draw(canvas, assembleDrawableObjects());
                }
            }catch(Exception e)  {
                // do nothing
            } finally {
                if(canvas!= null)  {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            // interval to redraw game canvas
            // usage of milliseconds instead of nanoseconds
            long dt = calculateDeltaTime(System.nanoTime(), startTime);
            if(dt < 10)  {
                dt = 10;
            }
            for(ILoopControllerInterface gameObject : gameLoopCtrls) {
                gameObject.update(dt);
            }
            this.tickGameTime(dt);
            try {
                sleep(dt);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
        }
    }

    private long calculateDeltaTime(long now, long frameStart) {
        if (frameStart == -1) {
            frameStart = now;
        }
        // convert nanoseconds to milliseconds
        return (now - frameStart) / 1000000;
    }

    private void tickGameTime(long dt){
        this.gameTime -= dt;
        long timeInSecs = this.gameTime / 1000;
        String text = timeInSecs > 9 ? ""+timeInSecs : "0"+timeInSecs;
        this.gameTimerDrawText.changeTextContent(text);
        if(this.gameTime <= 0) {
            running = false;
        }
    }

    public void setRunning(boolean running)  {
        this.running = running;
    }

    public void onTouchDetected(Vector2 touchVec){
        // Erst überprüfen, ob der Spieler nachladen will
        if(this.ammoController.initiatedReloadAttempt(touchVec)) return;
        if(this.ammoController.canFireSnowball()) {
            this.ammoController.onSnowballFired();
            if(this.targetController.hitTarget(touchVec)){
                this.pointController.incrementScore(touchVec);
            }else{
                // Wenn keine Figur getroffen wurde, multiplier zurücksetzen
                this.pointController.resetMultiplier();
            }
        }
    }

    private List<IDrawable> assembleDrawableObjects(){
        LinkedList<IDrawable> drawables = new LinkedList<>();
        for(ILoopControllerInterface loopCtrl : this.gameLoopCtrls) {
            drawables.addAll(loopCtrl.getDrawables());
        }
        drawables.add(this.gameTimerDrawText);
        return drawables;
    }
}
