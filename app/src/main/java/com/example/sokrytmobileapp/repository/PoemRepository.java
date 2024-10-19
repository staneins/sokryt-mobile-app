package com.example.sokrytmobileapp.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.data.PoemDao;
import com.example.sokrytmobileapp.data.PoemDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class PoemRepository {
    private final PoemDao poemDao;
    private final LiveData<List<Poem>> allPoems;
    private final Integer offset = 0;
    private Integer limit = 30;

    public PoemRepository(Application application) {
        PoemDatabase db = PoemDatabase.getDatabase(application);
        poemDao = db.poemDao();
        allPoems = poemDao.getAllPoems(offset, limit);
    }

    public LiveData<List<Poem>> getAllPoems() {
        return allPoems;
    }

    public void insert(final Poem poem) {
        Executors.newSingleThreadExecutor().execute(() -> poemDao.insertPoem(poem));
    }

    public void loadPoems() {

        if (allPoems.getValue() != null && !allPoems.getValue().isEmpty()) {
            Log.d(TAG, "Стихи уже загружены из базы данных");
            return;
        }

        String url = "https://sokryt.ru/json/stihi";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.d(TAG, "Обращаемся к адресу: " + url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Ошибка обращения к адресу", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    loadPoemsFromJson(jsonString);
                    Log.d(TAG, "Обращение прошло успешно. Длина ответа: " + jsonString.length());
                } else {
                    Log.e(TAG, "Ошибка обращения. Код: " + response.code());
                }
            }
        });
    }

    public void loadPoemsFromJson(String jsonString) {
        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<PoemJson>>() {
                }.getType();
                List<PoemJson> poemJsons = gson.fromJson(jsonString, listType);
                Log.d(TAG, "Распарсили " + poemJsons.size() + " стихов");

                for (PoemJson poemJson : poemJsons) {
                    Integer nid = poemJson.getNid();
                    Integer revisionUid = poemJson.getRevisionUid();
                    String title = poemJson.getTitle();
                    String body = poemJson.getBody();
                    if (nid != null && title != null && body != null) {

                        if (body != null) {
                            Log.d(TAG, "Заголовок стиха: " + title + ", Длина текста: " + body.length());
                        }

                        if (!isPoemExistInDatabase(nid, revisionUid)) {
                            Poem poem = new Poem(nid, revisionUid, title, body);
                            insert(poem);
                            Log.d(TAG, "Стих загружен в базу данных: " + nid + " " + title);
                        }
                    } else {
                        Log.w(TAG, "Пропущены обязательные поля: nid=" + nid + ", revisionUid=" + revisionUid);
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "Неудается разпарсить JSON", e);
            }
        } else {
            Log.e(TAG, "JSON строка пуста или null");
        }
    }

    public boolean isPoemExistInDatabase(Integer nid, Integer revisionUid) {
        LiveData<Poem> poem;
        if (revisionUid != null) {
            poem = poemDao.getPoemByUid(nid, revisionUid);
        } else {
            poem = poemDao.getPoemWithNullRevisionUid(nid);
        }

        if (poem != null) {
            Log.d(TAG, "Стих найден: " + nid);
            return true;
        } else {
            Log.d(TAG, "Стих не найден: " + nid);
            return false;
        }
    }
}