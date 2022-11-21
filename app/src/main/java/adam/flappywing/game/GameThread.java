package adam.flappywing.game;


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
            Canvas canvas = null;
            try {
                this.gameSurface.update();

                // TODO mi to ted po peti letech na soucasnem mobilu jelo extremne rychle, takze
                // jsem to takhle prasacky omezil, ale chtelo byt o lepsi reseni, ktere zajisti
                // stabilni plynule FPS
                // Pripadne smazat tohle a zmensit rychlost lode a ostatnich veci asi bude taky fungovat
                Thread.sleep(1);

                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas)  {
                    this.gameSurface.draw(canvas);
                }
            } catch(Exception ignored)  { }
            finally {
                if (canvas != null)  {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean running)  {
        this.running = running;
    }
}