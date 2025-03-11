package s21.presentation;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import s21.domain.Map;

import java.io.IOException;

import static s21.domain.GameConstants.MAP_HEIGHT;
import static s21.domain.GameConstants.MAP_WIDTH;


public class Print {
    private char[][] field;
//    private Character player;

    public Print(char[][] field) {
        this.field = field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public void printField() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.clear();

        for (int i=0; i < MAP_HEIGHT; i++ ){
            for (int j=0; j < MAP_WIDTH; j++ )
        screen.setCharacter(i, i, new TextCharacter(field[i][j]));
        }
        screen.refresh();

        screen.readInput();
        screen.stopScreen();
    }
}
