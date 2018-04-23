package com.example.fengcheng.main.listtask;

import android.os.Bundle;
import android.view.View;

import com.example.fengcheng.main.model.DataModel;

/**
 * @Package com.example.fengcheng.main.listtask
 * @FileName ListPresenter
 * @Date 4/23/18, 2:10 AM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class ListPresenter implements ListContract.IPresenter {
    ListContract.IView listIView;
    private DataModel dataModel;
    private Double lat, lngt;

    public ListPresenter(ListContract.IView fragment) {
        listIView = fragment;
        listIView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void pullData(View v, Bundle bundle) {
        dataModel = (DataModel) bundle.getSerializable("dataModel");
        lat = bundle.getDouble("lat");
        lngt = bundle.getDouble("lngt");

        listIView.initRecyclerView(v, dataModel, lat, lngt);
    }
}
