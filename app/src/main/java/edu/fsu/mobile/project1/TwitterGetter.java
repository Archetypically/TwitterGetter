package edu.fsu.mobile.project1;

import android.content.res.Resources;
import android.os.Handler;

/*import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TwitterGetter extends Thread {
/*
    private static final String STREAM_URI =
            "https://api.twitter.com/1.1/search/tweets.json?";
    // Need primary map activity
    private Resources res;
    private Handler handler = new Handler();
    private static final String geoCode = null //"geocode=33.12743%2C-117.2654932%2C30mi";
    private static final String sinceID = "&since_id=";
    private static int lastIDNum = -1;
    private static String twitURL = null;
    private static final int timeout = 120;
    private static final long refreshTimeout = 15000;

    public TwitterGetter(TwitterMapActivity twitterMap) {
        //this.twitterMap = twitterMap;
        //res = twitterMap.getResources();
    }

    public void run() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String line;
        JSONObject jObj = null;
        JSONObject jObj2 = null;
        JSONObject jObj3 = null;
        JSONObject geoObj = null;
        JSONArray jArr = null;
        String sn = null;
        String msg = null;
        //LatLng coords = null;
        double lng = 0;
        double lat = 0;
        int id = 0;

        while (true) {
            OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(res.getString(R.string.api_key))
                    .apiSecret(res.getString(R.string.api_secret))
                    .build();

            Token accessToken = new Token(
                    res.getString(R.string.token),
                    res.getString(R.string.token_secret)
            );

            if (lastIDNum == -1)
                twitURL = STREAM_URI + geoCode;
            else
                twitURL = STREAM_URI + geoCode + sinceID + lastIDNum;
            OAuthRequest request = new OAuthRequest(Verb.GET, twitURL);
            request.setConnectionKeepAlive(true);
            service.signRequest(accessToken, request);

            Response response = request.send();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));
                if ((line = reader.readLine()) != null) {
                    try {
                        jObj = new JSONObject(line);
                        jArr = jObj.getJSONArray("statuses");
                    } catch (JSONException e) { }
                }
                reader.close();
            }catch(IOException ioe){ }

            if (jArr != null) {
                for (int i = 0; i < jArr.length(); i++) {
                    try {
                        jObj2 = jArr.getJSONObject(i);
                        jObj3 = jObj2.getJSONObject("user");

                        msg = jObj2.getString("text");
                        sn = jObj3.getString("screen_name");
                        id = jObj2.getInt("id");

                        geoObj = jObj2.getJSONObject("geo");
                        lat = (geoObj.getJSONArray("coordinates")).getDouble(0);
                        lng = (geoObj.getJSONArray("coordinates")).getDouble(1);

                        coords = new LatLng(lat, lng);

                        System.out.println(ids.contains(id));
                        if(!ids.contains(id)) {
                            ids.add(id);
                            twitterMap.addTweetToMap(sn, msg, coords, timeout);
                        }

                    } catch (JSONException e) { }
                }
                try {
                    jObj2 = jObj.getJSONObject("search_metadata");
                    lastIDNum = jObj2.getInt("max_id");
                } catch (JSONException e) { }
            }
            try {
                Thread.sleep(refreshTimeout);
            } catch (InterruptedException e) { }
        }
    }
    */
}
