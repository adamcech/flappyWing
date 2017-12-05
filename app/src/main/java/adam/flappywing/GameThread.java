package adam.flappywing;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run()  {
        while(running) {
            Canvas canvas= null;
            try {
                this.gameSurface.update();

                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas)  {
                    this.gameSurface.draw(canvas);
                }
            } catch(Exception e)  { }
            finally {
                if(canvas!= null)  {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean running)  {
        this.running = running;
    }
}