package com.example.sokrytmobileapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface PoemDao {

    @Query("SELECT * FROM poems ORDER BY title LIMIT :limit OFFSET :offset")
    LiveData<List<Poem>> getAllPoems(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPoem(Poem poem);

    @Query("SELECT * FROM poems WHERE nid = :nid AND revision_uid = :uid")
    Poem getPoemByUid(int nid, int uid);

    @Query("SELECT * FROM poems WHERE nid = :nid AND revision_uid IS NULL")
    Poem getPoemWithNullRevisionUid(int nid);

    @Query("SELECT * FROM poems WHERE title LIKE :query")
    LiveData<List<Poem>> searchPoems(String query);

    @Query("SELECT * FROM poems WHERE title LIKE :query ORDER BY title LIMIT :limit OFFSET :offset")
    LiveData<List<Poem>> getAllSearchedPoems(int offset, int limit, String query);
}