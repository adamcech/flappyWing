package adam.flappywing.game.objects.ship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import adam.flappywing.R;
import adam.flappywing.game.objects.GameObject;
import adam.flappywing.utils.AudioPlayer;

public class Ship extends GameObject {

    Gravity gravity;
    AudioPlayer audioPlayer;
    boolean end;

    private BitmapTiles explosionTiles;

    public Ship(Context context, int canvasWidth, int canvasHeight, AudioPlayer audioPlayer) {
        super(context, canvasWidth, canvasHeight);

        this.end = false;

        this.gravity = new Gravity(canvasHeight);
        this.audioPlayer = audioPlayer;
        this.explosionTiles = new BitmapTiles(context, R.drawable.explosion_tiles, 9, 9, 74);
    }

    @Override
    protected void initPositionDefault(Context context, int canvasWidth, int canvasHeight) {
        this.x = (int)(canvasWidth * 0.1);
        this.y = (int)(canvasHeight * 0.5);
        this.width = (int)(canvasWidth * 0.16);
        this.height = (int)(canvasHeight * 0.04);
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
                Paint paint = new Paint();
                float alphaStep = 255f / (explosionTiles.getLength() / 2f);
                int alpha = (int) (255 - (explosionTiles.getIndex() * alphaStep));
                paint.setAlpha(alpha);
                canvas.drawBitmap(getBitmap(), null, getRectPosition(), paint);
            }

//            if (!explosionTiles.isFinished()){
//                canvas.drawBitmap(
//                        explosionTiles.getBitmap(),
//                        explosionTiles.getCurr(),
//                        new Rect(getX() - getxOffset(), getY() - getxOffset(), getX() + getxOffset(), getY() + getxOffset()),
//                        null);
//            }

            if (!explosionTiles.isFinished()){
                canvas.drawBitmap(
                        explosionTiles.getNextBitmap(),
                        null,
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

    @Override
    public void pipesBehindEvent() { }

}
