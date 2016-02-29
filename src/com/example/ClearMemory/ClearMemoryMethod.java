package com.example.ClearMemory;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ClearMemoryMethod {
	private static final String TAG = "ClearMemoryMethod";
	
	private Context context;
	
	public  ClearMemoryMethod(Context context) {
		this.context = context;
	}
	
	public void clearMemory() {
		//To change body of implemented methods use File | Settings | File Templates. 
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningAppProcessInfo> infoList = am.getRunningAppProcesses(); 
		//List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100); 
   
		long beforeMem = getAvailMemory(); 
		Log.i(TAG, "-----------before memory info : " + beforeMem); 
		int count = 0; 
		if (infoList != null) { 
			for (int i = 0; i < infoList.size(); ++i) { 
				RunningAppProcessInfo appProcessInfo = infoList.get(i); 
				Log.i(TAG, "process name : " + appProcessInfo.processName); 
				//importance �ý��̵���Ҫ�̶�  ��Ϊ����������ֵԽ�;�Խ��Ҫ�� 
				Log.i(TAG, "importance : " + appProcessInfo.importance); 
   
				// һ����ֵ����RunningAppProcessInfo.IMPORTANCE_SERVICE�Ľ��̶���ʱ��û�û��߿ս����� 
				// һ����ֵ����RunningAppProcessInfo.IMPORTANCE_VISIBLE�Ľ��̶��Ƿǿɼ����̣�Ҳ�����ں�̨������ 
				if (appProcessInfo.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) { 
					String[] pkgList = appProcessInfo.pkgList; 
					for (int j = 0; j < pkgList.length; ++j) {//pkgList �õ��ý��������еİ��� 
						Log.i(TAG, "It will be killed, package name : " + pkgList[j]); 
						am.killBackgroundProcesses(pkgList[j]); 
						count++; 
					} 
				} 
   
			} 
		} 
   
		long afterMem = getAvailMemory(); 
		Log.i(TAG, "----------- after memory info : " + afterMem); 
		//Toast.makeText(context, "clear " + count + " process, " 
		//		+ (afterMem - beforeMem) + "M", Toast.LENGTH_LONG).show(); 
   
		//ClearMemoryFloatView.instance(getApplicationContext()).createView();
		
	}
	
	
	
	
	
	//��ȡ�����ڴ��С 
    private long getAvailMemory() { 
        // ��ȡandroid��ǰ�����ڴ��С 
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
        MemoryInfo mi = new MemoryInfo(); 
        am.getMemoryInfo(mi); 
        //mi.availMem; ��ǰϵͳ�Ŀ����ڴ� 
        //return Formatter.formatFileSize(context, mi.availMem);// ����ȡ���ڴ��С��� 
        Log.d(TAG, "�����ڴ�---->>>" + mi.availMem / (1024 * 1024)); 
        return mi.availMem / (1024 * 1024); 
    }

}
