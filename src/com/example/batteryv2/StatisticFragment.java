package com.example.batteryv2;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.getAppInfo.AppInfoSort;
import com.example.getAppInfo.BrowseRunningAppAdapter;
import com.example.getAppInfo.RunningAppInfo;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Created by daydreamren on 7/11/14.
 * This fragment implement the battery consumption statistic
 * 1>get app icon, name , pkgname 
 * 2>can stop the running app
 */
public class StatisticFragment extends Fragment {
	private static String TAG = "BrowseRunningAppActivity";

	private ListView listview = null;

	private List<RunningAppInfo> mlistAppInfo = null;
    private TextView tvInfo = null ;
    
	private PackageManager pm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //��ת����ʾ������Ϣ����
    	View view = inflater.inflate(R.layout.browse_app_list, null);
		listview = (ListView) view.findViewById(R.id.listviewApp);
		tvInfo = (TextView)view.findViewById(R.id.tvInfo) ;
		pm= getActivity().getPackageManager();//��ð�������
	      //���ActivityManager����Ķ���
        //fragment�����ȡ�κ�id��service��Ҫ��ǰ׺
		mlistAppInfo = new ArrayList<RunningAppInfo>();//��̬����
			// ��ѯ�����������е�Ӧ�ó�����Ϣ�� �����������ڵĽ���id�ͽ�����
		tvInfo.setText("�����������е�Ӧ�ó�����-------");
		mlistAppInfo = queryAllRunningAppInfo(); 
		BrowseRunningAppAdapter browseAppAdapter = new BrowseRunningAppAdapter(getActivity(), mlistAppInfo);
		listview.setAdapter(browseAppAdapter);//����������ListView��������,ʵ�ֲ�������
		//�����¼��������Ƿ�ر�Ӧ�ã���ȷ������ת�������йر�Ӧ��
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final String killPackageName=mlistAppInfo.get(arg2).getPkgName();
				new AlertDialog.Builder(getActivity()).setTitle("Are you sure close").setPositiveButton("sure", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Uri packageURI = Uri.parse("package:" + killPackageName);//ͨ����Դ��־�� Universal Resource Identifier
				           Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);  
				           startActivity(intent);
					}
				}).setNegativeButton("cancel", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).show();
				return false;
			}
		});
		return view;
    }
 // ��ѯ�����������е�Ӧ�ó�����Ϣ�� �����������ڵĽ���id�ͽ�����
 	// �����ֱ�ӻ�ȡ��ϵͳ�ﰲװ������Ӧ�ó���Ȼ����ݱ���pkgname���˻�ȡ�����������е�Ӧ�ó���
 	private List<RunningAppInfo> queryAllRunningAppInfo() {
 		pm = getActivity().getPackageManager();
 		// ��ѯ�����Ѿ���װ��Ӧ�ó���
 		List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
// 		Collections.sort(listAppcations,new ApplicationInfo.DisplayNameComparator(pm));// ����

 		// ���������������еİ��� �Լ������ڵĽ�����Ϣ
 		Map<String, ActivityManager.RunningAppProcessInfo> pgkProcessAppMap = new HashMap<String, ActivityManager.RunningAppProcessInfo>();

 		ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
 		// ͨ������ActivityManager��getRunningAppProcesses()�������ϵͳ�������������еĽ���
 		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
 				.getRunningAppProcesses();

 		for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList) {
 			int pid = appProcess.pid; // pid
 			String processName = appProcess.processName; // ������
 			Log.i(TAG, "processName: " + processName + "  pid: " + pid);

 			String[] pkgNameList = appProcess.pkgList; // ��������ڸý����������Ӧ�ó����

 			// �������Ӧ�ó���İ���
 			for (int i = 0; i < pkgNameList.length; i++) {
 				String pkgName = pkgNameList[i];
 				Log.i(TAG, "packageName " + pkgName + " at index " + i+ " in process " + pid);
 				// ������map������
 				pgkProcessAppMap.put(pkgName, appProcess);
 			}
 		}
 		// ���������������е�Ӧ�ó�����Ϣ
 		List<RunningAppInfo> runningAppInfos = new ArrayList<RunningAppInfo>(); // ������˲鵽��AppInfo

 		for (ApplicationInfo app : listAppcations) {
 			// ����ð������� ����һ��RunningAppInfo����
 			//ֻ�Է�ϵͳӦ�ý�������
 			if((app.flags&ApplicationInfo.FLAG_SYSTEM)<=0)
 			{
 				if (pgkProcessAppMap.containsKey(app.packageName)) {
 				// ��ø�packageName�� pid �� processName
 				int pid = pgkProcessAppMap.get(app.packageName).pid;
 				String processName = pgkProcessAppMap.get(app.packageName).processName;
 				runningAppInfos.add(getAppInfo(app, pid, processName));
 			}
 			}
 		}
 		AppInfoSort appinfosort=new AppInfoSort();
// 		���ĵ������
 		Collections.sort(runningAppInfos, appinfosort);
 		return runningAppInfos;

 	}
 	
 	
 	// ����һ��RunningAppInfo���� ������ֵ
 	private RunningAppInfo getAppInfo(ApplicationInfo app, int pid, String processName) {
 		RunningAppInfo appInfo = new RunningAppInfo();
 		appInfo.setAppLabel((String) app.loadLabel(pm));
 		appInfo.setAppIcon(app.loadIcon(pm));
 		appInfo.setPkgName(app.packageName);

 		appInfo.setPid(pid);
 		appInfo.setProcessName(processName);
 		appInfo.setUpFlow(TrafficStats.getUidTxBytes(app.uid));
 		appInfo.setDownFlow(TrafficStats.getUidRxBytes(app.uid));
 		return appInfo;
 	}
}
