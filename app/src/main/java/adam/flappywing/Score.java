package adam.flappywing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Score extends GameObject {
    GameSurface gameSurface;
    Paint paint;
    private int score;
    private boolean end;

    public int toInt(){
        return score;
    }

    @Override
    public String toString(){
        return String.valueOf(score);
    }

    public void increaseScore(){
        this.score++;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    protected Score(GameSurface gameSurface, int x, int y, int width, int height, int canvasWidth, int canvasHeight) {
        super(gameSurface.getContext(), x, y, width, height, canvasWidth, canvasHeight);

        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setFakeBoldText(true);

        this.gameSurface = gameSurface;
        this.score = 0;
        this.end = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isEnd()){
            canvas.drawText(this.toString(), getX(), getY(), paint);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void initBitmap(Context context) {

    }

    @Override
    public void touchEvent(Context context, MotionEvent e) {

    }

    @Override
    public void end(Context context) {
        setEnd(true);
    }
}
