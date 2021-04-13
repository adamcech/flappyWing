package adam.flappywing.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import adam.flappywing.game.GameSurface;

public class EndScreen extends GameObject{

    GameSurface gameSurface;

    public GameSurface getGameSurface() {
        return gameSurface;
    }

    public EndScreen(GameSurface gameSurface, int canvasWidth, int canvasHeight) {
        super(gameSurface.getContext(), canvasWidth, canvasHeight);
        this.gameSurface = gameSurface;
    }

    @Override
    protected void initPositionDefault(Context context, int canvasWidth, int canvasHeight) {
        this.x = canvasWidth / 2;
        this.y = canvasHeight / 2;
        this.width = (int)(canvasWidth * 0.7);
        this.height = (int)(canvasHeight * 0.2);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        int cornerRadius = 50;
        int fontSize = ((canvasHeight * canvasWidth) / (1280 * 720)) * 45;

        // GRAY FILLED BACKGROUND
        paint.setColor(Color.rgb(192, 192, 192));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(getRectPosition()), cornerRadius, cornerRadius, paint);

        // BLACK BORDER
        paint.setColor(Color.rgb(48, 48, 48));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRoundRect(new RectF(getRectPosition()), cornerRadius, cornerRadius, paint);

        // TEXT
        paint.setColor(Color.rgb(88, 88, 88));
        paint.setTextSize(fontSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(true);
        paint.setStrokeWidth(3);
        canvas.drawText("Score: " + getGameSurface().getScore().toString(), getX() - (getxOffset() / 2), getY() + (fontSize / 3), paint);
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

    @Override
    public void pipesBehindEvent() { }

}
