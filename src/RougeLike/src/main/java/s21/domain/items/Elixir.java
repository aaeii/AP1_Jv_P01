package s21.domain.items;


import s21.domain.Position;

import java.sql.Date;

import static s21.domain.GameConstants.*;

public class Elixir extends Item {

        private Date dateStart;
        private int agility;
        private int strength;
        private int maxHealth;
        private long duration;

        public Elixir(){
            super();
            this.dateStart = null;
            this.agility = 0;
            this.strength = 0;
            this.maxHealth = 0;
            this.duration = 0;

        }

        public Elixir( int elixir_type) {
            super();
            setSymbol(ELIXIR_CHAR);
            setType(ELIXIR);
            switch (elixir_type) {
                case HEALTH_ELIXIR: //( "Health Elixir", 0, 0, 50, 60000)
                    this.name = "Health Elixir";
                    this.agility = 0;
                    this.strength = 0;
                    this.maxHealth = MAX_HEALTH;
                    this.duration = 60000;
                    break;
                case STRENGTH_ELIXIR: //("Strength Elixir", 5, 10, 0, 60000)
                    this.name = "Strength Elixir";
                    this.agility = 5;
                    this.strength = 10;
                    this.maxHealth = 0;
                    this.duration = 60000;
                    break;
                case AGILITY_ELIXIR:
                    this.name = "Agility Elixir";
                    this.agility = 10;
                    this.strength = 0;
                    this.maxHealth = 0;
                    this.duration = 50000;
                    break;
                default:
                    break;
            }

        }

    @Override
    public void action(Position player_pos, Position  top_left, Position bot_right){

        }

        public boolean isExpired() {
            return dateStart.getTime() + duration < System.currentTimeMillis();
        }

        public Date apply() {
            dateStart = new Date(System.currentTimeMillis());

            return dateStart;
        }

        public int getAgility() {
            return agility;
        }

        public int getStrength() {
            return strength;
        }

        public int getMaxHealth() {
            return maxHealth;
        }


    }


