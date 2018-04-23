package com.example.fengcheng.main.utils;

import com.example.fengcheng.main.model.DataModel;

import java.util.Comparator;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName DistanceComparator
 * @Date 4/22/18, 10:58 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class DistanceComparator implements Comparator {
    private double lastLatitude;
    private double lastLongtitude;


    public DistanceComparator(double lastLatitude, double lastLongtitude) {
        this.lastLatitude = lastLatitude;
        this.lastLongtitude = lastLongtitude;
    }

    @Override
    public int compare(Object o1, Object o2) {
        DataModel.ResultsBean resultsBean1 = (DataModel.ResultsBean) o1;
        DataModel.ResultsBean resultsBean2 = (DataModel.ResultsBean) o2;

        Double distance1 = resultsBean1.getGeometry().getLocation().countDistance(lastLatitude, lastLongtitude);
        Double distance2 = resultsBean2.getGeometry().getLocation().countDistance(lastLatitude, lastLongtitude);

        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        }
        return 0;
    }
}
