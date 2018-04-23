package com.example.fengcheng.main.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.fengcheng.main.bbvaassignment.R;

/**
 * @Package com.example.fengcheng.main.utils
 * @FileName ActivityUtils
 * @Date 4/22/18, 8:33 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class ActivityUtils {

    public static void addFragmenttoActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag){

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frameId, fragment, tag);
        ft.commit();
    }
}
