package com.puji.edog.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.puji.edog.config.Config;
import com.puji.edog.util.SharedPreferenceUtil;

public class EdogService extends Service {

	private String mCurrentPackageName;
	private ActivityManager mActivityManager;
	private SharedPreferenceUtil mPreferenceUtil;
	private ScheduledExecutorService mExecutorService;

	private static final int SUCCESS = 1;
	private static final int NO_CHOICE_APP = 2;

	private static final String TAG = "EdogService";

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case SUCCESS:
				Intent intent = new Intent(Config.LAUNCH_KEYGUAGRD_ACTION);
				sendBroadcast(intent);
				break;
			case NO_CHOICE_APP:
				Intent noChoiceAppIntent = new Intent(Config.NO_CHOICE_APP);
				sendBroadcast(noChoiceAppIntent);
				break;

			default:
				break;
			}

		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		getPackageManager();
		mCurrentPackageName = getPackageName();
		mPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mExecutorService = Executors.newScheduledThreadPool(1);
		mExecutorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				if (!mPreferenceUtil.isSwitch()) {
					stopSelf();
				}

				// �õ���ǰ����ǰ̨���е�Ӧ�ó������
				String runingBagName = mActivityManager.getRunningTasks(1).get(
						0).topActivity.getPackageName();

				// �����ǰ��Ҫ�����ĳ�������ǰ̨��������ִ�к�������

				if (runingBagName.equals(mPreferenceUtil.getPackage())) {
					mPreferenceUtil.setEnable(true);
					return;
				}

				// �����ǰ��ǰ̨���еĳ����ǵ��ӹ�����ִ�к�������
				if (runingBagName.equals(mCurrentPackageName)) {
					return;
				}
				// ��Ҫ�����ĳ�����ǰ̨���У��л����ó���
				if (mPreferenceUtil.getPackage() == null) {
					mHandler.sendEmptyMessage(NO_CHOICE_APP);
				} else if (mPreferenceUtil.isEnable()) {
					mHandler.sendEmptyMessage(SUCCESS);
				}

			}
		}, 0, mPreferenceUtil.getInterval(), TimeUnit.SECONDS);

		return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mExecutorService.shutdown();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
