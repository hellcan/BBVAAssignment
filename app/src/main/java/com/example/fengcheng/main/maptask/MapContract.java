package com.example.fengcheng.main.maptask;

import android.app.Activity;

import com.example.fengcheng.main.bbvaassignment.BasePresenter;
import com.example.fengcheng.main.bbvaassignment.BaseView;
import com.example.fengcheng.main.model.DataModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName MapContract
 * @Date 4/23/18, 1:07 AM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public interface MapContract {

    interface IView extends BaseView<IPresenter> {

        void initGoogleMap();

        void moveCamera(LatLng latLng, float zoom);

        void showBBVAOnMap(DataModel dataModel);

        void showDetailDialog(DataModel dataModel, Marker marker, double lat, double lngt);

    }

    interface IPresenter extends BasePresenter {

        void getLocationPermission(Activity activity);

        void moveToCurrentLocation(Activity activity);

        void getBBVALocation();

        void markClick(Marker marker);

        DataModel getDataModel();

        double getLat();

        double getLngt();

        void requestPermission(int[] grantResults);
    }
}
