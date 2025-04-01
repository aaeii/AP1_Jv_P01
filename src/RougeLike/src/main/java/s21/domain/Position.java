package s21.domain;

import static s21.domain.GameConstants.OCCUPIED;
import static s21.domain.GameConstants.UNOCCUPIED;

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

    public Position generate_entity_coords(Room room){
        Position pos  = new Position();
        do {
            int x = (int) ((Math.random() * (room.getBot_right().getX() - room.getTop_left().getX() - 1)) + room.getTop_left().getX() + 1);
            int y = (int) ((Math.random() * (room.getBot_right().getY() - room.getTop_left().getY() - 1)) + room.getTop_left().getY() + 1);
            pos.setNew(x, y, false);
        }
        while (check_unoccupied(room, pos) == OCCUPIED);
        return pos;
    }

    private int check_unoccupied(Room room, Position pos)
    {
        int status = UNOCCUPIED;

        for (int i = 0; i < room.getEntities_cnt() && status == UNOCCUPIED; i++)
            if (room.getEntities(i).position.getX() == pos.getX() && room.getEntities(i).position.getY() == pos.getY())
                status = OCCUPIED;

        return status;
    }
}
