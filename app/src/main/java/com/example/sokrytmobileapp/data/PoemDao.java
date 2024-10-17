package com.example.sokrytmobileapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PoemDao {

    @Query("SELECT * FROM poems ORDER BY title LIMIT :limit OFFSET :offset")
    LiveData<List<Poem>> getAllPoems(int offset, int limit);

    @Insert
    void insertPoem(Poem poem);

    @Query("SELECT * FROM poems WHERE nid = :nid AND revision_uid = :uid")
    LiveData<Poem> getPoemByUid(int nid, int uid);
}