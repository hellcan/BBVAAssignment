package com.example.fengcheng.main.maptask;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.fengcheng.main.model.DataModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName MapPresenter
 * @Date 4/23/18, 1:06 AM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class MapPresenter implements MapContract.IPresenter {
    private MapContract.IView mapView;
    //this is location permission
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5000;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Boolean hasLocationPermission = false;
    private Location currentLoc;
    private DataModel dataModel;

    private static final String TAG = "MapPresenter";


    public MapPresenter(MapContract.IView fragment) {
        //provide regView reference
        mapView = fragment;
        //link view to presenter
        mapView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void getLocationPermission(Activity activity) {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION
                , android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity.getApplicationContext().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            hasLocationPermission = true;
            mapView.initGoogleMap();

        } else {
            ActivityCompat.requestPermissions(activity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void moveToCurrentLocation(Activity activity) {
        if (hasLocationPermission){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
            try {
                if (hasLocationPermission) {
                    final Task location = fusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                currentLoc = (Location) task.getResult();

                                mapView.moveCamera(new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude()), 12f);

                            } else {
                                Log.i(TAG, "Unable to get current location");
                            }
                        }
                    });
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getBBVALocation() {
        //async task
        MyAsyncTask myAsyncTask = new MyAsyncTask(currentLoc.getLatitude(), currentLoc.getLongitude());
        myAsyncTask.execute();
    }

    @Override
    public void markClick(Marker marker) {
        mapView.showDetailDialog(dataModel, marker, currentLoc.getLatitude(), currentLoc.getLongitude());
    }

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    @Override
    public double getLat() {
        return currentLoc.getLatitude();
    }

    @Override
    public double getLngt() {
        return currentLoc.getLongitude();
    }

    @Override
    public void requestPermission(int[] grantResults) {
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    hasLocationPermission = false;
                    return;
                }
            }
            hasLocationPermission= true;

            mapView.initGoogleMap();
    }
    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private URL url = null;
        private String mUrl, api_key = "AIzaSyCs2Roy90QP4vtQaJRdcwL_3orQiuXjpeU";
        private String result;
        private List<DataModel.ResultsBean> resultsBeanList;

        public MyAsyncTask(Double mLat, Double mLont) {
            mUrl = " https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=" + mLat + "," + mLont + "&radius=10000&key=" + api_key;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(mUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    while ((result = br.readLine()) != null) {
                        sb.append(result);
                    }

                    return sb.toString();
                }

            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(TAG, result);
            Log.i(TAG, mUrl);

            resultsBeanList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultObj = jsonArray.getJSONObject(i);
                    //formatted_address
                    String formatted_address = resultObj.getString("formatted_address");
                    //geometry
                    JSONObject geoObj = resultObj.getJSONObject("geometry");
                    //location
                    JSONObject locationObj = geoObj.getJSONObject("location");
                    double lat = locationObj.getDouble("lat");
                    double lng = locationObj.getDouble("lng");
                    //viewport
                    JSONObject vpObj = geoObj.getJSONObject("viewport");
                    //ne
                    JSONObject neObj = vpObj.getJSONObject("northeast");
                    double neLat = neObj.getDouble("lat");
                    double neLng = neObj.getDouble("lng");
                    //sw
                    JSONObject swObj = vpObj.getJSONObject("southwest");
                    double swLat = swObj.getDouble("lat");
                    double swLng = swObj.getDouble("lng");
                    //icon, id, name
                    String icon = resultObj.getString("icon");
                    String id = resultObj.getString("id");
                    String name = resultObj.getString("name");

                    boolean open_now = false;
                    //open hours
                    if (resultObj.has("opening_hours")) {
                        open_now = resultObj.getJSONObject("opening_hours").getBoolean("open_now");
                    }
                    //permanently_closed
                    boolean permanently_closed = false;
                    if (resultObj.has("permanently_closed")) {
                        permanently_closed = resultObj.getBoolean("permanently_closed");
                    }
                    //placeId, ref
                    String placeId = resultObj.getString("place_id");
                    String ref = resultObj.getString("reference");
                    //types
                    JSONArray typesArray = resultObj.getJSONArray("types");
                    List<String> typeList = new ArrayList<>();
                    for (int j = 0; j < typesArray.length(); j++) {
                        typeList.add(typesArray.getString(j));
                    }

                    //loc bean
                    DataModel.ResultsBean.GeometryBean.LocationBean locationBean = new DataModel.ResultsBean.GeometryBean.LocationBean(lat, lng);
                    //ne bean
                    DataModel.ResultsBean.GeometryBean.ViewportBean.NortheastBean northeastBean = new DataModel.ResultsBean.GeometryBean.ViewportBean.NortheastBean(neLat, neLng);
                    //sw bean
                    DataModel.ResultsBean.GeometryBean.ViewportBean.SouthwestBean southwestBean = new DataModel.ResultsBean.GeometryBean.ViewportBean.SouthwestBean(swLat, swLng);
                    //vp bean
                    DataModel.ResultsBean.GeometryBean.ViewportBean viewportBean = new DataModel.ResultsBean.GeometryBean.ViewportBean(northeastBean, southwestBean);
                    //geo bean
                    DataModel.ResultsBean.GeometryBean geometryBean = new DataModel.ResultsBean.GeometryBean(locationBean, viewportBean);
                    //open hr bean
                    DataModel.ResultsBean.OpeningHoursBean openingHoursBean = new DataModel.ResultsBean.OpeningHoursBean(open_now, new ArrayList<String>() {
                    });

                    resultsBeanList.add(new DataModel.ResultsBean(formatted_address, geometryBean, icon, id, name, placeId, ref, openingHoursBean, permanently_closed, typeList));
                }
                String status = jsonObject.getString("status");
                dataModel = new DataModel(status, new ArrayList<String>(), resultsBeanList);


                mapView.showBBVAOnMap(dataModel);


                Log.i(TAG, dataModel.getResults().size() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
