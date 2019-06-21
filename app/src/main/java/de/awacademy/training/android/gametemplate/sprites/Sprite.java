package de.awacademy.training.android.gametemplate.sprites;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.awacademy.training.android.gametemplate.R;

/**
 * This abstract sprite is for convenience usage handling sprites the come from
 * an image file that frames all possible views for it. As such it is possible
 * to move a sprite over the screen displaying it specifically for the direction
 * it is just moving.
 */
public class Sprite {

    /**
     * The image containing all sprite views
     */
    public void setImage(Bitmap image){
        this.image = image;
    }

    protected Bitmap image;

    protected R.drawable drawableRef;

    /**
     * Row count of the image file reflecting number of sprites moving horizontally
     */
    protected final int rowCount;
    /**
     * Column count of the image file reflecting number of sprites moving vertically
     */
    protected final int colCount;

    /**
     * Width of the png file
     */
    protected final int WIDTH;
    /**
     * Height of the png file
     */
    protected final int HEIGHT;

    /**
     * Width of one single sprite on the image file
     */
    protected final int width;
    /**
     * Height of one single sprite on the image file
     */
    protected final int height;

    public int getCurRow() {
        return curRow;
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    protected int curRow;

    public int getCurCol() {
        return curCol;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    protected int curCol;

    public boolean shouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    boolean shouldDraw;
    /**
     * Constructor that instantiates the sprite handler taking configuration parameters of the
     * sprite image
     *
     * @param image    Image file containing all sprite views
     * @param rowCount Number of horizontal sprites on the image
     * @param colCount Number of vertical sprites on the image
     */
    public Sprite(Bitmap image, int rowCount, int colCount, int curCol, int curRow) {

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH / colCount;
        this.height = this.HEIGHT / rowCount;

        this.curCol = curCol;
        this.curRow = curRow;

        this.shouldDraw = true;
    }

    public Sprite(Bitmap image, int rowCount, int colCount){
        this(image, rowCount, colCount, 0, 0);
    }

    public Sprite(Bitmap image){
        this(image, 1, 1, 0, 0);
    }

    /**
     * Method to crop the sprite out of the image file. Therefore, the Bitmap class of the
     * android graphics class library is used.
     *
     * @param row Horizontal index of the sprite to crop
     * @param col Vertical index of the sprite to crop
     * @return Cropped sprite
     */
    protected Bitmap createSubImageAt(int row, int col) {
        Bitmap sprite = Bitmap.createBitmap(
                image, col * width, row * height, width, height);
        return sprite;
    }


    public static Sprite initialiseSprite(Resources res, int spriteId){
        Bitmap targetBitmap = BitmapFactory.decodeResource(res, spriteId);
        return new Sprite(targetBitmap);
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public Bitmap getSpriteAtRowCol(int row, int col){
        return createSubImageAt(row, col);
    }

    public Bitmap getImage() {
        return createSubImageAt(curRow, curCol);
    }
}
