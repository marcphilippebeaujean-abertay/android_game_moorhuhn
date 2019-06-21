package de.awacademy.training.android.gametemplate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

import de.awacademy.training.android.gametemplate.controllers.GameController;
import de.awacademy.training.android.gametemplate.controllers.TargetController;
import de.awacademy.training.android.gametemplate.math.ScreenHelper;
import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.sprites.Sprite;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private GameController gameController;
    private Sprite backgroundSprite;

    @SuppressLint("ClickableViewAccessibility")
    public GameCanvas(Context context) {
        super(context);

        // set canvas to hanlde events
        this.setFocusable(true);

        this.getHolder().addCallback(this);

        this.setOnTouchListener(this);

        // Hintergrund an die Größe des Bilschirms anpassen
        Vector2 screenDim = ScreenHelper.getScreenDim(this.getContext());
        int width = screenDim.getX();
        int height = screenDim.getY();
        Bitmap bgNonScaledImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.android_game_background);
        Bitmap bgImage = Bitmap.createScaledBitmap(bgNonScaledImage,
                                                   width,
                                                   height,
                                                   false);
        this.backgroundSprite = new Sprite(bgImage);
    }

    public void draw(Canvas canvas, List<IDrawable> objectsToDraw) {
        super.draw(canvas);
        canvas.drawBitmap(this.backgroundSprite.getImage(), 0, 0, null);
        for (IDrawable objectToDraw : objectsToDraw) {
            Sprite sprite = objectToDraw.getSprite();
            if(!sprite.shouldDraw()){
                continue;
            }
            canvas.drawBitmap(sprite.getImage(),
                            objectToDraw.getPosX() - (sprite.getWidth() / 2),
                            objectToDraw.getPosY() - (sprite.getHeight() / 2),
                        null);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Erstelle game objects und initialisiere sprites
        this.gameController = new GameController(this, holder);
        this.gameController.setRunning(true);
        this.gameController.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameController.setRunning(false);
                // parent runnable thread is to wait for game controller end
                this.gameController.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Vector2 touchVec = new Vector2((int)motionEvent.getX(),
                                       (int)motionEvent.getY());
        this.gameController.onTouchDetected(touchVec);
        return false;
    }
}
