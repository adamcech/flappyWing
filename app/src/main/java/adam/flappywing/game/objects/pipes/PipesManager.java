package adam.flappywing.game.objects.pipes;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import adam.flappywing.game.GameSurface;
import adam.flappywing.game.objects.ship.Ship;

public class PipesManager {

    protected int bonusHeightMax;
    protected int bonusHeight;

    protected int canvasHeight;

    protected Pipe upperPipe;
    protected Pipe lowerPipe;

    protected int managerStep;

    public PipesManager(GameSurface gameSurface, int canvasWidth, int canvasHeight) {
        this.canvasHeight = canvasHeight;
        this.managerStep = 0;

        this.regenerateBonusHeight();

        upperPipe = new UpperPipe(gameSurface, this, canvasWidth, canvasHeight);
        lowerPipe = new LowerPipe(gameSurface, this, canvasWidth, canvasHeight);
    }

    protected void regenerateBonusHeight() {
        this.bonusHeightMax = (int)(canvasHeight * 0.3);
        this.bonusHeight = new Random().nextInt((int)(canvasHeight * 0.3));

        this.managerStep++;
    }

    public void addCollisionObject(Ship ship) {
        upperPipe.getCollisionObjects().add(ship);
        lowerPipe.getCollisionObjects().add(ship);
    }

    public Pipe getUpperPipe() {
        return upperPipe;
    }

    public Pipe getLowerPipe() {
        return lowerPipe;
    }

    public int getBonusHeightMax() {
        return bonusHeightMax;
    }

    public int getBonusHeight() {
        return bonusHeight;
    }
}
