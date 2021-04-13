package adam.flappywing.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;


public class Score extends GameObject {
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

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Score(Context context, int canvasWidth, int canvasHeight) {
        super(context, 50, 100, 0, 0, canvasWidth, canvasHeight);

        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setFakeBoldText(true);

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

    @Override
    public void pipesBehindEvent() {
        this.score++;
    }
}
