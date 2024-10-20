package com.example.sokrytmobileapp.repository;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoemJson {
    @SerializedName("nid")
    private List<ValueWrapper<Integer>> nid;

    @SerializedName("revision_uid")
    private List<ValueWrapper<Integer>> revision_uid;

    @SerializedName("title")
    private List<ValueWrapper<String>> title;

    @SerializedName("body")
    private List<ValueWrapper<String>> body;

    public PoemJson() {
    }

    public PoemJson(List<ValueWrapper<Integer>> nid, List<ValueWrapper<Integer>> revision_uid, List<ValueWrapper<String>> title, List<ValueWrapper<String>> body) {
        this.nid = nid;
        this.revision_uid = revision_uid;
        this.title = title;
        this.body = body;
    }

    public Integer getNid() {
        return nid != null && !nid.isEmpty() ? nid.get(0).getValue() : null;
    }

    public Integer getRevisionUid() {
        return revision_uid != null && !revision_uid.isEmpty() ? revision_uid.get(0).getValue() : null;
    }

    public String getTitle() {
        return title != null && !title.isEmpty() ? title.get(0).getValue() : null;
    }

    public String getBody() {
        return body != null && !body.isEmpty() ? body.get(0).getValue() : null;
    }
}
