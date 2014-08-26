package com.ssf.edog.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

	/**
	 * ���ӹ����ص�key
	 */
	private static final String SWITCH = "switch";

	/**
	 * ���ӹ����ص�Ĭ��״̬
	 */
	private static final boolean DEFAULT_SWITCH = false;

	/**
	 * ���ڱ������ݵ��ļ���
	 */
	private static final String FILE_NAME = "edog";

	/**
	 * ����Ա��������Ӧ��key
	 */
	public static final String PWD_KEY = "pwd";

	/**
	 * ���ӹ���̽ʱ��������Ӧ��key
	 */
	public static final String INTERVAL_KEY = "interval";

	/**
	 * Ĭ�ϵĹ���Ա����
	 */
	private static final String DEFAULT_PWD = "123456";

	/**
	 * Ĭ�ϵĵ��ӹ���̽ʱ����
	 */
	private static final int DEFAULT_INTERVAL = 3;

	public static final String ON_HOUR_KEY = "on_hour";
	public static final String ON_MINUTE_KEY = "on_minute";
	public static final String OFF_HOUR_KEY = "off_hour";
	public static final String OFF_MINUTE_KEY = "off_minute";
	public static final String REBOOT_HOUR_KEY = "reboot_hour";
	public static final String REBOOT_MINUTE_KEY = "reboot_minute";
	public static final String TYPE_KEY = "type";
	public static final String IS_ENABLE_KEY = "is_enable_key";

	public static final int CLOSE_SETTING = 0;
	public static final int AUTO_ON_OFF = 1;
	public static final int AUTO_REBOOT = 2;
	public static final int AUTO_OFF = 3;

	private SharedPreferences mSharedPreferences;

	public SharedPreferenceUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
	}

	public void setOnHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(ON_HOUR_KEY, hour);
		editor.commit();
	}

	public int getOnHour() {
		return mSharedPreferences.getInt(ON_HOUR_KEY, 0);
	}

	public void setOnMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(ON_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getOnMinute() {
		return mSharedPreferences.getInt(ON_MINUTE_KEY, 0);
	}

	public void setOffHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(OFF_HOUR_KEY, hour);
		editor.commit();
	}

	public int getOffHour() {
		return mSharedPreferences.getInt(OFF_HOUR_KEY, 0);
	}

	public void setOffMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(OFF_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getOffMinute() {
		return mSharedPreferences.getInt(OFF_MINUTE_KEY, 0);
	}

	public void setRebootHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(REBOOT_HOUR_KEY, hour);
		editor.commit();
	}

	public int getRebootHour() {
		return mSharedPreferences.getInt(REBOOT_HOUR_KEY, 0);
	}

	public void setRebootMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(REBOOT_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getRebootMinute() {
		return mSharedPreferences.getInt(REBOOT_MINUTE_KEY, 0);
	}

	public void setType(int type) {

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(TYPE_KEY, type);
		editor.commit();

	}

	public int getType() {
		return mSharedPreferences.getInt(TYPE_KEY, CLOSE_SETTING);
	}

	public void setEnable(boolean enable) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(IS_ENABLE_KEY, enable);
		editor.commit();
	}

	public boolean isEnable() {
		return mSharedPreferences.getBoolean(IS_ENABLE_KEY, false);
	}

	/**
	 * �������Ա���뵽�ļ���
	 * 
	 * @param pwd
	 */
	public void savePassword(String pwd) {

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(PWD_KEY, pwd);
		editor.commit();

	}

	/**
	 * ���ļ���ù���Ա����
	 * 
	 * @return
	 */
	public String getPassword() {
		return mSharedPreferences.getString(PWD_KEY, DEFAULT_PWD);
	}

	/**
	 * ������ӹ���̽ʱ�������ļ���
	 * 
	 * @param interval
	 */
	public void saveInterval(int interval) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(INTERVAL_KEY, interval);
		editor.commit();
	}

	/**
	 * ���ļ��л�õ��ӹ���̽ʱ����
	 * 
	 * @return
	 */
	public int getInterval() {
		return mSharedPreferences.getInt(INTERVAL_KEY, DEFAULT_INTERVAL);
	}

	/**
	 * ���ڱ�����ӹ��Ŀ���״̬
	 * 
	 * @param is
	 */
	public void saveSwitch(boolean is) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(SWITCH, is);
		editor.commit();
	}

	/**
	 * �õ����ӹ��Ŀ���״̬
	 * 
	 * @return
	 */
	public boolean isSwitch() {
		return mSharedPreferences.getBoolean(SWITCH, DEFAULT_SWITCH);
	}

	/**
	 * ����ļ��б��������
	 */
	public void clear() {
		mSharedPreferences.edit().clear().commit();
	}

}
