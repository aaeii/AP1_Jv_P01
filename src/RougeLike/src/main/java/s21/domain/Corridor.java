package s21.domain;

import static s21.domain.GameConstants.UNINITIALIZED;

public class Corridor {
    private final Position[] points;
    private final int points_cnt;
    private int type;

    public Corridor() {
        this.points_cnt = 4;
        points = new Position[4];
        type = UNINITIALIZED;
    }

    public Corridor(int points_cnt) {
        this.points_cnt = points_cnt;
        points = new Position[4];
        type = UNINITIALIZED;
    }

    public void setType(int type){
        this.type=type;
    }

    public void setPoints(Position point, int i){
        this.points[i] = point;
    }

    public int getType()
    {
        return type;
    }

    public Position getPoints(int i)
    {
        return points[i];
    }
}
