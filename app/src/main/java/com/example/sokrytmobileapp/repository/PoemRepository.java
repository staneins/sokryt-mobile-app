package com.example.sokrytmobileapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.data.PoemDao;
import com.example.sokrytmobileapp.data.PoemDatabase;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.res.AssetManager;
import okhttp3.*;

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
    private final LiveData<List<Poem>> allPoems;

    public PoemRepository(Application application) {
        PoemDatabase db = PoemDatabase.getDatabase(application);
        poemDao = db.poemDao();
        allPoems = poemDao.getAllPoemsLiveData();
    }

    public LiveData<List<Poem>> getAllPoems() {
        return allPoems;
    }

    public void insert(final Poem poem) {
        Executors.newSingleThreadExecutor().execute(() -> poemDao.insertPoem(poem));
    }

    private void loadPoems(PoemRepository poemRepository) {
        String url = "https://sokryt.ru/json/stihi";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    loadPoemsFromJson(jsonString, poemRepository);
                }
            }
        });
    }

    public void loadPoemsFromJson(String jsonString, PoemRepository poemRepository) {
        if (jsonString != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Poem>>() {}.getType();
            List<PoemJson> poemJsons = gson.fromJson(jsonString, listType);

            for (PoemJson poemJson : poemJsons) {
                Integer nid = poemJson.getNid();
                Integer revisionUid = poemJson.getRevisionUid();
                String title = poemJson.getTitle();
                String body = poemJson.getBody();
                Poem poem = new Poem(nid, revisionUid, title, body);
                poemRepository.insert(poem);
            }
        }
    }
}
