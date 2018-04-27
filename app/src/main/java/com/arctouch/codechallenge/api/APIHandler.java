package com.arctouch.codechallenge.api;

public class APIHandler {

    public static class Service {
        private final APIHandler.Controller controllerHandler;

        public Service() {
            controllerHandler = new Controller() {
                @Override
                public void onStart() {}
                @Override
                public void onSuccess(Object obj) {}
                @Override
                public void onError(APIServiceResultFailed error) {}
                @Override
                public void onFailure(APIServiceResultFailed failure) {}
                @Override
                public void onFinish() {}
            };
        }

        public Service(APIHandler.Controller controllerHandler) {
            this.controllerHandler = controllerHandler;
        }

        public void onStart()                                   { controllerHandler.onStart(); }
        public void onSuccess(Object obj)                       { controllerHandler.onSuccess(obj); }
        public void onError(APIServiceResultFailed error)       { controllerHandler.onError(error); }
        public void onFailure(APIServiceResultFailed failure)   { controllerHandler.onFailure(failure); }
        public void onFinish()                                  { controllerHandler.onFinish();}
    }

    public static abstract class Controller {

        public abstract void onStart();
        public abstract void onSuccess(Object obj);
        public abstract void onError(APIServiceResultFailed error);
        public abstract void onFailure(APIServiceResultFailed failure);
        public abstract void onFinish();

    }
}
