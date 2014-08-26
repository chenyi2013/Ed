package com.ssf.edog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

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

}
