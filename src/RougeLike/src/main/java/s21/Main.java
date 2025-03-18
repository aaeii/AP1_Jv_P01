package s21;

import s21.domain.Level;
import s21.domain.Map;
import s21.domain.Room;
import s21.presentation.Print;

import java.io.IOException;

import static s21.domain.GameConstants.MAP_HEIGHT;
import static s21.domain.GameConstants.MAP_WIDTH;

public class Main {
    public static void main(String[] args) throws IOException {
        Level level = new Level();
        level.generate_level();
        Map map = new Map();
        map.level_to_map(level, map);
//        for (int i=0; i < MAP_HEIGHT; i++ ) {
//            for (int j = 0; j < MAP_WIDTH; j++) {
//                System.out.print(map.playground[i][j]);
//            }
//            System.out.println();
//        }
        Print printStep = new Print();
        printStep.setField(map.getPlayground());
        printStep.printField();
//        for (int i=0; i < MAP_HEIGHT; i++ ) {
//            for (int j = 0; j < MAP_WIDTH; j++) {
//                System.out.print(map.playground[i][j]);
//            }
//            System.out.println();
//        }
//        Print print1 = new Print(map.getPlayground());
//        print1.printField();
    }

}
