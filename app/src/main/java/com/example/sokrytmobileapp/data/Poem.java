package com.example.sokrytmobileapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "poems")
public class Poem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nid")
    private Integer nid;

    @ColumnInfo(name = "revision_uid")
    private Integer revisionUid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "is_favourite")
    private Boolean isFavourite;

    public Poem(Integer nid, Integer revisionUid, String title, String body) {
        this.nid = nid;
        this.revisionUid = revisionUid;
        this.title = title;
        this.body = body;
        this.isFavourite = false;
    }

    public Poem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
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