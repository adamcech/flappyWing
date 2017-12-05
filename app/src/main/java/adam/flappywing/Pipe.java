package adam.flappywing;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class Pipe extends GameObject {
    int xDecrease;
    boolean isBottom;
    GameSurface gameSurface;
    public int id;
    public boolean end;

    public int getxDecrease() {
        return xDecrease;
    }
    public void setxDecrease(int xDecrease) {
        this.xDecrease = xDecrease;
    }
    public boolean isBottom(){
        return isBottom;
    }
    public boolean isEnd() {
        return end;
    }
    public void setEnd(boolean end) {
        this.end = end;
    }

    protected Pipe(GameSurface gameSurface, int x, int y, int width, int height, int canvasWidth, int canvasHeight) {
        super(gameSurface.getContext(), x, y, width, height, canvasWidth, canvasHeight);
        xDecrease = (int)(canvasWidth * 0.01);
        this.gameSurface = gameSurface;
        end = false;
    }

    public void reinit(int pipex, int pipey, int width, int height){
        setX(pipex);
        setY(pipey);
        setWidth(width);
        setHeight(height);
        initOffsets();
    }


    @Override
    public void end(Context context){
        setxDecrease(0);
        end = true;
    }

    @Override
    public void update() {
        if (!isEnd()){
            if (collisionDetect()){
                gameSurface.setEnd(true);
            }

            int xNew = getX() - getxDecrease();

            if (xNew + getxOffset() < 0){
                gameSurface.reinitPipes();
            } else {
                setX(getX() - getxDecrease());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), null, getRectPosition(), null);
    }

    @Override
    public void initBitmap(Context context) {
        getBitmaps().add(BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe));
    }

    @Override
    public void touchEvent(Context context, MotionEvent e) {

    }
}
