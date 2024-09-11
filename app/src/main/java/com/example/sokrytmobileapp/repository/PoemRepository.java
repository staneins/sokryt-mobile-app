package com.example.sokrytmobileapp.repository;

import android.app.Application;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.data.PoemDao;
import com.example.sokrytmobileapp.data.PoemDatabase;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoemRepository {
    private final PoemDao poemDao;
    private final ExecutorService executorService;

    public PoemRepository(Application application) {
        PoemDatabase db = PoemDatabase.getDatabase(application);
        poemDao = db.poemDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<Poem> getAllPoems() {
        return poemDao.getAllPoems();
    }

    public void insert(final Poem poem) {
        executorService.execute(() -> poemDao.insertPoem(poem));
    }

    public void loadPoemsFromJson(Context context, PoemRepository poemRepository) {
        String jsonString = loadJsonFromAssets(context);
        if (jsonString != null) {
            // парсинг json
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Poem>>() {}.getType();
            List<Poem> poems = gson.fromJson(jsonString, listType);

            // добавление стихов в БД
            for (Poem poemJson : poems) {
                Poem poem = new Poem(poemJson.title, poemJson.text);
                poemRepository.insert(poem);
            }
        }
    }

    //парсинг json файла из папки Assets в телефоне
    private String loadJsonFromAssets(Context context) {
        StringBuilder jsonBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try (InputStream inputStream = assetManager.open("poems.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonBuilder.toString();
    }
}
