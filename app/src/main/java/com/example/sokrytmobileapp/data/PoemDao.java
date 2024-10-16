package com.example.sokrytmobileapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PoemDao {

    @Query("SELECT * FROM poems ORDER BY title LIMIT 30")
    LiveData<List<Poem>> getAllPoemsLiveData();

    @Insert
    void insertPoem(Poem poem);
}
