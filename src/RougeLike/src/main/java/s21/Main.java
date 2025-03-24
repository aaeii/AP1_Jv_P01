package s21;

import com.googlecode.lanterna.terminal.Terminal;
import s21.controller.UserInput;
import s21.domain.GameSession;
import s21.presentation.Print;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameSession newGame = new GameSession();
        UserInput userInput = new UserInput();
        Print print = new Print();
        print.setField(newGame.getField());
        Terminal terminal = print.printField();
//        gameSession.gameLoop();
        while (!newGame.isReadyToStart()) {
            print.printRules(terminal);
            userInput.waitForInput(terminal);
            System.out.println(userInput.getAction());
            newGame.waitForStart(userInput.getAction());
        }
        if (newGame.isInGame()) terminal=print.printField();
        System.out.println(newGame.isInGame());
        while (newGame.isInGame()) {
            System.out.println(userInput.getAction());
            char ch = terminal.readInput().getCharacter();
//            userInput.waitForInput(terminal);
        terminal.flush();
            System.out.println(ch);
            newGame.gameStep(ch);
//            print.printInfoMessage(newGame.workWithInput(userInput.returnInput()), newGame.getCurrentLevelNumber());
            print.setField(newGame.getField());
            terminal=print.printField();
            terminal.flush();
        }
        terminal.close();
    }

}
