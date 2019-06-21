package de.awacademy.training.android.gametemplate.user_interface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import de.awacademy.training.android.gametemplate.math.Vector2;
import de.awacademy.training.android.gametemplate.sprites.Sprite;

public class DrawText extends UiElement {

    public int getTextColor() {
        return textColor;
    }

    private int textColor;

    public float getTextSize() {
        return textSize;
    }

    public String getText() {
        return text;
    }

    private float textSize;
    private String text;

    public DrawText(String text, Vector2 pos, float textSize, int textColor) {
        super(new Sprite(textAsBitmap(text, textSize, textColor)), pos);
        this.textColor = textColor;
        this.textSize = textSize;
        this.text = text;
    }

    public void changeTextColor(int textColor) {
        this.textColor = textColor;
        this.updateTextDisplay();
    }

    public void changeTextContent(String text){
        this.text = text;
        this.updateTextDisplay();
    }

    private void updateTextDisplay(){
        this.sprite = new Sprite(textAsBitmap(text, textSize, textColor));
    }

    private static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textSize);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        Paint stkPaint = new Paint();
        stkPaint.setStyle(Paint.Style.STROKE);
        stkPaint.setStrokeWidth(10);
        stkPaint.setTextSize(textSize);
        stkPaint.setColor(Color.BLACK);
        canvas.drawText(text, 0, baseline, stkPaint);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
