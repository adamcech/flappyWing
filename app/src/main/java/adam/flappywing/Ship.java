package adam.flappywing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class Ship extends GameObject{

    Gravity gravity;
    AudioPlayer audioPlayer;
    boolean end;

    private BitmapTiles explosionTiles;


    public Ship(Context context, int x, int y, int width, int height, int canvasWidth, int canvasHeight, AudioPlayer audioPlayer) {
        super(context, x, y, width, height, canvasWidth, canvasHeight);
        gravity = new Gravity(canvasHeight);
        this.audioPlayer = audioPlayer;

        end = false;
        explosionTiles = new BitmapTiles(context, R.drawable.explosion_tiles, 9, 9, 74);
    }


    public void update(){
        if (gravity.getY() < 0){
            if (getY() + (int)gravity.getY() - getyOffset() > 0) {
                setY(getY() + (int)gravity.getY());
            } else {
                gravity.setY(0);
            }
        } else {
            if (getY() + gravity.getY() + getyOffset() < getCanvasHeight()) {
                setY(getY() + (int)gravity.getY());
            }
        }

        gravity.update();
    }

    @Override
    public void end(Context context){
        audioPlayer.playExplosion(context);
        gravity.end();
        setEnd(true);
    }

    @Override
    public void touchEvent(Context context, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN && !isEnd()) {
            audioPlayer.playEngine(context);
            gravity.speedUp();
        }
    }

    @Override
    public void initBitmap(Context context) {
        getBitmaps().add(BitmapFactory.decodeResource(context.getResources(), R.drawable.xwing_on));
        getBitmaps().add(BitmapFactory.decodeResource(context.getResources(), R.drawable.xwing_off));
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isEnd()){
            canvas.drawBitmap(getBitmap(), null, getRectPosition(), null);
        } else {

            if (!explosionTiles.isAcrossHalfway()){
                canvas.drawBitmap(getBitmap(), null, getRectPosition(), null);
            }

            if (!explosionTiles.isFinished()){
                canvas.drawBitmap(
                        explosionTiles.getBitmap(),
                        explosionTiles.getCurr(),
                        new Rect(getX() - getxOffset(), getY() - getxOffset(), getX() + getxOffset(), getY() + getxOffset()),
                        null);
            }
        }
    }

    @Override
    public Bitmap getBitmap(){
        return gravity.getY() < 0 ? getBitmap(0) : getBitmap(1);
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public BitmapTiles getExplosionTiles() {
        return explosionTiles;
    }
}
