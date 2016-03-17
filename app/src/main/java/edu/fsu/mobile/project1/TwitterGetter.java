package edu.fsu.mobile.project1;

import android.content.res.Resources;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TwitterGetter extends Thread {

    private static final String STREAM_URI =
            "https://api.twitter.com/1.1/search/tweets.json?";
    private MainActivity mainActivity;
    private static Resources res;
    private static String geoCode;
    private static final String sinceID = "&since_id=";
    private static int lastIDNum = -1;
    private static String twitURL = null;
    private static final int timeout = 120;
    private static final long refreshTimeout = 15000;

    public TwitterGetter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        res = mainActivity.getResources();

        if(mainActivity.currLoc == null){
            geoCode = "geocode=" +
                    mainActivity.defLoc.latitude +
                    "%2C" +
                    mainActivity.defLoc.longitude +
                    "%2C30mi";
        }
        else {
            geoCode = "geocode=" +
                    mainActivity.currLoc.latitude +
                    "%2C" +
                    mainActivity.currLoc.longitude +
                    "%2C30mi";
        }


    }

    public void run() {
        ArrayList<Integer> ids = new ArrayList<>();
        String line;
        JSONObject jObj = null;
        JSONObject jObj2;
        JSONObject jObj3;
        JSONObject geoObj;
        JSONArray jArr = null;
        String sn;
        String msg;
        String name;
        String loc;
        String time;
        LatLng coords;
        double lng;
        double lat;
        int id;

        while (!Thread.interrupted()) {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                reader.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

            if (jArr != null) {
                for (int i = 0; i < jArr.length(); i++) {
                    try {
                        jObj2 = jArr.getJSONObject(i);
                        jObj3 = jObj2.getJSONObject("user");

                        time = jObj2.getString("created_at");
                        msg = jObj2.getString("text");
                        sn = jObj3.getString("screen_name");
                        name = jObj3.getString("name");
                        loc = jObj3.getString("location");
                        id = jObj2.getInt("id");

                        geoObj = jObj2.getJSONObject("geo");
                        lat = (geoObj.getJSONArray("coordinates")).getDouble(0);
                        lng = (geoObj.getJSONArray("coordinates")).getDouble(1);

                        coords = new LatLng(lat, lng);

                        if(!ids.contains(id)) {
                            ids.add(id);
                            mainActivity.addMapMarker(coords, sn, msg, name, loc, time);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jObj2 = jObj.getJSONObject("search_metadata");
                    lastIDNum = jObj2.getInt("max_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(refreshTimeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
