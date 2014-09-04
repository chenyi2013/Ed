package com.puji.edog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puji.edog.config.Config;
import com.puji.edog.util.SharedPreferenceUtil;
import com.puji.edog.R;

/**
 * 
 * @author Administrator
 * 
 */
public class KeyguardActivity extends Activity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private int time = 0;// 等待用户输入密码的时间
	private static final int SUCESS = 1;

	private TextView mPromptTv;
	private Button mEnterHomeBtn;
	private EditText mPwdText;
	private ImageView mFinishBtn;
	protected PackageManager mPackageManager;
	protected SharedPreferenceUtil mPreferenceUtil;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == SUCESS) {

				if (time >= 0) {// 等待用户输入密码的时间还未结束，提示用户输入密码
					mPromptTv.setText(String.format(
							getResources().getString(R.string.alert_display),
							time--));
					mHandler.sendEmptyMessageDelayed(1, 1000);
				} else {// 等待用户输入密码的时间已结束,启动普及管家

					Intent intent = mPackageManager
							.getLaunchIntentForPackage(mPreferenceUtil
									.getPackage());

					if (intent != null) {
						startActivity(intent);
						finish();
					} else {
						mPreferenceUtil.saveSwitch(false);
						mPreferenceUtil.setEnable(false);
						finish();
					}

				}

			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyguard);
		mPackageManager = getPackageManager();
		mPreferenceUtil = new SharedPreferenceUtil(this);
		initView();
		if (mPreferenceUtil.getPackage() == null) {
			Intent intent = new Intent(KeyguardActivity.this,
					PickAppDialogAct.class);
			startActivity(intent);
			finish();
		}

	}

	/**
	 * 初始化UI组件
	 */
	private void initView() {
		mPromptTv = (TextView) findViewById(R.id.info);
		mEnterHomeBtn = (Button) findViewById(R.id.enter_home);
		mEnterHomeBtn.setOnClickListener(this);
		mPwdText = (EditText) findViewById(R.id.password_text);
		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setCancelable(false);
		builder.setNegativeButton(getString(R.string.confirm), this);
		builder.setTitle(getString(R.string.info_prompt_title));
		builder.setMessage(getString(R.string.not_install_puji_guanjia));

		builder.create();

	}

	@Override
	protected void onResume() {
		super.onResume();
		time = Config.WAIT_REBOOT_TIME;// 初始化等待用户输入的时间
		mHandler.sendEmptyMessageDelayed(SUCESS, 1000);// 启动计时器
	}

	@Override
	protected void onPause() {

		super.onPause();
		mHandler.removeMessages(SUCESS);// 停止计时
	}

	/**
	 * 验证用户输入的密码是否正确，如果正确取消打开普及管家，否则提示错误信息
	 */
	public void verifyPassword() {

		String pwd = mPwdText.getText().toString().trim();

		if (pwd.equals(mPreferenceUtil.getPassword())) {

			mPreferenceUtil.setEnable(false);
			finish();

		} else {

			mPwdText.setError(getResources().getString(R.string.error_display));
			mPwdText.requestFocus();
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.enter_home:
			verifyPassword();
			break;
		case R.id.finish:
			Intent intent = mPackageManager
					.getLaunchIntentForPackage(mPreferenceUtil.getPackage());
			startActivity(intent);
			finish();
		default:
			break;
		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		mPreferenceUtil.saveSwitch(false);
		mPreferenceUtil.setEnable(false);
		finish();

	}
}
