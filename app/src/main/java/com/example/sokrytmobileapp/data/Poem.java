package com.example.sokrytmobileapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "poems")
public class Poem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nid")
    public Integer nid;

    @ColumnInfo(name = "revision_uid")
    public Integer revisionUid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "body")
    public String body;

    public Poem(Integer nid, Integer revisionUid, String title, String body) {
        this.nid = nid;
        this.revisionUid = revisionUid;
        this.title = title;
        this.body = body;
    }
}