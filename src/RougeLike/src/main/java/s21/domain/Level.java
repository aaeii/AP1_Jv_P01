package s21.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

import static s21.domain.GameConstants.*;
import s21.domain.*;

public class Level {

    private List<Room> roomsSequence; //[MAX_ROOMS_NUMBER]
    private List<Corridor> corridors; //MAX_CORRIDORS_NUMBER
    private Room[][] rooms; //[ROOMS_PER_SIDE + 2][ROOMS_PER_SIDE + 2]
    private int room_cnt;
    private int  corridors_cnt;
//    private boolean roomsConnectivity;

    public Level() {
        roomsSequence = new ArrayList<>();
        corridors = new ArrayList<>();
        rooms = new Room[ROOMS_PER_SIDE][ROOMS_PER_SIDE];
        room_cnt = 0;
        corridors_cnt = 0;
//        roomsConnectivity = false;

        for (int i = 0; i < ROOMS_PER_SIDE; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
            { Room currentRoom = new Room();
                rooms[i][j] = currentRoom;
                for (int k = 0; k < 4; k++)
                {
                    Room room = new Room();
                    rooms[i][j].setConnections(null, k);
                    Position pos = new Position(UNINITIALIZED, UNINITIALIZED);
                    rooms[i][j].setDoors(pos, k);
                }
                rooms[i][j].setEntities(0);
            }
        roomsSequence = new ArrayList<>();
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++){
            Room room = new Room();
            roomsSequence.add(room);
            }
        corridors = new ArrayList<>();
        for (int i = 0; i < MAX_CORRIDORS_NUMBER; i++){
            Corridor corr = new Corridor();
            corridors.add(corr);
        }

}

    public List<Room> getRoomsSequence() {
        return roomsSequence;
    }

    public Room getRoomsSequence(int i) {
        return roomsSequence.get(i);
    }

