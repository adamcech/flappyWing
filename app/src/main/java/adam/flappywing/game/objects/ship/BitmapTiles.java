package adam.flappywing.game.objects.ship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BitmapTiles{

    private int lx, ly;
    private int index, length;
    private float width, height;
    private float tileWidth, tileHeight;

    private Bitmap bitmap;
    private List<Bitmap> bitmaps = new ArrayList<>();

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

        this.initBitmaps();
    }

    public Bitmap getNextBitmap() {
        if (!isFinished()){
            index++;
            return this.bitmaps.get(index - 1);
        } else {
            return null;
        }
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    /*public Rect getCurr() {
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
    }*/

    private void initBitmaps() {
        for (int index = 0; index < length; index++) {
            int left = (int) (tileWidth * (index % this.lx));
            int top = (int) ((height / ly) * Math.floor(index / ly));

            if (left + tileWidth > width) {
                left = 0;
            }

            Bitmap bitmap = Bitmap.createBitmap(this.bitmap, left, top, (int)tileWidth, (int)tileHeight);

            this.bitmaps.add(bitmap);
        }
    }

    public boolean isFinished(){
        return index >= length;
    }

    public boolean isAcrossHalfway(){
        return index >= length / 2;
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return length;
    }
}
