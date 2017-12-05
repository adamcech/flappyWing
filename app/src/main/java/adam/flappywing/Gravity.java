package adam.flappywing;


import android.util.Log;

class Gravity {

    private double y;
    private double yMax;
    private double yDecrease;
    private double yIncrease;

    public double getY(){
        return y;
    }
    public double getyMax() {
        return yMax;
    }
    public double getyDecrease() {
        return yDecrease;
    }
    public double getyIncrease() {
        return yIncrease;
    }


    public Gravity(int canvasHeight) {
        y = 0;
        yDecrease = canvasHeight * 0.0003;
        yIncrease = -(canvasHeight * 0.01);
        yMax = canvasHeight * 0.0125;
    }

    public void setY(double val){
        if (val > getyMax()){
            y = getyMax();
        } else if (val < -getyMax()){
            y = -getyMax();
        } else {
            y = val;
        }
    }

    public void speedUp() {
        if (getY() >= 0){
            setY(getyIncrease());
        } else {
            setY(getY() * 1.75);
        }
    }

    public void update() {
        setY(getY() + getyDecrease());
    }

    public void end() {
        y = 0;
        yIncrease = 0;
        yDecrease = 0;
        yMax = 0;
    }
}
