package com.example.fengcheng.main.maptask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengcheng.main.model.DataModel;
import com.example.fengcheng.main.dialog.DialogDetail;
import com.example.fengcheng.main.bbvaassignment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName MyMapFragment
 * @Date 4/22/18, 8:36 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback, MapContract.IView {

    private GoogleMap mMap;

    //this is a permission check flag
    private Boolean hasLocationPermission = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5000;


    private static final String TAG = "MyMapFragment";

    //presenter
    private MapContract.IPresenter mapPresenter;


    public static MyMapFragment newInstance() {
        return new MyMapFragment();
    }

    public MyMapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        if (mapPresenter != null)
            mapPresenter.getLocationPermission(getActivity());

        return v;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mapPresenter.moveToCurrentLocation(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        hasLocationPermission = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {

                mapPresenter.requestPermission(grantResults);

                break;
            }
        }
    }


    @Override
    public void setPresenter(MapContract.IPresenter presenter) {
        mapPresenter = presenter;
    }

    @Override
    public void initGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mapPresenter.getBBVALocation();

    }


    @Override
    public void showBBVAOnMap(final DataModel dataModel) {
        // Add a marker
        for (int i = 0; i < dataModel.getResults().size(); i++) {

            LatLng resultLoc = new LatLng(dataModel.getResults().get(i).getGeometry().getLocation().getLat(), dataModel.getResults().get(i).getGeometry().getLocation().getLng());

            Marker marker = mMap.addMarker(new MarkerOptions().position(resultLoc).title("BBVA Compass").snippet(dataModel.getResults().get(i).getFormatted_address()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8f));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);

            marker.setTag(i);
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                mapPresenter.markClick(marker);

                return false;
            }
        });
    }

    @Override
    public void showDetailDialog(DataModel dataModel, Marker marker, double lat, double lngt) {
        DialogDetail dialogDetail = DialogDetail.newInstance(dataModel, marker.getTag().toString(),
                lat, lngt);
        dialogDetail.showDialog(getActivity().getSupportFragmentManager(), "dlgDetail");
    }

    public DataModel getDataModel() {
        return mapPresenter.getDataModel();
    }

    public Double getCurrentLat() {
        return mapPresenter.getLat();
    }

    public Double getCurrentLngt() {
        return mapPresenter.getLngt();
    }
}
