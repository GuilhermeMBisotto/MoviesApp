package com.arctouch.codechallenge.api;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.app.MovieApplication;
import com.arctouch.codechallenge.util.Constants;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.FuelManager;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import kotlin.Pair;

public class APIService {


    public static void initialSetup() {
        // Set root path
        FuelManager.Companion.getInstance().setBasePath(Constants.BASE_API_URL);
        // Define headers
        FuelManager.Companion.getInstance().setBaseHeaders(new HashMap<String, String>() {{ }});
    }

    // API Calls
    public static void GET(String action, List<Pair<String, String>> params, APIHandler.Service handler) {
        handler.onStart();
        params.add(new Pair<>("api_key", Constants.API_KEY));
        params.add(new Pair<>("language", Constants.DEFAULT_LANGUAGE));

        Fuel.get(action, params).responseString( APIService.HANDLER(handler) );
    }

    // Private API
    private static Handler<String> HANDLER(final APIHandler.Service handler) {
        return new Handler<String>() {
            @Override
            public void success(Request request, Response response, String content) {
                handler.onSuccess(content);
                handler.onFinish();
            }
            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                String content = new String(response.getData());
                APIServiceResultFailed result;

                try {
                    result = new Gson().fromJson(content, APIServiceResultFailed.class);
                } catch (Exception e) { // catch anything during JSON parser
                    response.setHttpStatusCode(500);
                    result = APIServiceResultFailed.createResultFailed(e.getMessage());
                }

                switch (response.getHttpStatusCode()) {
                    case -1:    // Unable to resolve host
                        String message = MovieApplication.context().getString(R.string.apiError_unableToResolveHost);
                        handler.onFailure(
                                APIServiceResultFailed.createResultFailed(-1, message));
                        break;
                    case 400:
                        handler.onError(result);
                        break;
//                    case 500: //onFailure - means that something unexpected happened at server side
                    default:
                        handler.onFailure(
                                APIServiceResultFailed.createResultFailed(MovieApplication.context().getString(R.string.apiError_somethingFailedAtServerSide)));
                        break;
                }
                handler.onFinish();
            }
        };
    }
}
