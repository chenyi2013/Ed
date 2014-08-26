package com.ssf.edog;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected SharedPreferenceUtil mPreferenceUtil;

	/**
	 * 打开新的Activity
	 * 
	 * @param clazz
	 */
	protected void openActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	/**
	 * 将数据封装在一个Bundle对象然后传递给要打开的Activity
	 * 
	 * @param clazz
	 * @param bundle
	 */
	protected void openActivity(Class<?> clazz, Bundle bundle) {

		Intent intent = new Intent(this, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPreferenceUtil = new SharedPreferenceUtil(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mPreferenceUtil.setEnable(true);
	}

}
