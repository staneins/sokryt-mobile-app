package com.example.sokrytmobileapp.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
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
    private final Application application;

    public PoemRepository(Application application) {
        this.application = application;
        PoemDatabase db = PoemDatabase.getDatabase(application);
        poemDao = db.poemDao();
    }

    public LiveData<List<Poem>> searchPoems(String query) {
        return poemDao.searchPoems("%" + query + "%");
    }

    public LiveData<List<Poem>> getSearchedPoemsWithPagination(int offset, int limit, String query) {
        return poemDao.getAllSearchedPoems(offset, limit, query);
    }

    public LiveData<List<Poem>> getSearchedPoemsBeforeOffset(int offset, String query) {
        return poemDao.getAllSearchedPoems(0, offset, query);
    }

    public LiveData<List<Poem>> getPoemsWithPagination(int offset, int limit) {
        return poemDao.getAllPoems(offset, limit);
    }

    public LiveData<List<Poem>> getPoemsBeforeOffset(int offset) {
        return poemDao.getAllPoems(0, offset);
    }

    public LiveData<List<Poem>> getFavouritePoems() {
        return poemDao.getFavouritePoems();
    }

    public void insert(final Poem poem) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                poemDao.insertPoem(poem);
                Log.d("PoemRepository", "Стих успешно вставлен: " + poem.getTitle());
            } catch (Exception e) {
                Log.e("PoemRepository", "Ошибка при вставке стиха: " + e.getMessage());
            }
    });
    }

    public void loadPoems() {

        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(application, "Обновляем стихи", Toast.LENGTH_LONG).show()
        );

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
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(application, "Ошибка обновления стихов", Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    loadPoemsFromJson(jsonString);
                    Log.d(TAG, "Обращение прошло успешно. Длина ответа: " + jsonString.length());
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(application, "Стихи обновлены", Toast.LENGTH_LONG).show()
                    );
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
                    String body = Html.fromHtml(poemJson.getBody()).toString();
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
        Poem poem;
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