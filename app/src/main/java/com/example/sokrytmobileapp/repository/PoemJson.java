package com.example.sokrytmobileapp.repository;

import java.util.List;

public class PoemJson {
    private List<ValueWrapper<Integer>> nid;
    private List<ValueWrapper<Integer>> revision_uid;
    private List<ValueWrapper<String>> title;
    private List<ValueWrapper<String>> body;

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
