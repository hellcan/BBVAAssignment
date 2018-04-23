package com.example.fengcheng.main.listtask;

import android.os.Bundle;
import android.view.View;

import com.example.fengcheng.main.bbvaassignment.BasePresenter;
import com.example.fengcheng.main.bbvaassignment.BaseView;
import com.example.fengcheng.main.model.DataModel;

/**
 * @Package com.example.fengcheng.main.listtask
 * @FileName ListContract
 * @Date 4/23/18, 2:11 AM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public interface ListContract{

    interface IView extends BaseView<IPresenter>{

        void initRecyclerView(View v, DataModel dataModel, double lat, double lngt);
    }

    interface IPresenter extends BasePresenter{
        void pullData(View v, Bundle bundle);
    }

}
