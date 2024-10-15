package com.example.sokrytmobileapp.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;

import java.util.List;

public class PoemViewModel extends AndroidViewModel {
    private final PoemRepository repository;
    private final LiveData<List<Poem>> allPoems;

    public PoemViewModel(Application application) {
        super(application);
        repository = new PoemRepository(application);
        allPoems = repository.getAllPoems();
    }

    public LiveData<List<Poem>> getAllPoems() {
        return allPoems;
    }

    public void insert(Poem poem) {
        repository.insert(poem);
    }
}
