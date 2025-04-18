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

//public class Item extends Entity {
//    protected String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public Item(int type) {
//        super();
//        this.type = type;
//        switch (type) {
//            case GOLD:
//                break;
//            case FOOD:
//                break;
//            case SCROLL:
//                break;
//            case WEAPON:
//                break;
//            case ELIXIR:
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void move(Position player_pos, Position  top_left, Position bot_right){
//        switch (type){
//            case GOLD:
//                break;
//            default:
//                break;
//
//        }
//    }
//}
