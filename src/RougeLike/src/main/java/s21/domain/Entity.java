package s21.domain;

import static s21.domain.GameConstants.*;

abstract class Entity {
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
        this.position = new Position(position.getX(), position.getY(), false);
    }
     abstract void move(Character player_pos, Position  top_left, Position bot_right);

    public void attack(Character p) {

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



}
