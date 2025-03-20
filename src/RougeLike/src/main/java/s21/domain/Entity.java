package s21.domain;

import static s21.domain.GameConstants.*;

public class Entity {
    int type; // enemy, item, player, exit
    int symbol; // выбрать из возможного пула
    Position position;

    public Entity() {
        type = -1;
        symbol = -1;
        Position position = new Position();
    }

    public Entity(int type, int symbol, Position position) {
        this.type = type;
        this.symbol = symbol;
        this.position = new Position(position.getX(), position.getY());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public Position generate_entity_coords(Room room){
        Position pos  = new Position();
        do {
            int x = (int) ((Math.random() * (room.getBot_right().getX() - room.getTop_left().getX() - 1)) + room.getTop_left().getX() + 1);
            int y = (int) ((Math.random() * (room.getBot_right().getY() - room.getTop_left().getY() - 1)) + room.getTop_left().getY() + 1);
            pos.setNew(x,y);
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
