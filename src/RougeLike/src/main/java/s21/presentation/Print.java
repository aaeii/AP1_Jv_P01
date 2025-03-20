package s21.presentation;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static s21.domain.GameConstants.*;


public class Print {
    private char[][] field;
//    private Character player;

    public  Print() {
        field = new char[MAP_HEIGHT][MAP_WIDTH];
    }

    public void setField(char[][] playground) {
        this.field = playground;
    }

    public void printField() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalSize screenSize = terminal.getTerminalSize();
        System.out.println(screenSize);
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.clear();

        for (int i=0; i < MAP_HEIGHT; i++ ){
            for (int j=0; j < MAP_WIDTH; j++ )
        screen.setCharacter(j, i, new TextCharacter(field[i][j]));
        }
        screen.refresh();
        screen.readInput();
        screen.stopScreen();
    }
}
