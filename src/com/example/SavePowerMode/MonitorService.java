package com.example.SavePowerMode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MonitorService extends Service {
	
	
	private int lowValue;
	private SharedPreferences sharedPreferences;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();		
	}
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//��ȡ���͵�������ֵ
		//Log.i("MonitorService", String.valueOf(stringtoInt(intent.getStringExtra("StartValueOfPower"))));
		//lowValue = stringtoInt(intent.getStringExtra("StartValueOfPower"));
		sharedPreferences = getSharedPreferences("lowPowerMode", SavePowerModeActivity.MODE_WORLD_READABLE);
		lowValue = stringtoInt(sharedPreferences.getString("StartValueOfPower", ""));
		Log.i("MonitorService", String.valueOf(lowValue));
		// �����ص������¹㲥�Ĺ�����,ֻ���ܴ���ACTION_BATTERRY_CHANGED�¼���Intent  
	    IntentFilter batteryChangedReceiverFilter = new IntentFilter();  
	    batteryChangedReceiverFilter.addAction(Intent.ACTION_BATTERY_CHANGED);  
	       
	    // ��ϵͳע��batteryChangedReceiver������������������ʵ�ּ������ֶδ�  
	    registerReceiver(batteryChangedReceiver, batteryChangedReceiverFilter);  
	  
	   
	  
	    // ���ڳ�ʼ��������ʱϵͳ����û�з���ACTION_BATTERY_CHANGED�㲥����ô�ղ�ע����Ǹ��������������ڱ���������ʱ�������������¾��޷���ʾ��ǰ������������������һ�������㲥��������  
	    new BroadcastReceiver() {  
	      @Override
	      public void onReceive(Context context, Intent intent) {  
	        int level = intent.getIntExtra("level", 0);  //��ص����ȼ�  
	        if(level <= lowValue){
	        	Log.i("MonitorService", "100");
	        	applyCpuMode();
	        }
	      }  
	    };  
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("MonitorService", "��������");
		unregisterReceiver(batteryChangedReceiver);
		super.onDestroy();
	}
	
	/**
     * �������ٷֱ�ת��Ϊ������ 
     * @param string
     */
    public int stringtoInt(String string) {
		if(string.equals("5%"))
			return 5;
		if(string.equals("10%"))
			return 10;
		if(string.equals("15%"))
			return 15;
		if(string.equals("20%"))
			return 20;
		return 0;
	}
    
    // ���ܵ����Ϣ���µĹ㲥  
    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {  
      public void onReceive(Context context, Intent intent) {  
        int level = intent.getIntExtra("level", 0);  
        if(level <= lowValue){
        	Log.i("MonitorService", "101");
        	applyCpuMode();
        }
      }  
    };
    
    
    public void applyCpuMode() {
		try {
        	
        	//����CPUƵ��
            CommandCapture setGovernor = new CommandCapture(0,
                    "echo \"" + "powersave" +
                            "\" > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
            RootTools.getShell(true).add(setGovernor);
            //RootDeniedException | IOException rde            
        }catch (RootDeniedException rde) {
            rde.printStackTrace();
            Log.i("MonitorService", rde.getMessage());
        }catch (TimeoutException te) {
            te.printStackTrace();
            Log.i("MonitorService", te.getMessage());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("MonitorService", e.getMessage());
		} 
		
	}

}
