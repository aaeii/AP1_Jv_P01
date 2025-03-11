package s21.domain;

import static s21.domain.GameConstants.*;

public class Entity {
    int type; // enemy, item, player, exit
    int symbol; // выбрать из возможного пула
    Position pos;

    public void Entity() {
        type = PLAYER;
        symbol = '@';
        Position pos = new Position(1 , 1);
    }
}
