package s21.domain;

import java.util.List;

import static s21.domain.GameConstants.*;

public class Room {
    private  int sector;
    private  int grid_i;
    private  int grid_j;
    private Room[] connections; //4
    private Position[] doors; //4
    private Position top_left;
    private Position bot_right;
    //    private boolean playerExit;
//    private boolean playerSpawn;
    private final Entity [] entities; //MAX_ENTITIES_PER_ROOM
    private int entities_cnt;
    private final Enemy [] enemies;
    private int enemies_cnt;



    public Room(){
        grid_i = 0;
        grid_j = 0;
        this.sector = -1;
        connections = new Room[4];
        doors = new Position[4];
        top_left = new Position();
        bot_right = new Position();
        entities = new Entity[MAX_ENTITIES_PER_ROOM];
        entities_cnt = 0;
        enemies = new Enemy[MAX_ENTITIES_PER_ROOM];
        enemies_cnt=0;
    }

    public Room(int i, int j, int sector){
        grid_i = i;
        grid_j = j;
        this.sector = sector;
        connections = new Room[4];
        for (int k = 0; k < 4; k++){
            connections[i] = null;
        }
        doors = new Position[4];
        for (int k = 0; k < 4; k++){
            Position currentPosition = new Position();
            doors[k] = currentPosition;
        }

        entities = new Entity[MAX_ENTITIES_PER_ROOM];
        for (int k = 0; k < MAX_ENTITIES_PER_ROOM; k++){
            entities[k] = null;
        }
        enemies = new Enemy[MAX_ENTITIES_PER_ROOM];
        for (int k = 0; k < MAX_ENTITIES_PER_ROOM; k++){
            enemies[k] = null;
        }
        top_left = new Position();
        bot_right = new Position();
        entities_cnt = 0;
        enemies_cnt=0;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getGrid_i() {
        return grid_i;
    }

    public void setGrid_i(int i) {
        this.grid_i = i;
    }

    public int getGrid_j() {
        return grid_j;
    }

    public void setGrid_j(int j) {
        this.grid_j = j;
    }

    public Room[] getConnections() {
        return connections;
    }

    public Room getConnections(int i) {
        return connections[i];
    }

    public int getEntities_cnt(){
        return entities_cnt;
    }

    public void setEntities_cnt(int entities_cnt) {
        this.entities_cnt = entities_cnt;
    }

    public void setConnections(Room room, int i) {
        this.connections[i] = room;
    }

    public void setDoors(Position pos, int i) {
        this.doors[i] = pos;
    }

    public void setConnections(Room[] connections) {
        this.connections = connections;
    }

    public void setEntities(Entity entity) {
        this.entities[entities_cnt] = entity;
        this.entities_cnt++;
    }

    public Entity getEntities(int i) {
        return entities[i];
    }
    //Enemy
    public void setEnemies(Enemy enemy) {
        this.enemies[entities_cnt] = enemy;
        this.entities_cnt++;
    }
    public Enemy getEnemies(int i) {
        return enemies[i];
    }
    public int getEnemies_cnt(){
        return enemies_cnt;
    }

    public void setEnemies_cnt(int enemies_cnt) {
        this.enemies_cnt = enemies_cnt;
    }

    public void setTop_left(Position position) {
        this.top_left = position;
    }

    public Position getTop_left() {
        return  top_left;
    }

    public Position getDoors(int i) {
        return doors[i];
    }

    public void setBot_right (Position position) {
        this.bot_right = position;
    }

    public Position getBot_right() {
        return bot_right;
    }

    public boolean checkInRoomEntities(Position position){
        for (int i = 0; i < entities_cnt; i++) {
            if (entities[i].getPosition().getX() == position.getX() && entities[i].getPosition().getY() == position.getY())
                return true;
        }
        return false;
    }

    public boolean checkIsItExit(Position position){
        for (int i = 0; i < entities_cnt; i++) {
            if (entities[i].getPosition().getX() == position.getX() && entities[i].getPosition().getY() == position.getY() && entities[i].getType() == EXIT)
                return true;
        }
        return false;
    }


    public void moveEnemies(){

    }

}
