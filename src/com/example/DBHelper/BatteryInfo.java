package com.example.DBHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.R.string;

public class BatteryInfo {
	
	public static final String BATTERY_TABLE = "batteryinfo";
	
	public static String ID = "_id";  //����
	public static String TIME = "time";  //ʱ��
	public static String CPU_E = "cpu_e"; //CPUЧ��
	public static String MEM_E = "mem_e"; //�ڴ�ʹ����
	public static String WIFI_DATA = "wifi_data"; //wifiʹ����
	public static String MOBILE_DATA = "mobile_data"; //�ƶ�����ʹ��
	public static String GPS = "gps"; //GPS�Ƿ���
	public static String BRIGHTNESS = "brightness"; //��Ļ����
	public static String CONNECT = "connecting"; //��ǰ����
	public static String RSSI = "rssi"; //Received Signal Strength Indication���յ��ź�ǿ��ָʾ
	/*Reference Signal Receiving Power���ο��źŽ��չ���) 
	 * ��LTE�����п��Դ��������ź�ǿ�ȵĹؼ������Լ�������������֮һ��
	 * ����ĳ�������ڳ��زο��źŵ�����RE(��Դ����)�Ͻ��յ����źŹ��ʵ�ƽ��ֵ*/
	public static String RSPR = "rspr"; 
	public static String SNR = "snr"; //�����
	public static String LONGITUDE = "longitude"; //����
	public static String LATITUDE = "latitude"; //γ��
	public static String TEMPERATURE = "temperature"; //����¶� 
	public static String VOLTAGE = "voltage"; //��ѹ 
	public static String ELECTRICITY = "level"; //���� 
	
	
	/*����DB���ݱ�*/
	public static final String CREATE_BATTERY_INFO_TABLE = "create table if not exists "
			+ BATTERY_TABLE 
			+ "(" + ID + " integer primary key autoincrement, "
			+ TIME + " varchar, "
			+ ELECTRICITY + " varchar, "
			+ TEMPERATURE + " varchar, "			
			+ VOLTAGE + " varchar, "			
			+ CPU_E + " varchar, "
			+ MEM_E + " varchar, "
			+ WIFI_DATA + " varchar, "
			+ MOBILE_DATA + " varchar, "
			+ GPS + " varchar, "
			+ BRIGHTNESS + " varchar, "
			+ CONNECT + " varchar, "
			+ RSSI + " varchar, "
			+ RSPR + " varchar, "
			+ SNR + " varchar, "
			+ LONGITUDE + " varchar, "
			+ LATITUDE + " varchar) ";

	/*ɾ��DB���ݱ� */
	public static final String DROP_BATTY_TABLE = "drop table if exists " + BATTERY_TABLE;
	
	
	public BatteryInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ���ݿ�Ƕ������ ID Ϊ���������� ֻ���ʼ��ΪNULL SQLite�������� 
	 * time ��¼ʱ��
	 * @param cpu CPU������
	 * @param mem �ڴ�������
	 * @param wifi wifiʹ����
	 * @param mobile �ƶ�����ʹ����
	 * @param gps gps����
	 * @param brightness  ��Ļ����
	 * @param connecting ��ǰ����
	 * @param rssi ���յ��ź�ǿ��ָʾ ��λdBM
	 * @param rspr �ο��źŽ��չ��� ��λdBM
	 * @param snr ����� ��λdB
	 * @param longitude ����
	 * @param latitude γ��
	 * @param temperature ����¶�
	 * @param voltage ��ѹ
	 * @param electricity ��ص���
	 */

	public BatteryInfo(String electricity, String temperature, String voltage, String cpu, String mem,
			String wifi, String mobile, Boolean gps, String brightness, String connecting,
			String rssi, String rspr, String snr, String longitude, String latitude) {		
		this.ID = null;
		// ��ȡʱ��
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy��MM��dd��    HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);
		this.TIME = str;
		this.ELECTRICITY = electricity + "%";
		this.TEMPERATURE = temperature;
		this.VOLTAGE = voltage + "v";
		this.CPU_E = cpu;
		this.MEM_E = mem;
		this.WIFI_DATA = wifi;
		this.MOBILE_DATA = mobile;
		this.GPS = String.valueOf(gps);
		this.BRIGHTNESS = brightness;
		this.CONNECT = connecting;
		this.RSSI = rssi + "dBM";
		this.RSPR = rspr + "dBM";
		this.SNR = snr + "dB";	
		this.LONGITUDE = longitude;
		this.LATITUDE = latitude;
		
		/*try {
		db.execSQL("INSERT INTO batteryinfo VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] {
				batteryInfo.TIME, batteryInfo.CPU_E, batteryInfo.MEM_E,
				batteryInfo.WIFI_USAGE, batteryInfo.FLOW_USAGE, batteryInfo.GPS,
				batteryInfo.SCREEN_B, batteryInfo.NET_NOW, batteryInfo.RSSI,
				batteryInfo.RSPR, batteryInfo.SNR});
		
		} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		}*/
	}
}
