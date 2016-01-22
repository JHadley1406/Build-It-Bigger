package training.hhi.com.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements CallBack {

    public static final String JOKE_NAME = "joke";
    public static final String URL = "http://localhost:8080";
    public static final String API = "_ah/api/";
    public static final String ENDPOINT = "joke";
    public static final String JOKE_ERROR = "Error Loading Joke";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    InterstitialAd mInterstitial;

    @Bind(R.id.joke_button)
    Button jokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mInterstitial = new InterstitialAd(getApplicationContext());
        mInterstitial.setAdUnitId(getResources().getString(R.string.admob_unit_id));

        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                super.onAdClosed();
            }
        });

        requestNewInterstitial();
    }

    @OnClick(R.id.joke_button)
    public void submit(){
        // call asynctask to fetch joke
        if(mInterstitial.isLoaded()){
            mInterstitial.show();
        }
        AsyncJoke joke = new AsyncJoke(MainActivity.this);
        joke.execute(URL + API + ENDPOINT);
    }

    private void requestNewInterstitial(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("DEVICE ID GOES HERE")
                .build();

        mInterstitial.loadAd(adRequest);
    }

    public void tellJoke(String joke){
        Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
        intent.putExtra(JOKE_NAME, joke);
        startActivity(intent);
    }

    private class AsyncJoke extends AsyncTask<String, Void, String>{

        private ProgressDialog dialog;

        public AsyncJoke(MainActivity activity){
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute(){
            dialog.setMessage("Loading Joke");
            dialog.show();
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

            if(dialog.isShowing()){
                dialog.dismiss();
            }
            tellJoke(joke);

        }
    }
}
