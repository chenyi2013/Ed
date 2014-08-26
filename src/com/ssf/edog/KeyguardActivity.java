package com.ssf.edog;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.ssf.edog.config.Config;
import com.ssf.edog.util.SharedPreferenceUtil;

/**
 * 
 * @author Administrator
 * 
 */
public class KeyguardActivity extends Activity implements OnClickListener {

	private int time = 0;// �ȴ��û����������ʱ��
	private static final int SUCESS = 1;

	private TextView mPromptTv;
	private Button mEnterHomeBtn;
	private EditText mPwdText;
	private SharedPreferenceUtil mPreferenceUtil;
	private ImageView mFinishBtn;

	protected PackageManager mPackageManager;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == SUCESS) {

				if (time != 0) {// �ȴ��û����������ʱ�仹δ��������ʾ�û���������
					mPromptTv.setText(String.format(
							getResources().getString(R.string.alert_display),
							time--));
					mHandler.sendEmptyMessageDelayed(1, 1000);
				} else {// �ȴ��û����������ʱ���ѽ���,�����ռ��ܼ�

					Intent intent = mPackageManager
							.getLaunchIntentForPackage(Config.PACKAGE_NAME);
					startActivity(intent);
					finish();
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
	}

	/**
	 * ��ʼ��UI���
	 */
	private void initView() {
		mPromptTv = (TextView) findViewById(R.id.info);
		mEnterHomeBtn = (Button) findViewById(R.id.enter_home);
		mEnterHomeBtn.setOnClickListener(this);
		mPwdText = (EditText) findViewById(R.id.password_text);
		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

	}

	@Override
	protected void onResume() {

		super.onResume();
		time = Config.WAIT_REBOOT_TIME;// ��ʼ���ȴ��û������ʱ��
		mHandler.sendEmptyMessageDelayed(SUCESS, 1000);// ������ʱ��
	}

	@Override
	protected void onPause() {

		super.onPause();
		mHandler.removeMessages(SUCESS);// ֹͣ��ʱ
	}

	/**
	 * ��֤�û�����������Ƿ���ȷ�������ȷȡ�����ռ��ܼң�������ʾ������Ϣ
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
					.getLaunchIntentForPackage(Config.PACKAGE_NAME);
			startActivity(intent);
			finish();
		default:
			break;
		}

	}
}
