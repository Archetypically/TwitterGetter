package edu.fsu.mobile.project1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * updateLocation(Location)
     *
     * Reacts to updates to the user's location.
     * Required by LocationFetcher.
     */
    void updateLocation(Location location) {
        // TODO: React to location change.
    }

    /*
     * getLocation()
     *
     * Creates a LocationFetcher object that gets the user's current location.
     */
    void getLocation() {
        LocationFetcher locationFetcher = new LocationFetcher();
        locationFetcher.execute();
    }

    /*
     * LocationFetcher
     *
     * Used to asynchronously fetch the user's current location.
     * Requires that an external function "updateLocation(Location)" be implemented.
     */
    public class LocationFetcher extends AsyncTask<Void, Void, Location> {
        Location newLocation;
        boolean hasLocationBeenFetched = false;
        LocationManager locationManager;
        CustomLocationListener locationListener;

        @Override
        protected void onPreExecute() {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new CustomLocationListener();

            // Check for location permissions
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e("MainActivity", "onPreExecute: Missing network provider permission");
                //return;
            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
        }

        @Override
        protected Location doInBackground(Void... params) {
            // Wait for the location to be updated
            while (!hasLocationBeenFetched) {}

            return newLocation;
        }

        @Override
        protected void onPostExecute(Location result) {
            // updateLocation must be implemented outside this class
            updateLocation(result);
        }

        public class CustomLocationListener implements LocationListener {
            @Override
            public void onLocationChanged(Location location) {
                newLocation = location;
                hasLocationBeenFetched = true;

                // Check for location permissions
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Log.e("MainActivity", "onLocationChanged: Missing network provider permission");
                    //return;
                }
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }
    }
}
