package s21.domain.items;

import s21.domain.Entity;
import s21.domain.Position;

public abstract class Item extends Entity {

    public Item(int type, int symbol, Position position) {
        super(type,  symbol, position);
    }
    public Item(){super();}

    protected String name;

    public String getName() {
        return name;
    }

}

