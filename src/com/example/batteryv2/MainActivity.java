package com.example.batteryv2;


import com.example.RootManager.SystemManager;
import com.example.SaveData.SaveData;
import com.example.SetCpuMode.SetCpuModeActivity;
import com.example.batteryv2.R;
import com.stericson.RootTools.RootTools;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	private FragmentManager fragmentManager;
	private RadioGroup radioGroup;
	
	//CPU ģʽ���ݱ���
	private static  SharedPreferences cpuModeSharedPre;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		//Root  ��Ȩ
		String apkRoot = "battery" + getPackageCodePath();
		SystemManager.RootCommand(apkRoot);
		
		/* ���Root��Ȩ     ��¼�ֻ�����CPU����ģʽ  */
		cpuModeSharedPre = getSharedPreferences("cpumode", MainActivity.MODE_PRIVATE);
		SaveData.initShareMode(cpuModeSharedPre);
		SaveData.saveNorCpuMode(this);

		fragmentManager = getFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
		
		//�������߳��������
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads()  
        .detectDiskWrites()  
        .detectNetwork()  
        .penaltyLog()  
        .build());   
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects()  
        .detectLeakedClosableObjects()  
        .penaltyLog()  
        .penaltyDeath()  
        .build());

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment fragment = FragmentFactory.getInstanceByIndex(R.id.bt1);
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}
	
	
	

	/**
	 * һ��activity������resumed״̬,
	 * ��������ɵ���activity��Ӻ��Ƴ�fragment.���,����activity����resumed״̬ʱ,
	 * fragment���������ڲſ��Զ����仯.
	 * 
	 */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						FragmentTransaction transaction = fragmentManager
								.beginTransaction();
						Fragment fragment = FragmentFactory
								.getInstanceByIndex(checkedId);
						transaction.replace(R.id.content, fragment);

						transaction.commit();
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ���¼����Ϸ��ذ�ť
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
		// �����������ַ�ʽ
		// android.os.Process.killProcess(android.os.Process.myPid());
	}
}
