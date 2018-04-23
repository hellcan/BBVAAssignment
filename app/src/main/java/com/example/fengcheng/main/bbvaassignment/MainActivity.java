package com.example.fengcheng.main.bbvaassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fengcheng.main.listtask.ListFragment;
import com.example.fengcheng.main.listtask.ListPresenter;
import com.example.fengcheng.main.maptask.MapPresenter;
import com.example.fengcheng.main.maptask.MyMapFragment;
import com.example.fengcheng.main.utils.ActivityUtils;



/*BBVA:
        The next step of the interview is a coding assignment and you need to complete this in 48 hours and you can email your project folder or share it using google drive. (For the assignment, it is preferred to use the default Android APIs for network connections and parsing JSON instead of going for third party libraries).

        Here is the assignment:

        Screen1:

        Show map view with current location icon.

        Search BBVA Compass around your current location, show the results on map view (Don’t show search bar on the screen, use hardcoded string as “BBVA Compass” in background).

        Use the below link to get the search details from google APIs: https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=MY_LAT,MY_LONG&radius=10000&key=YOUR_API_KEY

        Parse the above json, & plot the markers on map view.

        An option in menu to toggle between list view & map view.

        List view will show the same result items in a list.

        Screen 2:

        Clicking on marker on map view will show a tooltip and clicking on the tooltip will take you to different screen with more detail shown.

        Optional:

        A button in details screen to launch default Map application with direction from your current location.

        Marker with bank icon instead of default marker (there is link for icon in search results from Google api link).

        Enabling gestures, my location, zoom, compass & other useful ui interactions on map view.

        Sort the list by distance.

        Marshmallow permissions handling.*/

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    MapPresenter mapPresenter;
    ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyMapFragment mapFragment = MyMapFragment.newInstance();
        ActivityUtils.addFragmenttoActivity(getSupportFragmentManager(), mapFragment, R.id.frame_container, "mapFgt");

        mapPresenter = new MapPresenter(mapFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toggle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:

                MyMapFragment myMapFragment = (MyMapFragment) getSupportFragmentManager().findFragmentByTag("mapFgt");

                ListFragment listFragment = ListFragment.newInstance();

                if (myMapFragment != null){

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("dataModel", myMapFragment.getDataModel());
                    bundle.putDouble("lat", myMapFragment.getCurrentLat());
                    bundle.putDouble("lngt", myMapFragment.getCurrentLngt());
                    listFragment.setArguments(bundle);

                    ActivityUtils.addFragmenttoActivity(getSupportFragmentManager(), listFragment, R.id.frame_container, "listFgt");
                }

                listPresenter = new ListPresenter(listFragment);

                break;
            case R.id.menu_map:

                MyMapFragment mapFragment = MyMapFragment.newInstance();
                ActivityUtils.addFragmenttoActivity(getSupportFragmentManager(), mapFragment, R.id.frame_container, "mapFgt");

                mapPresenter = new MapPresenter(mapFragment);

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