    public void setRoomsSequence(int i, Room room) {
        this.roomsSequence.set(i, room);
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public int getRoom_cnt() {
        return room_cnt;
    }

    public int getCorridors_cnt() {
        return corridors_cnt;
    }

    public void generate_level()
    {
        generate_sectors();
        generate_connections();
        generate_rooms_geometry();
        generate_corridors_geometry();
    }

    public void generate_sectors()
    {
        while (room_cnt < 6)
        {
            int sector = 0;
            for (int i = 0; i < ROOMS_PER_SIDE ; i++){
                for (int j = 0; j < ROOMS_PER_SIDE; j++, sector++){
                    if (Math.random() < ROOM_CHANCE &&
                            rooms[i][j].getSector() == -1 ){
                            rooms[i][j] = new Room(i, j, sector);
                            setRoomsSequence(room_cnt, rooms[i][j]);
                            room_cnt++;
                    }
                }
            }
        }
        roomsSequence.sort(new RoomsComparator());
    }

    public class RoomsComparator implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            return o1.getSector() - o2.getSector();
        }
    }


    public void generate_rooms_geometry(){
        int count = 0;
        for (int i = 0; i < ROOMS_PER_SIDE ; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
                if (rooms[i][j].getSector() != -1)
                {
                    generate_corners(rooms[i][j], (i) * SECTOR_HEIGHT, (j) * SECTOR_WIDTH);
                    generate_doors(rooms[i][j]);
                    for (int k = 0; k < roomsSequence.size(); k++){
                        if (roomsSequence.get(k).getSector() == rooms[i][j].getSector()){
                            roomsSequence.get(k).setBot_right(rooms[i][j].getBot_right());
                            roomsSequence.get(k).setTop_left(rooms[i][j].getTop_left());
                        }
                    }
                }
    }

    public void generate_corners(Room room, int offset_y, int offset_x)
    {
        int top_leftY = (int) ((Math.random() * (double) (SECTOR_HEIGHT - 6) / 2) + offset_y + 1);
        int top_leftX = (int) ((Math.random() * (double) (SECTOR_WIDTH - 6) / 2) + offset_x + 1);
        Position Top_left = new Position(top_leftX, top_leftY);
        room.setTop_left(Top_left);
        int bot_rightY =  top_leftY + (int) ((Math.random() * (double) (SECTOR_HEIGHT - SECTOR_HEIGHT/2)) + 3);
        int bot_rightX = top_leftX + (int) ((Math.random() * (double) (SECTOR_WIDTH - SECTOR_WIDTH/2)) + 3 );
        Position Bot_right = new Position(bot_rightX, bot_rightY);
        room.setBot_right(Bot_right);
    }

    public void generate_doors(Room room){
        if (room.getConnections(TOP) != null)
        {
            int xDoor = (int) (Math.random() * (double) ((room.getBot_right().getX()-room.getTop_left().getX() - 1)) + room.getTop_left().getX()+1);
            Position doorPos = new Position(xDoor, room.getTop_left().getY());
            room.setDoors(doorPos, TOP);

        }

        if (room.getConnections(RIGHT) != null)
        {
            int yDoor = (int) (Math.random() * (double) ((room.getBot_right().getY()-room.getTop_left().getY() - 1)) + room.getTop_left().getY()+1);
            Position doorPos = new Position(room.getBot_right().getX(), yDoor);
            room.setDoors(doorPos, RIGHT);
        }

        if (room.getConnections(BOTTOM) != null)
        {
            int xDoor = (int) (Math.random() * (double) ((room.getBot_right().getX()-room.getTop_left().getX() - 1)) + room.getTop_left().getX()+1);
            Position doorPos = new Position(xDoor, room.getBot_right().getY());
            room.setDoors(doorPos, BOTTOM);
        }
        if (room.getConnections(LEFT) != null)
        {
            int yDoor = (int) (Math.random() * (double) ((room.getBot_right().getY()-room.getTop_left().getY() - 1)) + room.getTop_left().getY()+1);
            Position doorPos = new Position(room.getTop_left().getX(), yDoor);
            room.setDoors(doorPos, LEFT);
        }


    }

    void generate_connections()
    {
        generate_primary_connections();
//        generate_secondary_connections();
    }

    void generate_primary_connections()
    {
        for (int i = 0; i < ROOMS_PER_SIDE; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
                if ((rooms[i][j].getSector() != UNINITIALIZED) && (rooms[i][j] != null))
                {
                    if ((i > 0))
                    {
                        if ((rooms[i - 1][j].getSector() != UNINITIALIZED) && (rooms[i - 1][j] != null))
                        rooms[i][j].setConnections( rooms[i - 1][j], TOP );
                    }
                    if (j < (ROOMS_PER_SIDE - 1) )
                    {
                        if ((rooms[i][j + 1].getSector() != UNINITIALIZED) && (rooms[i][j + 1] != null))
                        {
                            rooms[i][j].setConnections(rooms[i][j + 1], RIGHT);
                        }
                    }
                    if (i < ROOMS_PER_SIDE - 1)
                    {
                        if ((rooms[i + 1][j].getSector() != UNINITIALIZED) && (rooms[i + 1][j] != null))
                            rooms[i][j].setConnections(rooms[i + 1][j], BOTTOM);
                    }
                    if (j > 0) {
                    if ((rooms[i][j - 1].getSector() != UNINITIALIZED) && (rooms[i][j - 1]!= null))
                        rooms[i][j].setConnections(rooms[i][j - 1], LEFT);
                    }
                }

    }

//    void generate_secondary_connections()
//    {
//        for (int i = 0; i < roomsSequence.size() - 1; i++){
//            if (getRoomsSequence(i).getSector() != UNINITIALIZED)
//            {
//                Room current = getRoomsSequence(i);
//                Room next = getRoomsSequence(i + 1);
//
//                if (current.getGrid_i() == next.getGrid_i() && next.getConnections(LEFT) == null)
//                {
//                    current.setConnections(next, RIGHT);
//                    next.setConnections(current, LEFT);
//                }
//                else if (current.getGrid_i() - next.getGrid_i() == -1 && current.getConnections(BOTTOM) == null)
//                {
//                    if (current.getGrid_j() < next.getGrid_j() && next.getConnections(LEFT) == null)
//                    {
//                        current.setConnections(next, BOTTOM);
//                        next.setConnections(current, LEFT);
//                    }
//                    else if (current.getGrid_j() > next.getGrid_j() && next.getConnections(RIGHT) == null)
//                    {
//                        current.setConnections(next, BOTTOM);
//                        next.setConnections(current, RIGHT);
//                    }
//                    else if (current.getGrid_j() > next.getGrid_j() && current.getConnections(BOTTOM) == null &&
//                            i < room_cnt - 2)
//                    {
//                        current.setConnections(roomsSequence.get(i + 2), BOTTOM);
//                        roomsSequence.get(i + 2).setConnections(current, RIGHT);
//                    }
//                }
//                else if (current.getGrid_i() - next.getGrid_i() == -2 && next.getConnections(TOP) == null)
//                {
//                    current.setConnections(next, BOTTOM);
//                    next.setConnections(current, TOP);
//                }
//            }
//        }
//    }

    public void generate_corridors_geometry(){
        for (int i = 0; i < ROOMS_PER_SIDE; i++){
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
            {
                Room current = rooms[i][j];

                if (current.getConnections(RIGHT) != null && current.getConnections(RIGHT).getConnections(LEFT) == current)
                    System.out.println(current.getSector());
//                generate_left_to_right_corridor(dungeon, cur_room, cur_room->connections[RIGHT], &dungeon->corridors[dungeon->corridors_cnt++]);

            }
        }
    }

}

