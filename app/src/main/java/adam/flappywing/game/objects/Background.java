package adam.flappywing.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import adam.flappywing.R;

public class Background extends GameObject {

    int xDecrease;

    public void setxDecrease(int xDecrease) {
        this.xDecrease = xDecrease;
    }

    public Background(Context context, int canvasWidth, int canvasHeight) {
        super(context, canvasWidth, canvasHeight);

        xDecrease = -((int)(canvasWidth * 0.005));
    }

    @Override
    protected void initPositionDefault(Context context, int canvasWidth, int canvasHeight) {
        Bitmap backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

        this.x = 0;
        this.y = 0;
        this.width = backgroundImage.getWidth();
        this.height = backgroundImage.getHeight();
    }

    @Override
    public void end(Context context){
        setxDecrease(0);
    }

    @Override
    public void draw(Canvas canvas) {

        for (int x = getX(); x < getCanvasWidth(); x += getWidth()){
            for (int y = 0; y < getCanvasHeight(); y += getHeight()){
                canvas.drawBitmap(
                        getBitmap(),
                        null,
                        new Rect(x, y, x + getWidth(), y + getHeight()),
                        null) ;
            }
        }
    }

    @Override
    public void update(){
        setX(getX() + xDecrease);

        if (Math.abs(getX()) >= getWidth()){
            setX(0);
        }
    }

    @Override
    public void initBitmap(Context context) {
        getBitmaps().add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
    }

    @Override
    public void touchEvent(Context context, MotionEvent e) {}

    @Override
    public void pipesBehindEvent() {}

}
