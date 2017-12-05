package adam.flappywing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class EndScreen extends GameObject{

    GameSurface gameSurface;

    public GameSurface getGameSurface() {
        return gameSurface;
    }

    protected EndScreen(GameSurface gameSurface, int x, int y, int width, int height, int canvasWidth, int canvasHeight) {
        super(gameSurface.getContext(), x, y, width, height, canvasWidth, canvasHeight);

        this.gameSurface = gameSurface;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        int cornerRadius = 50;

        paint.setColor(Color.rgb(192, 192, 192));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(getRectPosition()), cornerRadius, cornerRadius, paint);

        paint.setColor(Color.rgb(48, 48, 48));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRoundRect(new RectF(getRectPosition()), cornerRadius, cornerRadius, paint);


        paint.setColor(Color.rgb(96, 96, 96));
        int fontSize = 60;
        paint.setTextSize(fontSize);
        paint.setFakeBoldText(true);
        paint.setStrokeWidth(3);
        canvas.drawText("Score: "+getGameSurface().getScore().toString(), getX() - (getxOffset() / 2), getY() + (fontSize / 3), paint);
    }

    @Override
    public void touchEvent(Context context, MotionEvent e) {
        gameSurface.finish();
    }

    @Override
    public void update() {

    }

    @Override
    public void initBitmap(Context context) {

    }

    @Override
    public void end(Context context) {

    }
}
