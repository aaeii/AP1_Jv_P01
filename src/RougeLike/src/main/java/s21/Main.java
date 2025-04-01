package s21;

import com.googlecode.lanterna.terminal.Terminal;
import s21.controller.UserInput;
import s21.domain.GameSession;
import s21.presentation.Print;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        GameSession newGame = new GameSession();
        Print print = new Print();
        print.setField(newGame.getField());
        Terminal terminal = print.createTerminal();
        UserInput userInput = new UserInput();
        print.printGame(terminal, newGame);
        while (!newGame.isReadyToStart()) {
            print.printRules(terminal);
            userInput.waitForInput(terminal);
            newGame.waitForStart(userInput.getAction());
        }
        if (newGame.isInGame()) print.printGame(terminal, newGame);
        while (newGame.isInGame()) {
            char ch = terminal.readInput().getCharacter();
            newGame.gameStep(ch);
            print.setField(newGame.getField());
            print.printGame(terminal, newGame);
            terminal.flush();
        }
            print.printResultOfGame(terminal, newGame);
            userInput.waitForResumeOrExit(terminal);
//        if (userInput.getAction() == 'n' || userInput.getAction() == 'N') {
//            print.printResultTable();
//            userInput.waitForExit();
//        }
        terminal.flush();
        terminal.close();
    }
}
