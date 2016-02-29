package com.example.SetCpuMode;

import android.R.integer;
import android.R.string;

	/**
	 * Cpu����ģʽѡ�����
	 */

public class CpuControlMode {

	

	/**
	 * �������cpuƵ��,�������ֻ���ʱ����������Ƶ��,
	 * ���������Ӧ�ú��Ѹ�����������Ƶ��,������ʱѸ�ٽ���Ƶ��,���ܽ��ȶ�,����Ƶ�ʱ仯���ȹ���,
	 * ʡ�緽��ֻ��һ���ˮƽ����һ���ڵ�غ�����֮������ƽ���Ĭ��ģʽ, ���Ƕ��������ֻ���˵,
	 * ondemand�����ܱ��ַ�������Ƿȱ��
	 */
	public static final String OnDemand = "ondemand";
	
	public static final String NORMAL_HELP_INFO =
			"��ʾ��  "+"\n"+"��ģʽΪ�ֻ��Դ�CPU����ģʽ";

	
	/**
	 * �κ�����¶������CPU���������õ�Ƶ�ʷ�Χ��,�����е��û��Լ���ӵ�ʡ�����á�
	 *  �ڴ��龰ģʽ��,����CPU�������Ƶ�ʿ����ӳ���ش���ʱ��,
	 * ��ͬʱҲ�ή�ͻ����Ļ����ٶ�,������ò�ʹ�ø�ѡ�
	 */
	public static final String UserSpace = "userspace";

	/**
	 * ���趨���Ƶ������,�ճ�û��ʹ�ü�ֵ,�������setcpu�龰ģʽ,
	 * ����˯��ʱʹ�ô˵���ģʽ,ʡ�絫ϵͳ��Ӧ�ٶ�����
	 */
	public static final String PowerSave = "powersave";
	public static final String POWERSAVE_HELP_INFO = 
			"��ʾ��  "+"\n"+"���趨���Ƶ������,����˯��ʱ�Ƽ�ʹ�ô˵���ģʽ,�ǳ�ʡ�絫ϵͳ��Ӧ�ٶ���";
	
	/**
	 * Դ�������Ի�����������һ��������,
	 * ���Ե������ص���CPU�ں�,���������ܲ���Ҳ��ʡ�硣
	 */
	public static final String Pegasusq = "pegasusq";
	
	/**
	 * ������ģʽ,�����趨��Χ�����Ƶ������,��ʹϵͳ���طǳ���cpu��Ƶ��ҲΪ��ߡ�
	 * ���ܺܺ�,��ΪCPU������Ҫ��Դȥ����Ƶ��,���ǵ������ĽϿ�,�¶�Ҳ��һЩ.
	 */
	
	public static final String Perfromance = "performance";
	
	public static final String PERFROMANCE_HELP_INFO = 
			"��ʾ��  "+"\n"+"������ģʽ,�����趨��Χ�����Ƶ������," +
					"��ʹ�ֻ����ڿ���״̬,Ƶ��ҲΪ���,�ٶȼ���,���������ĽϿ�,�¶Ƚϸ�";
	


	public static String getPowersave() {
		return "�ͺ�ģʽ";
	}

	public static String getPegasusq() {
		//����ģʽ
		return "����ģʽ";
	}

	public static String getPerfromance() {
		return "���ģʽ";
	}

	public static String getNormal() {
		return "����ģʽ";
	}
	
	
	
	
}
