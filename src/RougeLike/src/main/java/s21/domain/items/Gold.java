package s21.domain.items;

import s21.domain.Position;

public class Gold extends Item {

    private int strength;

    public Gold(){
        super();
        this.strength = 0;
    }

    public Gold(int type, int symbol, Position position) {
        super(type,  symbol, position);
        this.strength = 0;
    }

    public void setStrength(int amount) {
        this.strength = amount;
    }

    @Override
    public String toString(){
        return  "Gold = " + strength;
    }
    @Override
    public void action(Position player_pos, Position top_left, Position bot_right) {
    }

    @Override
    public int getStrength() {
        return strength;
    }
}
