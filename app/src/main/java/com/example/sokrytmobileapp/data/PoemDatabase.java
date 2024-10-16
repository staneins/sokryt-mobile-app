package com.example.sokrytmobileapp.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Poem.class}, version = 1, exportSchema = false)
public abstract class PoemDatabase extends RoomDatabase {

    private static volatile PoemDatabase INSTANCE;

    public abstract PoemDao poemDao();

    public static PoemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PoemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PoemDatabase.class, "poem_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
