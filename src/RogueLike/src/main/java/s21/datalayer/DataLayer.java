package s21.datalayer;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import s21.domain.Character;
import com.google.gson.reflect.TypeToken;

public class DataLayer {
    private static final String SAVE_FILE = "src/RogueLike/src/game_progress.json";

    public static void saveProgress(Character player) {
//        Gson gson = new Gson();
        List<Character> players = loadProgress(); // Загружаем текущий прогресс
        players.add(player); // Добавляем нового персонажа
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("characters", gson.toJsonTree(players)); // Сохраняем список персонажей
            gson.toJson(jsonObject, writer); // Записываем в файл
        } catch (IOException e) {
//            return;
        }
    }
    public static List<Character> loadProgress() {
        File file = new File(SAVE_FILE);

        // Если файл не существует или пустой, возвращаем пустой список
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            // Проверяем, что JSON не пустой и является объектом
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                System.err.println("Файл не содержит JSON-объект");
                return new ArrayList<>();
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Type listType = new TypeToken<List<Character>>() {
            }.getType();
            List<Character> characters = gson.fromJson(jsonObject.get("characters"), listType);
            return characters != null ? characters : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}
