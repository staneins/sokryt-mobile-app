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

    public Poem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getRevisionUid() {
        return revisionUid;
    }

    public void setRevisionUid(Integer revisionUid) {
        this.revisionUid = revisionUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}