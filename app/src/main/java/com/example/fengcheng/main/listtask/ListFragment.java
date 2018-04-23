package com.example.fengcheng.main.listtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengcheng.main.adapter.DetailAdapter;
import com.example.fengcheng.main.model.DataModel;
import com.example.fengcheng.main.bbvaassignment.R;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName ListFragment
 * @Date 4/22/18, 10:02 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class ListFragment extends Fragment implements ListContract.IView{

    ListContract.IPresenter listPresenter;

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        listPresenter.pullData(v, getArguments());

        return v;
    }

    @Override
    public void setPresenter(ListContract.IPresenter presenter) {
        listPresenter = presenter;
    }


    @Override
    public void initRecyclerView(View v, DataModel dataModel, double lat, double lngt) {
        RecyclerView recyclerView = v.findViewById(R.id.rv_location_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new DetailAdapter(getContext(), dataModel, lat, lngt));

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}
