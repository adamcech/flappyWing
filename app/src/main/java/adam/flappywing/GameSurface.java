package adam.flappywing;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private Ship ship;
    private Pipe upPipe;
    private Pipe downPipe;
    private Score score;

    private int canvasWidth;
    private int canvasHeight;

    private boolean end;

    private AudioPlayer audioPlayer;

    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> touchEventObservers = new ArrayList<>();

    public List<GameObject> getTouchEventObservers() {
        return touchEventObservers;
    }
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
    public boolean isEnd() {
        return end;
    }
    public Score getScore() { return score; }

    public GameSurface(Context context)  {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);

        audioPlayer = ((GameActivity)context).getAudioPlayer();
    }

    public void finish(){
        ((GameActivity)getContext()).end();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (GameObject o : getTouchEventObservers()){
            o.touchEvent(getContext(), event);
        }
        return true;
    }

    public void update() {
        for (GameObject o: getGameObjects()){
            o.update();
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        for (GameObject o: getGameObjects()){
            o.draw(canvas);
        }
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        canvasWidth = width;
        canvasHeight = height;

        // BACKGROUND
        getGameObjects().add(new Background(getContext(), width, height));

        // PIPES
        int bonusHeightMax = (int)(canvasHeight * 0.3);
        int bonusHeight = new Random().nextInt((int)(canvasHeight * 0.3));

        int pipeWidth = (int)(canvasWidth * 0.15);
        int pipex = canvasWidth + (pipeWidth/2);

        int pipeHeightUp = (int)(canvasHeight * 0.2) + bonusHeight;
        int pipeyUp = pipeHeightUp / 2;

        int pipeHeightDown = (int)(canvasHeight * 0.2) + (bonusHeightMax - bonusHeight);
        int pipeyDown = (canvasHeight - pipeHeightDown) + (pipeHeightDown / 2);

        upPipe = new Pipe(this, pipex, pipeyUp, pipeWidth, pipeHeightUp, canvasWidth, canvasHeight);
        downPipe = new Pipe(this, pipex, pipeyDown, pipeWidth, pipeHeightDown, canvasWidth, canvasHeight);

        getGameObjects().add(upPipe);
        getGameObjects().add(downPipe);

        // SHIP
        ship = new Ship(getContext(),
                (int)(width * 0.1), (int)(height * 0.5),
                (int)(width * 0.16), (int)(height * 0.04),
                width, height, getAudioPlayer());

        getGameObjects().add(ship);
        getTouchEventObservers().add(ship);

        upPipe.getCollisionObjects().add(ship);
        downPipe.getCollisionObjects().add(ship);

        // SCORE
        score = new Score(this, 10, 50, 0, 0, width, height);
        getGameObjects().add(score);

        gameThread = new GameThread(this, holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            gameThread.setRunning(false);
            gameThread.join();
        }catch(InterruptedException e)  { }
    }


    public void reinitPipes() {
        score.increaseScore();

        int bonusHeightMax = (int)(canvasHeight * 0.3);
        int bonusHeight = new Random().nextInt((int)(canvasHeight * 0.3));

        int pipeWidth = (int)(canvasWidth * 0.15);
        int pipex = canvasWidth + (pipeWidth/2);

        int pipeHeightUp = (int)(canvasHeight * 0.2) + bonusHeight;
        int pipeyUp = pipeHeightUp / 2;

        int pipeHeightDown = (int)(canvasHeight * 0.2) + (bonusHeightMax - bonusHeight);
        int pipeyDown = (canvasHeight - pipeHeightDown) + (pipeHeightDown / 2);

        upPipe.reinit(pipex, pipeyUp, pipeWidth, pipeHeightUp);
        downPipe.reinit(pipex, pipeyDown, pipeWidth, pipeHeightDown);
    }

    public void setEnd(boolean end) {
        this.end = end;

        if (isEnd()){
            for (GameObject o: getGameObjects()) {
                o.end(getContext());
            }
            EndScreen endScreen = new EndScreen(this,
                            canvasWidth / 2, canvasHeight / 2,
                            (int)(canvasWidth * 0.7), (int)(canvasHeight * 0.2),
                            canvasWidth, canvasHeight);

            getGameObjects().add(endScreen);
            getTouchEventObservers().add(endScreen);

            ((GameActivity)getContext()).refreshScore(score.toInt());
        }
    }

}