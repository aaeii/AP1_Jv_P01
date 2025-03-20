package s21;

import s21.domain.GameSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameSession gameSession = new GameSession();
        gameSession.gameLoop();
    }

}
