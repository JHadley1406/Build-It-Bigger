package com.health.hhi.asyncjokelibrary;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Josiah Hadley on 1/25/2016.
 */
public class AsyncJoke extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = AsyncJoke.class.getSimpleName();
    public static final String JOKE_ERROR = "Error Loading Joke";

    private ICallBack callBack;

    public AsyncJoke(ICallBack callBack){
        this.callBack = callBack;
    }


    @Override
    protected String doInBackground(String... url){
        OkHttpClient jokeGCEClient = new OkHttpClient();
        String joke = JOKE_ERROR;
        try{

            Request request = new Request.Builder()
                    .url(url[0])
                    .build();
            Response response = jokeGCEClient.newCall(request).execute();
            JSONObject jokeData = new JSONObject(response.body().string());
            joke = jokeData.getString("data");
        } catch(IOException e){
            Log.e(LOG_TAG, e.toString());
        } catch(JSONException e){
            Log.e(LOG_TAG, e.toString());
        }
        return joke;
    }

    protected void onPostExecute(String joke){
        Log.i(LOG_TAG, "in onPostExecute");
        callBack.tellJoke(joke);

    }
}