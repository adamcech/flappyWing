package adam.flappywing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    private int x, y;
    private int width, height;
    private int xOffset, yOffset;
    private int canvasWidth, canvasHeight;

    abstract public void draw(Canvas canvas);
    abstract public void update();
    abstract public void initBitmap(Context context);
    abstract public void touchEvent(Context context, MotionEvent e);
    abstract public void end(Context context);

    private List<GameObject> collisionObjects = new ArrayList<>();
    private List<Bitmap> bitmaps = new ArrayList<>();

    // FOR BACKGROUND
    protected GameObject(Context context, int canvasWidth, int canvasHeight){
        initBitmap(context);
        int width = getBitmap().getWidth();
        int height = getBitmap().getHeight();
        initDimensionsAndPosition(0, 0, width, height, canvasWidth, canvasHeight);
        initOffsets();
    }

    protected GameObject(Context context, int x, int y, int width, int height, int canvasWidth, int canvasHeight){
        initDimensionsAndPosition(x, y, width, height, canvasWidth, canvasHeight);
        initOffsets();
        initBitmap(context);
    }

    protected boolean collisionDetect(){
        for (GameObject o: getCollisionObjects()) {
            if (o.getRectPosition().intersect(this.getRectPosition())){
                return true;
            }
        }
        return false;
    }

    protected void initDimensionsAndPosition(int x, int y, int width, int height, int canvasWidth, int canvasHeight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    protected void initOffsets(){
        xOffset = width / 2;
        yOffset = height / 2;
    }

    public Bitmap getBitmap(){
        return bitmaps.get(0);
    }

    public Bitmap getBitmap(int i){
        return bitmaps.get(i);
    }

    public Rect getRectPosition(){
        return new Rect(x - xOffset, y - yOffset, x + xOffset, y + yOffset);
    }

    public Point2D getPointPosition(){
        return new Point2D(x, y);
    }


    public void log(){
        Log.i("x", String.valueOf(x));
        Log.i("y", String.valueOf(y));
        Log.i("width", String.valueOf(width));
        Log.i("height", String.valueOf(height));
        Log.i("xOffset", String.valueOf(xOffset));
        Log.i("yOffset", String.valueOf(yOffset));
        Log.i("canvasWidth", String.valueOf(canvasWidth));
        Log.i("canvasHeight", String.valueOf(canvasHeight));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) { this.y = y; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public List<GameObject> getCollisionObjects() {
        return collisionObjects;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }
}
