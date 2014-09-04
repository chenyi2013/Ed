package com.puji.edog;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.puji.edog.service.EdogService;
import com.puji.edog.util.SharedPreferenceUtil;

public class PickAppDialogAct extends Activity implements OnClickListener {

	private ListView mPickAppListView;
	private List<PackageInfo> mInfos;
	private SharedPreferenceUtil mPreferenceUtil;
	private TextView mErrorTV;
	private Button mConfirmButton;
	private ProgressBar mProgressBar;

	public static final String PACKAGE_NAME = "package_name";
	public static final String APP_NAME = "app_name";
	public static final int RESULT_CODE_SUCCESS = 200;
	public static final int RESULT_CODE_ERROR = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_app_dialog);

		initView();

		mPreferenceUtil = new SharedPreferenceUtil(this);

		PackageManager manager = getPackageManager();
		mInfos = manager.getInstalledPackages(PackageManager.GET_ACTIVITIES);

		Iterator<PackageInfo> iterator = mInfos.iterator();

		while (iterator.hasNext()) {

			PackageInfo packageInfo = iterator.next();

			if (!packageInfo.packageName.contains("com.puji")) {
				iterator.remove();
			}

			if (packageInfo.packageName.contains(getPackageName())) {
				iterator.remove();
			}
		}

		if (mInfos == null || mInfos.size() == 0) {

			setTitle(R.string.info_prompt_title);
			mProgressBar.setVisibility(View.GONE);
			mErrorTV.setText(R.string.no_install_puji_product);
		} else {
			mPickAppListView.setAdapter(new PickAppAdapter());
		}

	}

	private void initView() {

		setTitle(R.string.please_choice_app);

		mErrorTV = (TextView) findViewById(R.id.error_alert_text_view);

		mConfirmButton = (Button) findViewById(R.id.confirm_button);
		mConfirmButton.setOnClickListener(this);

		mProgressBar = (ProgressBar) findViewById(R.id.app_picker_progress_bar);

		mPickAppListView = (ListView) findViewById(R.id.pick_app_list_view);

		mPickAppListView.setEmptyView(mProgressBar);
		mPickAppListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String packageName = mInfos.get(position).packageName;
				String appName = mInfos.get(position).applicationInfo
						.loadLabel(getPackageManager()).toString();

				mPreferenceUtil.setPackage(packageName);
				mPreferenceUtil.setAppName(appName);

				Intent intent = new Intent();
				intent.putExtra(PACKAGE_NAME, packageName);
				intent.putExtra(APP_NAME, appName);

				setResult(RESULT_CODE_SUCCESS, intent);
				finish();

			}
		});

	}

	class PickAppAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return mInfos.size();
		}

		@Override
		public Object getItem(int position) {

			return mInfos.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.pick_app_list_item, parent, false);
			}

			TextView appNameTv = (TextView) convertView
					.findViewById(R.id.pack_app_list_item);
			appNameTv.setText(mInfos.get(position).applicationInfo.loadLabel(
					getPackageManager()).toString());
			TextView packageName = (TextView) convertView
					.findViewById(R.id.package_name_text);
			packageName.setText(mInfos.get(position).packageName);
			ImageView appIcon = (ImageView) convertView
					.findViewById(R.id.app_icon);

			try {
				Intent intent = getPackageManager().getLaunchIntentForPackage(
						mInfos.get(position).packageName);
				if (intent != null) {
					Drawable drawable = getPackageManager().getActivityIcon(
							intent);
					appIcon.setImageDrawable(drawable);
				} else {
					appIcon.setImageResource(R.drawable.ic_launcher);
				}

			} catch (NameNotFoundException e) {

				e.printStackTrace();
			}
			return convertView;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_button:
			mPreferenceUtil.saveSwitch(false);
			Intent intent = new Intent(PickAppDialogAct.this, EdogService.class);
			stopService(intent);
			setResult(RESULT_CODE_ERROR);
			finish();
			break;

		default:
			break;
		}

	}
}
