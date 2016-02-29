package com.example.BasicInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.text.format.Formatter;

public class MemoryInfoUtil {

	private static Context context;

	private static List<String> memoryInfo = new ArrayList(); // ����ڴ���Ϣ��list

	static {
		// �����ڴ��ֵ����meminfo
		String totalMem = "Total_Memory:" + getTotalMemory();
		memoryInfo.add(totalMem);
	}

	public static List<String> getMemInfo(Context context) {
		MemoryInfoUtil.context = context;
		List<String> memInfoToReturn = new ArrayList();
		memInfoToReturn.addAll(memoryInfo);

		// �������ڴ�ֵ����info
		String availMem = "Available_Memory:" + getAvailMemory();
		memInfoToReturn.add(availMem);
		return memInfoToReturn;
	}

	// ��ȡ���ڴ��ֵ
	private static String getTotalMemory() {
		String mem = "/proc/meminfo";// ϵͳ�ڴ���Ϣ�ļ�
		String valueString;
		long initial_memory = 0;
		double result1;
		String[] arrayOfString = { "", "" };
		String result = null;

		try {
			FileReader localFileReader = new FileReader(mem);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			valueString = localBufferedReader.readLine();// ��ȡmeminfo��һ�У�ϵͳ���ڴ��С

			arrayOfString = valueString.split("\\s+");
			initial_memory = Integer.valueOf(arrayOfString[1]);// �õ����ڴ��ֵ����λΪB
			result1 = initial_memory / 1024 / 1024;
			result = result1 + " GB";

			localBufferedReader.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡ��ǰ�����ڴ��С
	private static String getAvailMemory() {
		ActivityManager am = (ActivityManager)context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);// ����ȡ���ڴ��С���
	}
}
