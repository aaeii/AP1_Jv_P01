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
                    rooms[i][j].setConnections(room, k);
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
//        Level currentLevel = new Level();
        generate_sectors();
//        generate_connections(dungeon);
        generate_rooms_geometry();
//        generate_corridors_geometry(dungeon);
    }

    public void generate_sectors()
    {
        while (room_cnt < 9)
        {
            int sector = 0;
            for (int i = 0; i < ROOMS_PER_SIDE ; i++){
                for (int j = 0; j < ROOMS_PER_SIDE; j++, sector++){
                    if (Math.random() < ROOM_CHANCE &&
                            rooms[i][j].getSector() == 0 ){
                            rooms[i][j] = new Room(i, j, sector);
                            roomsSequence.add(rooms[i][j]);
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
        for (int i = 0; i < ROOMS_PER_SIDE ; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
                if (rooms[i][j].getSector() != 0)
                {
                    generate_corners(rooms[i][j], (i) * SECTOR_HEIGHT, (j) * SECTOR_WIDTH);
//                    generate_doors(&dungeon->rooms[i][j]);
                }

    }

    public void generate_corners(Room room, int offset_y, int offset_x)
    {
        int top_leftY = (int) ((Math.random() * (double) ((MAP_HEIGHT / 3) - 6) / 2) + offset_y + 1);
        int top_leftX = (int) ((Math.random() * (double) (MAP_WIDTH / 3 - 6) / 2) + offset_x + 1);
        Position Top_left = new Position(top_leftX, top_leftY);
        room.setTop_left(Top_left);
        System.out.println(room.getTop_left().getX());
        int bot_rightY =  top_leftY + (int) ((Math.random() * (double) (SECTOR_HEIGHT - 5)) + 4);
        int bot_rightX = top_leftX + (int) ((Math.random() * (double) (SECTOR_WIDTH - 5)) +4);
        Position Bot_right = new Position(bot_rightX, bot_rightY);
        room.setBot_right(Bot_right);
    }
}
