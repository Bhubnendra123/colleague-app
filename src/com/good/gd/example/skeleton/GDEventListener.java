/*
 *  This file contains Good Sample Code subject to the Good Dynamics SDK Terms and Conditions.
 *  (c) 2013 Good Technology Corporation. All rights reserved.
 */

package com.good.gd.example.skeleton;

import java.util.Map;


import android.util.Log;

import com.good.gd.GDStateListener;

public class GDEventListener implements GDStateListener {
	
	private static final String TAG = GDEventListener.class.getSimpleName();

	@Override
	public void onAuthorized() {

	}

	@Override
	public void onLocked() {
	
	}

	@Override
	public void onWiped() {

	}

	@Override
	public void onUpdateConfig(Map<String, Object> settings) {
	
	}

	@Override
	public void onUpdatePolicy(Map<String, Object> policyValues) {

	}

	@Override
	public void onUpdateServices() {

	}
	
	@Override
	public void onUpdateDataPlan(){
		Log.i(TAG, "onUpdateDataPlan()");
	}

    @Override
    public void onUpdateEntitlements() {

    }
}
