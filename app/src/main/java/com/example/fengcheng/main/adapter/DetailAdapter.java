package com.example.fengcheng.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fengcheng.main.model.DataModel;
import com.example.fengcheng.main.utils.DistanceComparator;
import com.example.fengcheng.main.bbvaassignment.R;

import java.util.Collections;
import java.util.List;

/**
 * @Package com.example.fengcheng.main.adapter
 * @FileName DetailAdapter
 * @Date 4/22/18, 9:06 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.NormalViewHolder> {
    private List<DataModel.ResultsBean> dataList;
    private Context context;

    public DetailAdapter(Context context, DataModel dataModel, Double lat, Double lngt ) {
        this.context = context;
        this.dataList = dataModel.getResults();
        Collections.sort(dataList, new DistanceComparator(lat, lngt));
    }

    @Override
    public DetailAdapter.NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);

        return new NormalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.NormalViewHolder holder, int position) {
        holder.nameTv.setText(dataList.get(position).getName());
        holder.addressTv.setText(dataList.get(position).getFormatted_address());
        holder.timeTv.setText(context.getString(R.string.open_now) + ": "+ dataList.get(position).getOpening_hours().isOpen_now());
        if (dataList.get(position).getTypes() != null || dataList.get(position).getTypes().size() > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dataList.get(position).getTypes().size(); i++){
                if (i != dataList.get(position).getTypes().size()-1){
                    sb.append(dataList.get(position).getTypes().get(i) + ", ");
                }else {
                    sb.append(dataList.get(position).getTypes().get(i));

                }
            }
            holder.typeTv.setText(sb.toString());
        }else {
            holder.typeTv.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, addressTv, timeTv, typeTv;

        public NormalViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            addressTv = itemView.findViewById(R.id.tv_address);
            timeTv = itemView.findViewById(R.id.tv_time);
            typeTv = itemView.findViewById(R.id.tv_type);

        }
    }
}
