package com.example.batteryv2;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.example.SavePowerMode.SavePowerModeActivity;
import com.example.batteryv2.R;
import com.example.JavaEMail.EmailSender;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.BootService.PollingUtils;
import com.example.DBHelper.BatteryInfo;
import com.example.DBHelper.DBService;
/**
 * Created by admin on 13-11-23.
 */
public class SqlModeFragment extends Fragment {
	
	private Context context;
	//���ݿⰴ��
	private LinearLayout sqlSwitchLayout;
	//�����ʼ�����
	private LinearLayout emaiLayout;
	//���ݿ��α�
	private SQLiteDatabase db;
	//��ȡSQģʽ����
	private SharedPreferences sqlModeSharePre;
	private TextView sqlSwitchTxtTextView;
	//DBHelper
	private DBService dbHelper;
	private BatteryInfo batteryInfo;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sal_mode_activity, null);
        sqlSwitchLayout = (LinearLayout) view.findViewById(R.id.sql_mode_switch_layout);
        sqlModeSharePre = context.getSharedPreferences("sqlMode", context.MODE_WORLD_READABLE);
        sqlSwitchTxtTextView = (TextView) view.findViewById(R.id.sql_mode_switch_txt);
        emaiLayout = (LinearLayout) view.findViewById(R.id.send_email_switch_layout);
        batteryInfo = new BatteryInfo();
        
        
        if(sqlModeSharePre.getString("sqlswitchtxtmode", "").equals("�Ѵ�")){
        	sqlSwitchTxtTextView.setText("�Ѵ�");
        	sqlSwitchTxtTextView.setTextColor(0xff70bd34);       	
        }
        
        sqlSwitchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//�������ݿ� 
				if (!sqlModeSharePre.contains("sqldb")) {
					// �򿪴��� BatteryInfo.BATTY_TABLE
					db = context.openOrCreateDatabase("battery.db", Context.MODE_WORLD_READABLE, null);
					db.execSQL("DROP TABLE IF EXISTS " + BatteryInfo.BATTERY_TABLE);
					// ���� BatteryInfo.BATTY_TABLE
					db.execSQL(BatteryInfo.CREATE_BATTERY_INFO_TABLE);
					
					//��ȡ�͵�������ֵ�Լ�����ģʽ
					SharedPreferences.Editor editor = sqlModeSharePre.edit();
					editor.putString("sqldb", "true");
					editor.commit();
					db.close();
				}
				
				if(sqlSwitchTxtTextView.getText().toString().equals("�ѹر�")){
					sqlSwitchTxtTextView.setText("�Ѵ�");
					//ӫ����ɫ
					sqlSwitchTxtTextView.setTextColor(0xff70bd34);
					SharedPreferences.Editor editor = sqlModeSharePre.edit();
					editor.putString("sqlswitchtxtmode", "�Ѵ�");
					editor.commit();
					db = context.openOrCreateDatabase("battery.db", Context.MODE_WORLD_READABLE, null);
					// ���� BatteryInfo.BATTY_TABLE
					db.execSQL(BatteryInfo.DROP_BATTY_TABLE);
					db.execSQL(BatteryInfo.CREATE_BATTERY_INFO_TABLE);
					db.close();
					//������ѯ����
					PollingUtils.startPollingService(context, 5, DBService.class, DBService.DBService_Path);					
				}else {
					sqlSwitchTxtTextView.setText("�ѹر�");	
					//�ر���ɫ
					sqlSwitchTxtTextView.setTextColor(0xff586272);
					SharedPreferences.Editor editor = sqlModeSharePre.edit();
					editor.putString("sqlswitchtxtmode", "�ѹر�");
					editor.commit();
					//�ر���ѯ����
					PollingUtils.stopPollingService(context, DBService.class, DBService.DBService_Path);
					//db.close();
				}
			}
		});
        
        emaiLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

            		@Override
            		public void run() {
            			try {
            				EmailSender sender = new EmailSender();
            				//���÷�������ַ�Ͷ˿ڣ������ѵĵ�
            				sender.setProperties("smtp.sina.com", "25");
            				//�ֱ����÷����ˣ��ʼ�������ı�����
            				sender.setMessage("cetctestbattery@sina.com", "battery", "Battery APP DB !!");
            				//�����ռ���
            				sender.setReceiver(new String[]{"544355066@qq.com"});
            				//��Ӹ���
            				sender.addAttachment();
            				//�����ʼ�
            				sender.sendEmail("smtp.sina.com", "cetctestbattery@sina.com", "cetctest2015");
            			} catch (AddressException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			} catch (MessagingException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
            		}
            	}).start();
				
			}
		});
        
        
        return view;
    }
    
    Runnable sendEmailRun = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	};
    
    
    /**
	 * onAttach() ������ Fragment �������ȵ��ã������onCreate()
	 */

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}
	
}
