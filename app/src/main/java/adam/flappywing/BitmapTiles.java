package adam.flappywing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

class BitmapTiles{

    private int lx, ly;
    private int index, length;
    private int width, height;
    private int tileWidth, tileHeight;

    private Bitmap bitmap;

    public BitmapTiles(Context context, int tilesId, int lx, int ly, int length) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), tilesId);
        this.lx = lx;
        this.ly = ly;
        this.index = 0;
        this.length = length;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.tileWidth = width / lx;
        this.tileHeight = height / ly;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public Rect getCurr() {
        if (!isFinished()){
            int left = ((width / lx) * index) % width;
            int top = (int) ((height / ly) * Math.floor(index / ly));
            int right = left + tileWidth;
            int bottom = top + tileHeight;

            Rect tileRect = new Rect(left, top, right, bottom);

            index++;
            return tileRect;
        } else {
            return null;
        }
    }

    public boolean isFinished(){
        return index >= length;
    }

    public boolean isAcrossHalfway(){
        return index >= length / 2;
    }
}
