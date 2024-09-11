package com.example.sokrytmobileapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "poems")
public class Poem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "bundle")
    public String bundle = "poem";

    public Poem(String title, String text) {
        this.title = title;
        this.text = text;
        this.bundle = "poem"; // удалить, в новой модели не имеет смысла
    }
}