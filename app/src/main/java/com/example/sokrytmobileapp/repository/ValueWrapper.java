package com.example.sokrytmobileapp.repository;

import com.google.gson.annotations.SerializedName;

public class ValueWrapper<T> {
    @SerializedName("value")
    private T value;

    public T getValue() {
        return value;
    }
}

