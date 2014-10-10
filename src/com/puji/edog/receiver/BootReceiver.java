package com.puji.edog.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.puji.edog.config.Config;
import com.puji.edog.service.EdogService;
import com.puji.edog.util.MachineUtil;
import com.puji.edog.util.SharedPreferenceUtil;
import com.puji.edog.util.TimeUtils;

public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = "BootReceiver";
	private static final int SUCCESS = 1;
	private static final long DURATION = 60000;// 开机后延迟启动电子狗的时间

	private Context mContent;
	private AlarmManager mAlarmManager;
	private SharedPreferenceUtil mPreferenceUtil;
	private MachineUtil mMachineUtil;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case SUCCESS:

				mPreferenceUtil.setEnable(true);
				mContent.startService(new Intent(mContent, EdogService.class));
				Log.i(TAG, "boot edog");
				break;

			default:
				break;
			}
		};

	};

	public BootReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		mContent = context;
		mAlarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		mPreferenceUtil = new SharedPreferenceUtil(context);
		mMachineUtil = new MachineUtil();
		mMachineUtil.close();

		Intent newIntent = new Intent(Config.SWITCH_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			if (mPreferenceUtil.isSwitch()) {

				mHandler.sendEmptyMessageDelayed(SUCCESS, DURATION);

			}

			switch (mPreferenceUtil.getType()) {

			case SharedPreferenceUtil.AUTO_REBOOT:

				mAlarmManager.set(AlarmManager.RTC_WAKEUP, TimeUtils
						.calculateRebootTime(mPreferenceUtil.getRebootHour(),
								mPreferenceUtil.getRebootMinute()),
						pendingIntent);

				break;
			case SharedPreferenceUtil.AUTO_OFF:

				mAlarmManager.set(
						AlarmManager.RTC_WAKEUP,
						TimeUtils.calculateRebootTime(
								mPreferenceUtil.getOffHour(),
								mPreferenceUtil.getOffMinute()), pendingIntent);

				break;
			case SharedPreferenceUtil.AUTO_ON_OFF:

				int offHour = mPreferenceUtil.getOffHour();
				int offMinute = mPreferenceUtil.getOffMinute();
				int onHour = mPreferenceUtil.getOnHour();
				int onMinute = mPreferenceUtil.getOnMinute();

				mMachineUtil.setBoffh((byte) offHour);
				mMachineUtil.setBoffm((byte) offMinute);
				mMachineUtil.setBonh((byte) onHour);
				mMachineUtil.setBonm((byte) onMinute);

				mMachineUtil.openMachine();

				break;

			default:
				break;
			}

		}
	}
}
