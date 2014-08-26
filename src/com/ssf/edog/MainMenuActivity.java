package com.ssf.edog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenuActivity extends BaseActivity implements OnClickListener {

	private Button mPowerBtn;// �Զ����ػ����ð�ť
	private Button mEdogBtn;// ���ӹ����ð�ť
	private Button mPwdSettingBtn;// �������ð�ť

	private ImageView mFinishBtn;// �˳�����ť

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

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

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
		default:
			break;
		}

	}
}
