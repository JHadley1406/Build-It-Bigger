package training.hhi.com.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.health.hhi.asyncjokelibrary.ICallBack;
import com.health.hhi.asyncjokelibrary.AsyncJoke;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ICallBack {

    public static final String JOKE_NAME = "joke";
    public static final String URL = "http://10.0.2.2:8080";
    public static final String API = "/_ah/api/myApi/v1/";
    public static final String ENDPOINT = "joke";

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressDialog dialog;
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
        dialog = ProgressDialog.show(MainActivity.this, "Joke Dialog", "Loading Joke", true);
        joke.execute(URL + API + ENDPOINT);
    }

    private void requestNewInterstitial(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitial.loadAd(adRequest);
    }

    public void tellJoke(String joke){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
        intent.putExtra(JOKE_NAME, joke);
        startActivity(intent);
    }


}
