package com.puji.edog;

import com.puji.edog.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenuActivity extends BaseActivity implements OnClickListener {

	private Button mPowerBtn;// �Զ����ػ����ð�ť
	private Button mEdogBtn;// ���ӹ����ð�ť
	private Button mPwdSettingBtn;// �������ð�ť
	private Button mOpenPuJiBtn;

	private ImageView mFinishBtn;// �˳�����ť
	private AlertDialog mAlertDialog;

	private static final int REQUEST_CODE = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		initView();
	}

	/**
	 * ��ʼ��UI���
	 */
	public void initView() {
		mPowerBtn = (Button) findViewById(R.id.on_off_btn);
		mPowerBtn.setOnClickListener(this);

		mEdogBtn = (Button) findViewById(R.id.edog_seting_btn);
		mEdogBtn.setOnClickListener(this);

		mPwdSettingBtn = (Button) findViewById(R.id.modify_pwd_setting_btn);
		mPwdSettingBtn.setOnClickListener(this);

		mOpenPuJiBtn = (Button) findViewById(R.id.open_puji_guanjia_btn);
		mOpenPuJiBtn.setOnClickListener(this);

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.info_prompt_title));
		builder.setMessage(getString(R.string.not_install_puji_guajia_text));
		builder.setNegativeButton(getString(R.string.info_prompt_title), null);

		mAlertDialog = builder.create();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			Intent intent = getPackageManager().getLaunchIntentForPackage(
					mPreferenceUtil.getPackage());

			if (intent != null) {
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.on_off_btn:
			openActivity(TimeSettingActivity.class);// ���붨ʱ���ػ�����
			break;
		case R.id.edog_seting_btn:
			openActivity(SettingActivity.class);// ������ӹ�����
			break;
		case R.id.modify_pwd_setting_btn:
			openActivity(ModifyActivity.class);// ������������
			break;
		case R.id.finish:
			finish();
			break;
		case R.id.open_puji_guanjia_btn:

			if (mPreferenceUtil.getPackage() == null) {

				Intent intent = new Intent(MainMenuActivity.this,
						PickAppDialogAct.class);
				startActivityForResult(intent, REQUEST_CODE);
				return;

			}

			Intent intent = getPackageManager().getLaunchIntentForPackage(
					mPreferenceUtil.getPackage());

			if (intent != null) {
				startActivity(intent);
				finish();
			} else {
				mAlertDialog.show();
			}

			break;
		default:
			break;
		}

	}
}
