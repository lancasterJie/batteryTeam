package com.example.SavePowerMode;


import java.util.List;

import com.example.batteryv2.R;

import android.R.plurals;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SavePowerModeActivity extends Activity {
	
	
	//�͵���ģʽ ����
	private LinearLayout lowmodeSwitchLayout;
	private TextView pLowModeSwitchTextView;
	
	//�͵�������ֵ
	private LinearLayout pLowValueSwitchLayout;
	private TextView pLowValueSwitchTextView;
	
	private LinearLayout pLowModeChoiceLayout;
	private TextView pLowModeChoiceTextView;
	
	//��ȡ�͵���ģʽ����
	private SharedPreferences lowPowerModeSharePre;
	
	//�̼߳��
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_low_mode_activity);
		lowmodeSwitchLayout = (LinearLayout) findViewById(R.id.power_low_mode_switch);
		pLowModeSwitchTextView = (TextView) findViewById(R.id.power_low_state_switch);
		
		pLowValueSwitchLayout = (LinearLayout) findViewById(R.id.power_low_value_layout);
		pLowValueSwitchTextView = (TextView) findViewById(R.id.power_low_value_txt);
		
		pLowModeChoiceLayout = (LinearLayout) findViewById(R.id.power_low_mode_layout);
		pLowModeChoiceTextView = (TextView) findViewById(R.id.power_low_mode_txt);
		
		lowPowerModeSharePre = getSharedPreferences("lowPowerMode", SavePowerModeActivity.MODE_WORLD_READABLE);
	
		//�ж��Ƿ��Ѿ����˵͵������
		if(lowPowerModeSharePre.getString("pLowModeSwitchTxt", "").equals("�Ѵ�")){
			//��������
			pLowModeSwitchTextView.setText("�Ѵ�");
			pLowModeSwitchTextView.setTextColor(0xff70bd34);
			//����ֵ����
			pLowValueSwitchTextView.setText(lowPowerModeSharePre.getString("StartValueOfPower", ""));
            pLowValueSwitchTextView.setTextColor(0xff586272);
            
            //registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		}
		
		/* �͵���ģʽ��������   */
		final Intent intent = new Intent(this, MonitorService.class);
		lowmodeSwitchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(pLowModeSwitchTextView.getText().toString().equals("�ѹر�")){
					pLowModeSwitchTextView.setText("�Ѵ�");
					//registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
					//ӫ����ɫ
					pLowModeSwitchTextView.setTextColor(0xff70bd34);
					//��ȡ�͵�������ֵ�Լ�����ģʽ
					SharedPreferences.Editor editor = lowPowerModeSharePre.edit();
					editor.putString("StartValueOfPower", pLowValueSwitchTextView.getText().toString());
					editor.putString("pLowModeSwitchTxt", "�Ѵ�");
					editor.putString("StartValueOfPower", pLowValueSwitchTextView.getText().toString());
					editor.commit();
					//�� Service��ֵ 
					//intent.putExtra("StartValueOfPower", pLowValueSwitchTextView.getText().toString());
					//������ؼ�ط���  	
					startService(intent);
				    			
				}else {
					pLowModeSwitchTextView.setText("�ѹر�");	
					stopService(intent);					
					SharedPreferences.Editor editor = lowPowerModeSharePre.edit();
					editor.putString("pLowModeSwitchTxt", "�ѹر�");
					editor.commit();
					//unregisterReceiver(batteryChangedReceiver);
					//�ر���ɫ
					pLowModeSwitchTextView.setTextColor(0xff586272);
				}
			}
		});
		
		/*   �͵�������ֵ����   */
		 
		pLowValueSwitchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(SavePowerModeActivity.this);
                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("ѡ��һ������ֵ");
                //    ָ�������б����ʾ����
                final String[] values = {"5%", "10%", "15%", "20%"};
                //    ����һ���������б�ѡ����
                builder.setItems(values, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int value)
                    {
                    	SharedPreferences.Editor editor = lowPowerModeSharePre.edit();
    					editor.putString("StartValueOfPower", values[value]);
    					editor.commit();
                        pLowValueSwitchTextView.setText(values[value]);
                        pLowValueSwitchTextView.setTextColor(0xff586272);
                    }
                });
                builder.show();
			}
		});
		
		
		
		/*  ģʽ��������   
		pLowModeChoiceLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(SavePowerModeActivity.this);
                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("ѡ��һ��ģʽ");
                //    ָ�������б����ʾ����
                final String[] values = {"����ģʽ", "�͹���ģʽ"};
                //    ����һ���������б�ѡ����
                builder.setItems(values, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int value)
                    {
                        pLowModeChoiceTextView.setText(values[value]);
                        pLowModeChoiceTextView.setTextColor(0xff586272);
                        
                        /* ����ģʽ����  */
                        /*boolean isEnabled = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;  
                        Settings.System.putInt(getContentResolver(),Settings.System.AIRPLANE_MODE_ON, isEnabled?0:1);  
                        Intent i=new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);  
                        i.putExtra("state", !isEnabled);  
                        sendBroadcast(i);
                    }
                });
                builder.show();
			}
		});*/
	}
	
	
	/**
	 * ��ȡ�͵�������ֵ
	 * @return
	 */
	
	public int getLowPowerValue() {	
		return stringtoInt(lowPowerModeSharePre.getString("StartValueOfPower", ""));
	}
	
	/**
	 * �㲥������������%ֵ 
	 * @author GYC
	 *
	 */
    /*private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {  
          
        @Override  
        public void onReceive(Context context, Intent intent) {
              
            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){  
                int lowPowValue;  
                int level = intent.getIntExtra("level", 0);   
                lowPowValue = stringtoInt(lowPowerModeSharePre.getString("StartValueOfPower", ""));
                if(level > lowPowValue){
                	Toast.makeText(context, "Test:"+lowPowValue, Toast.LENGTH_LONG).show();
                }
            } 
        }  
    };*/
    
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
    
    

	
}
