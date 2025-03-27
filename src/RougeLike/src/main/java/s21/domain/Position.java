package s21.domain;

public class Position {
    private int x;
    private int y;
    private boolean visibility;

    public Position() {
        this.x = 0;
        this.y = 0;
        this. visibility = false;
    }

    public Position(int x, int y, boolean visibility) {
        this.x = x;
        this.y = y;
        this. visibility = visibility;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNew(int x, int y, boolean visibility) {
        this.x = x;
        this.y = y;
        this. visibility = visibility;
    }
}
