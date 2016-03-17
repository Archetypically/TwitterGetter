package edu.fsu.mobile.project1;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Samuel on 3/16/2016.
 */
public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        Bundle tweetData = getIntent().getExtras();

        TextView username = (TextView) findViewById(R.id.user);
        TextView tweet = (TextView) findViewById(R.id.tweet);
        TextView name = (TextView) findViewById(R.id.name);
        TextView location = (TextView) findViewById(R.id.location);
        TextView time = (TextView) findViewById(R.id.time);
        if(tweetData != null){
            username.setText("@" + tweetData.getString("username"));
            name.setText(tweetData.getString("name"));
            tweet.setText(tweetData.getString("tweet"));
            location.setText(tweetData.getString("location"));
            time.setText(tweetData.getString("time"));
        }
        else
            Log.i("POP", "Null");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4));
    }
}
