package com.arctouch.codechallenge.api;

public abstract class APIServiceHandler {

    public void onStart() {}
    public abstract void onSuccess(Object obj);
    public abstract void onError(APIServiceResultFailed error);
    public void onFailure(APIServiceResultFailed failure) {}
    public void onFinish() {}
}
