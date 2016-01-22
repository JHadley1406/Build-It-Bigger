package training.hhi.com.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jrh1406 on 1/7/16.
 */
public class JokeActivity extends AppCompatActivity {

    @Bind(R.id.joke_text)
    TextView jokeView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        ButterKnife.bind(this);

        jokeView.setText(getIntent().getStringExtra(MainActivity.JOKE_NAME));

    }
}
