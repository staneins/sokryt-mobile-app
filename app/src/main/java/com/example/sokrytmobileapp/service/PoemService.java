package com.example.sokrytmobileapp.service;

import androidx.lifecycle.LiveData;
import com.example.sokrytmobileapp.data.Poem;
import com.example.sokrytmobileapp.repository.PoemRepository;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class PoemService {
    private final PoemRepository poemRepository;

    public PoemService(PoemRepository poemRepository) {
        this.poemRepository = poemRepository;
    }

    public LiveData<List<Poem>> getAllPoems() {
        return poemRepository.getAllPoems();
    }

    public void insertPoem(Poem poem) {
        poemRepository.insert(poem);
    }
}