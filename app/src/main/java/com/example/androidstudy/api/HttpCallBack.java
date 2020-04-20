package com.example.androidstudy.api;

public interface HttpCallBack<T> {

    void success(T t);
    void failure(String message);
}
