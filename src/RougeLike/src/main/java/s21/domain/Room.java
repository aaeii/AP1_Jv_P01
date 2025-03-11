package s21.domain;

import java.util.List;

import static s21.domain.GameConstants.UNINITIALIZED;

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
//    private Entity [] entities; //MAX_ENTITIES_PER_ROOM
    private int entities_cnt;

    public Room(){
        grid_i = 0;
        grid_j = 0;
        this.sector = 0;
        connections = new Room[4];
        doors = new Position[4];
        top_left = new Position();
        bot_right = new Position();
        entities_cnt = 0;
    }

    public Room(int i, int j, int sector){
        grid_i = i;
        grid_j = j;
        this.sector = sector;
        connections = new Room[4];
        for (int k = 0; k < 4; k++){
            Room currentRoom = new Room();
            connections[i] = currentRoom;
        }
        doors = new Position[4];
        for (int k = 0; k < 4; k++){
            Position currentPosition = new Position();
            doors[i] = currentPosition;
        }
        top_left = new Position();
        bot_right = new Position();
        entities_cnt = 0;
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

    public void setConnections(Room room, int i) {
        this.connections[i] = room;
    }

    public void setDoors(Position pos, int i) {
        this.doors[i] = pos;
    }

    public void setConnections(Room[] connections) {
        this.connections = connections;
    }

    public void setEntities(int entities_cnt) {
        this.entities_cnt = entities_cnt;
    }

    public void setTop_left(Position position) {
        this.top_left = position;
    }

    public Position getTop_left() {
        return  top_left;
    }

    public void setBot_right (Position position) {
        this.bot_right = position;
    }

    public Position getBot_right() {
        return bot_right;
    }

}
