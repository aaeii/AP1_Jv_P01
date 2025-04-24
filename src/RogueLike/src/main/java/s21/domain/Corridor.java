package s21.domain;

import static s21.domain.GameConstants.*;
import static s21.domain.GameConstants.TOP_TO_BOTTOM_CORRIDOR;

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

    public boolean checkCorridor(Position player_position){
        int x = player_position.getX();
        int y = player_position.getY();
        int minX= Math.min(points[0].getX(), points[3].getX());
        int minY= Math.min(points[0].getY(), points[3].getY());
        int maxX= Math.max(points[0].getX(), points[3].getX());
        int maxY= Math.max(points[0].getY(), points[3].getY());
            switch (type)
            {
                case LEFT_TO_RIGHT_CORRIDOR:
                    if (y == points[0].getY() && x >= minX && x <= points[1].getX()
                            || x == points[1].getX() && y >= minY && y<=maxY
                            || y == points[3].getY() && x > points[2].getX() && x <= maxX)
                        return true;
                    break;
                case TOP_TO_BOTTOM_CORRIDOR:
                    if (x == points[0].getX() && y >= minY && y <= points[1].getY()
                            || y == points[1].getY() && x >= minX && x<= maxX
                            || x == points[3].getX() && y > points[2].getY() && y <= maxY)
                        return true;
                    break;
            }
        return false;
    }
}
