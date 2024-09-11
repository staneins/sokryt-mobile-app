package com.example.sokrytmobileapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PoemDao {

    @Query("SELECT * FROM poems WHERE bundle = 'poem' ORDER BY title") //редактировать в новой модели
    List<Poem> getAllPoems();

    @Insert
    void insertPoem(Poem poem);
}
