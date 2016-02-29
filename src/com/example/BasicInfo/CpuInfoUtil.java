package com.example.BasicInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.batteryv2.R;

public class CpuInfoUtil {

	private static List<String> cpuInfo = new ArrayList(); // ���cpu��Ϣ��list
	
	static {
		int count = Runtime.getRuntime().availableProcessors(); // ��ȡcpu�ĺ���
		// ��cpu��������cpuinfo
		String countnumber = "Cores:" + count;
		cpuInfo.add(countnumber);
		
		// cpuƵ�ʷ�Χ
		String freq = "Clock Speed:" + getCpuFrequency();
		cpuInfo.add(freq);
	}

	public static List<String> getCpuInfo() {
		// cpu������
		String load = "Load:" + readUsage();
		List<String> cpuInfoToReturn = new ArrayList();
		cpuInfoToReturn.addAll(cpuInfo);
		cpuInfoToReturn.add(load);
		return cpuInfoToReturn;
	}

	// ����Cpu��ǰ������
	private static String readUsage() {
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r"); // ��ȡcpu����״̬
			String load = reader.readLine(); // ��ȡ��һ�����ݣ�����cpu������
			String[] toks = load.split(" ");
			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			try {
				Thread.sleep(360);
			} catch (Exception e) {
			}
			reader.seek(0);
			load = reader.readLine();
			reader.close();
			toks = load.split(" ");
			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			return ((cpu2 - cpu1) * 100 / ((cpu2 + idle2) - (cpu1 + idle1)))
					+ "%";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ȡCpu��Ƶ�ʷ�Χ
	private static String getCpuFrequency() {
		String cpuMaxFreq = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
		String cpuMinFreq = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";
		String valueString;
		String otherString;
		int minCpuFreq;
		int maxCpuFreq;
		double number = Math.pow(10, 6);
		double result = 0;
		try {
			FileReader localFileReader = new FileReader(cpuMinFreq);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader);
			valueString = localBufferedReader.readLine();
			minCpuFreq = Integer.parseInt(valueString);
			if (minCpuFreq < number) {
				result = minCpuFreq / 1000;
				otherString = result + "MHZ";
			} else {
				result = minCpuFreq / number;
				otherString = result + "GHZ";
			}
			localBufferedReader.close();

			FileReader localFileReader1 = new FileReader(cpuMaxFreq);
			BufferedReader localBufferedReader1 = new BufferedReader(
					localFileReader1);
			valueString = localBufferedReader1.readLine();
			maxCpuFreq = Integer.parseInt(valueString);
			if (maxCpuFreq < number) {
				result = maxCpuFreq / 1000;
				valueString = result + "MHZ";
			} else {
				result = maxCpuFreq / number;
				valueString = result + "GHZ";
			}
			otherString = otherString + "~" + valueString;

			localBufferedReader1.close();
			return otherString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
