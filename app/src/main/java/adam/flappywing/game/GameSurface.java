package adam.flappywing.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import adam.flappywing.activities.GameActivity;
import adam.flappywing.game.objects.Background;
import adam.flappywing.game.objects.EndScreen;
import adam.flappywing.game.objects.GameObject;
import adam.flappywing.game.objects.Score;
import adam.flappywing.game.objects.ship.Ship;
import adam.flappywing.game.objects.pipes.PipesManager;
import adam.flappywing.utils.AudioPlayer;

// TODO nejak napojit ovladani pres gyroskop
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private PipesManager pipesManager;
    private Score score;

    private int canvasWidth;
    private int canvasHeight;

    private boolean end;

    private AudioPlayer audioPlayer;

    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> touchEventObservers = new ArrayList<>();
    private List<GameObject> pipesBehindObservers = new ArrayList<>();

    public GameSurface(Context context)  {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);

        audioPlayer = ((GameActivity) context).getAudioPlayer();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { }

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

    // TODO smazat pokud se pouzije senzor
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (GameObject o : getTouchEventObservers()){
            o.touchEvent(getContext(), event);
        }
        return true;
    }

    public void onPipesBehind() {
        for (GameObject o : getPipesBehindObservers()) {
            o.pipesBehindEvent();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        canvasWidth = width;
        canvasHeight = height;

        // BACKGROUND
        addGameObject(new Background(getContext(), width, height));

        // SHIP
        Ship ship = new Ship(getContext(), width, height, getAudioPlayer());
        addGameObject(ship);
        addTouchObserver(ship);

        // PIPES
        pipesManager = new PipesManager(this, this.canvasWidth, this.canvasHeight);
        pipesManager.addCollisionObject(ship);
        addGameObject(pipesManager.getUpperPipe());
        addGameObject(pipesManager.getLowerPipe());
        addPipesBehindObserver(pipesManager.getUpperPipe());
        addPipesBehindObserver(pipesManager.getLowerPipe());

        // SCORE (add as last, to be on top of the surface instead of behind other elements)
        score = new Score(getContext(), width, height);
        addGameObject(score);
        addPipesBehindObserver(score);

        // THREAD
        gameThread = new GameThread(this, holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    public void signalEnd(boolean end) {
        this.end = end;

        if (isEnd()){
            for (GameObject o: getGameObjects()) {
                o.end(getContext());
            }
            EndScreen endScreen = new EndScreen(this, canvasWidth, canvasHeight);

            addGameObject(endScreen);
            addTouchObserver(endScreen);

            ((GameActivity)getContext()).signalEnd();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            gameThread.setRunning(false);
            gameThread.join();
        } catch(InterruptedException ignored)  { }
    }

    public void finish(){
        ((GameActivity)getContext()).end();
    }

    public void addGameObject(GameObject g) {
        this.getGameObjects().add(g);
    }

    public void addTouchObserver(GameObject g) {
        this.getTouchEventObservers().add(g);
    }

    public void addPipesBehindObserver(GameObject g) {
        this.getPipesBehindObservers().add(g);
    }

    public boolean isEnd() {
        return end;
    }

    public Score getScore() {
        return score;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getTouchEventObservers() {
        return touchEventObservers;
    }

    public List<GameObject> getPipesBehindObservers() {
        return pipesBehindObservers;
    }
}