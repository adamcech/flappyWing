package adam.flappywing.game.objects.pipes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import adam.flappywing.R;
import adam.flappywing.game.GameSurface;
import adam.flappywing.game.objects.GameObject;

abstract public class Pipe extends GameObject {
    int xDecrease;
    GameSurface gameSurface;
    public int id;
    public boolean end;

    protected int managerStep;

    protected PipesManager pipesManager;

    public int getxDecrease() {
        return xDecrease;
    }
    public void setxDecrease(int xDecrease) {
        this.xDecrease = xDecrease;
    }
    public boolean isEnd() {
        return end;
    }

    private int pipeWidthMax;
    private int pipeWidthMin;
    private int widthStep;
    private boolean pipeDecrease;

    private int alpha;
    private boolean alphaIncrease;

    public Pipe(GameSurface gameSurface, PipesManager pipesManager, int canvasWidth, int canvasHeight) {
        super(gameSurface.getContext(), pipesManager, canvasWidth, canvasHeight);

        this.managerStep = this.pipesManager.managerStep;
        this.xDecrease = (int)(canvasWidth * 0.0125);
        this.gameSurface = gameSurface;
        this.end = false;

        this.pipeWidthMax = (int)(canvasWidth * 0.155);
        this.pipeWidthMin = (int)(canvasWidth * 0.145);
        this.widthStep = 1;
        this.pipeDecrease = true;

        this.alpha = 48;
        this.alphaIncrease = true;
    }

    @Override
    protected void initPositionPipes(Context context, PipesManager pipesManager, int canvasWidth, int canvasHeight) {
        this.pipesManager = pipesManager;
        this.reinit();
    }

    abstract public void reinit();

    protected void reinit(int pipex, int pipey, int width, int height){
        setX(pipex);
        setY(pipey);
        setWidth(width);
        setHeight(height);
        setxOffset(width / 2);
        setyOffset(height / 2);
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
                gameSurface.signalEnd(true);
            }

            int xNew = getX() - getxDecrease();

            if (xNew + getxOffset() < 0){
                this.gameSurface.onPipesBehind();
            } else {
                setX(xNew);
            }
        }


        if (new Random().nextDouble() < 0.33) {
            this.width = this.pipeDecrease ? this.width - this.widthStep : this.width + this.widthStep;
            if (this.width > this.pipeWidthMax || this.width < this.pipeWidthMin || new Random().nextDouble() < 0.05) {
                this.pipeDecrease = !this.pipeDecrease;
            }
            this.xOffset = width / 2;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getRectPosition();

        canvas.drawBitmap(getBitmap(), null, rect, null);
        canvas.drawRect(rect, this.getPaint());
    }

    private Paint getPaint() {
        this.alpha += this.alphaIncrease ? 1 : -1;
        if (this.alpha < 32 || this.alpha > 64) {
            this.alphaIncrease = !this.alphaIncrease;
        }

        Paint paint = new Paint();

        paint.setColor(Color.rgb(0, 0, 0));
        paint.setAlpha(this.alpha);
        paint.setStyle(Paint.Style.FILL);

        return paint;
    }

    @Override
    public void initBitmap(Context context) {
        getBitmaps().add(BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe));
    }

    @Override
    public void touchEvent(Context context, MotionEvent e) { }

    @Override
    public void pipesBehindEvent() {
        if (pipesManager.managerStep == managerStep) {
            pipesManager.regenerateBonusHeight();
        }

        this.managerStep = pipesManager.managerStep;

        this.reinit();
    }

}
