package com.ssf.edog;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected SharedPreferenceUtil mPreferenceUtil;

	/**
	 * ���µ�Activity
	 * 
	 * @param clazz
	 */
	protected void openActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	/**
	 * �����ݷ�װ��һ��Bundle����Ȼ�󴫵ݸ�Ҫ�򿪵�Activity
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
