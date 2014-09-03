package com.ssf.edog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.ssf.edog.service.EdogService;

public class SettingActivity extends BaseActivity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private Switch mToggleButton;// 电子狗开关按钮
	private EditText mIntervalText;// 用于输入电子狗嗅探时间间隔的文本框
	private Button mSaveSettingBtn;// 保存用户设置的按钮
	private ImageView mFinishBtn;// 退出本设置界面的按钮

	private static final String TAG = "eileen";
	private AlertDialog mAlertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		initView();
	}

	/**
	 * 初始化UI组件
	 */
	public void initView() {

		mToggleButton = (Switch) findViewById(R.id.toogle);
		mToggleButton.setChecked(mPreferenceUtil.isSwitch());

		mIntervalText = (EditText) findViewById(R.id.interval);
		mIntervalText.setText(mPreferenceUtil.getInterval() + "");

		mSaveSettingBtn = (Button) findViewById(R.id.save_setting);
		mSaveSettingBtn.setOnClickListener(this);

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		mAlertDialog = new AlertDialog.Builder(this)
				.setNeutralButton(getString(R.string.confirm), this)
				.setCancelable(false)
				.setTitle(getString(R.string.info_prompt_title)).create();
	}

	/**
	 * 保存用户设置
	 */
	public void saveSetting() {

		String intervalStr = mIntervalText.getText().toString().trim();

		if (!intervalStr.matches("[0-9]+")) {
			mIntervalText.setHint(getResources().getString(
					R.string.interval_error));
			return;
		}

		int interval = Integer.parseInt(intervalStr);
		if (interval < 3) {
			mIntervalText.setError(getString(R.string.second_than_three_large));
			mIntervalText.requestFocus();
			return;
		}
		mPreferenceUtil.saveInterval(interval);

		boolean toggle = mToggleButton.isChecked();
		mPreferenceUtil.saveSwitch(toggle);

		Intent intent = new Intent(this, EdogService.class);

		stopService(intent);
		Log.i(TAG, "stopService");
		if (toggle) {
			mPreferenceUtil.setEnable(true);

			startService(intent);
			Log.i(TAG, "startService");

		}

		mAlertDialog.setMessage(getString(R.string.setting_success));
		mAlertDialog.show();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.finish:
			finish();
			break;

		case R.id.save_setting:
			saveSetting();
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		finish();
	}
}
