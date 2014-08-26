package com.ssf.edog;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ModifyActivity extends Activity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private EditText mNewPwdEdt;
	private EditText mReNewPwdEdt;
	private Button mSaveButton;

	private ImageView mFinishBtn;// 退出程序按钮

	private String mReNewPwd;
	private String mNewPwd;// 新密码

	private AlertDialog mAlertDialog;

	private SharedPreferenceUtil mPreferenceUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		mPreferenceUtil = new SharedPreferenceUtil(this);
		initView();
	}

	/**
	 * 初始化UI组件
	 */
	private void initView() {

		mNewPwdEdt = (EditText) findViewById(R.id.new_pwd);
		mReNewPwdEdt = (EditText) findViewById(R.id.re_new_pwd);

		mSaveButton = (Button) findViewById(R.id.save_setting);
		mSaveButton.setOnClickListener(this);

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		mAlertDialog = new AlertDialog.Builder(this).setNeutralButton(
				getString(R.string.confirm), this).create();
	}

	private void initData() {
		mNewPwd = mNewPwdEdt.getText().toString().trim();
		mReNewPwd = mReNewPwdEdt.getText().toString().trim();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.save_setting: {
			initData();

			if (verifyNewPassword(mNewPwd, mReNewPwd)) {
				mPreferenceUtil.savePassword(mNewPwd);
				mAlertDialog
						.setMessage(getString(R.string.modify_password_success));
				mAlertDialog.show();
			}
		}
			break;

		case R.id.finish:

			finish();

			break;
		default:
			break;
		}

	}

	/**
	 * 验证用户输入的新密码是否满足指定条件
	 * 
	 * @param newPwd
	 * @return
	 */
	private boolean verifyNewPassword(String newPwd, String reNewPwd) {

		if (newPwd == null || "".equals(newPwd)) {

			mNewPwdEdt.setError(getString(R.string.password_can_not_null));
			mNewPwdEdt.requestFocus();

			return false;

		} else if (newPwd.length() < 4) {

			mNewPwdEdt
					.setError(getString(R.string.password_must_than_length_than_four_big));
			mNewPwdEdt.requestFocus();
			return false;

		}

		if (newPwd.equals(reNewPwd)) {
			return true;
		} else {
			mReNewPwdEdt.setError(getString(R.string.password_diff_error));
			mReNewPwdEdt.requestFocus();

		}

		return false;

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		finish();

	}
}
