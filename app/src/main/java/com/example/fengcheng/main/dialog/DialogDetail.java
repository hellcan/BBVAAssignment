package com.example.fengcheng.main.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.fengcheng.main.bbvaassignment.R;
import com.example.fengcheng.main.model.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName DialogDetail
 * @Date 4/22/18, 11:24 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class DialogDetail extends DialogFragment {
    Button navBtn;
    TextView nameTv, addressTv, timeTv, typeTv;
    String name, address;
    Boolean isopen;
    List<String> typeList;
    Double lat, lngt, lastLat, lastLngt;

    public static DialogDetail newInstance(DataModel dataModel, String pos, Double lat, Double lngt) {
        Bundle args = new Bundle();
        args.putString("name", dataModel.getResults().get(Integer.valueOf(pos)).getName());
        args.putString("address", dataModel.getResults().get(Integer.valueOf(pos)).getFormatted_address());
        args.putBoolean("isopen", dataModel.getResults().get(Integer.valueOf(pos)).getOpening_hours().isOpen_now());
        args.putStringArrayList("types", (ArrayList<String>) dataModel.getResults().get(Integer.valueOf(pos)).getTypes());
        args.putDouble("lat", lat);
        args.putDouble("lngt", lngt);
        args.putDouble("destlat", dataModel.getResults().get(Integer.valueOf(pos)).getGeometry().getLocation().getLat());
        args.putDouble("destlngt", dataModel.getResults().get(Integer.valueOf(pos)).getGeometry().getLocation().getLng());


        DialogDetail dialogOrderDetail = new DialogDetail();
        dialogOrderDetail.setArguments(args);
        return dialogOrderDetail;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog rootView
        View v = inflater.inflate(R.layout.dialog_detail, container, false);

        getData();

        initView(v);

        return v;
    }

    private void getData() {
        name = getArguments().getString("name");
        address = getArguments().getString("address");
        isopen = getArguments().getBoolean("isopen");
        typeList = getArguments().getStringArrayList("types");
        lat = getArguments().getDouble("lat");
        lngt = getArguments().getDouble("lngt");
        lastLat = getArguments().getDouble("destlat");
        lastLngt = getArguments().getDouble("destlngt");

    }

    private void initView(View v) {
        navBtn = v.findViewById(R.id.btn_direction);
        nameTv = v.findViewById(R.id.tv_name);
        addressTv = v.findViewById(R.id.tv_address);
        timeTv = v.findViewById(R.id.tv_time);
        typeTv = v.findViewById(R.id.tv_type);

        nameTv.setText(name);
        addressTv.setText(address);
        timeTv.setText(getActivity().getString(R.string.open_now) + ": " + isopen);
        if (typeList != null || typeList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < typeList.size(); i++) {
                if (i != typeList.size() - 1) {
                    sb.append(typeList.get(i) + ", ");
                } else {
                    sb.append(typeList.get(i));
                }
            }
            typeTv.setText(sb.toString());
        } else {
            typeTv.setText("");
        }

        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=loc" + lat + "," + lngt + "&daddr=" + lastLat + "," + lastLngt));
                startActivity(intent);
            }
        });
    }

    public void showDialog(FragmentManager fragmentManager, String tag) {
        if (getDialog() == null || !getDialog().isShowing()) {
            show(fragmentManager, tag);
        }
    }
}
