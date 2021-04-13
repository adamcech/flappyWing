package adam.flappywing.game.objects.pipes;

import android.util.Log;

import adam.flappywing.game.GameSurface;

public class UpperPipe extends Pipe {

    public UpperPipe(GameSurface gameSurface, PipesManager pipesManager, int canvasWidth, int canvasHeight) {
        super(gameSurface, pipesManager, canvasWidth, canvasHeight);
    }

    @Override
    public void reinit() {
        int canvasWidth = this.getCanvasWidth();
        int canvasHeight = this.getCanvasHeight();

        int pipeWidth = (int)(canvasWidth * 0.15);
        int pipex = canvasWidth + (pipeWidth/2);

        int pipeUpHeight = (int)(canvasHeight * 0.2) + this.pipesManager.getBonusHeight();
        int pipeUpY = pipeUpHeight / 2;

        this.reinit(pipex, pipeUpY, pipeWidth, pipeUpHeight);
    }

}
