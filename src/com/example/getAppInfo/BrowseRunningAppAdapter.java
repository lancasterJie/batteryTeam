package com.example.getAppInfo;

import java.util.List;

import com.example.batteryv2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


//�Զ����������࣬�ṩ��listView���Զ���view
public class BrowseRunningAppAdapter extends BaseAdapter {
	
	private List<RunningAppInfo> mlistAppInfo = null;
	
	//���沼���ļ�xml��findviewbyid
	LayoutInflater infater = null;
  
	public BrowseRunningAppAdapter(Context context,  List<RunningAppInfo> apps) {
		infater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mlistAppInfo = apps ;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("size" + mlistAppInfo.size());
		return mlistAppInfo.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlistAppInfo.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		//System.out.println("getView at " + position);
		View view = null;
		ViewHolder holder = null;
		if (convertview == null || convertview.getTag() == null) {
			view = infater.inflate(R.layout.browse_app_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} 
		else{
			view = convertview ;
			holder = (ViewHolder) convertview.getTag() ;
		}
		RunningAppInfo appInfo = (RunningAppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.tvAppLabel.setText(appInfo.getAppLabel());
//		holder.tvPkgName.setText(appInfo.getPkgName());
//		holder.tvProcessId.setText(appInfo.getPid()+"") ;
//		holder.tvProcessName.setText(appInfo.getProcessName()) ;
		
		//����������1024b�ͼ�Ϊkb
//		if(appInfo.getUpFlow()>=1024)
			holder.tvAppUpFlow.setText(appInfo.getUpFlow()/1024+"KB");
//		else
//			holder.tvAppUpFlow.setText(appInfo.getUpFlow()+"bytes");
//		if(appInfo.getDownFlow()>=1024)
			holder.tvAppDownFlow.setText(appInfo.getDownFlow()/1024+"KB");
//		else
//			holder.tvAppDownFlow.setText(appInfo.getDownFlow()+"bytes");
		
		return view;
	}

	class ViewHolder {
		ImageView appIcon; //Ӧ��ͼ��
		TextView tvAppLabel; //Ӧ����
//		TextView tvPkgName; //Ӧ�ð���
//      TextView tvProcessId ; //Ӧ�ý���id
//		TextView tvProcessName ; //Ӧ�ý�����
		TextView tvAppUpFlow;//Ӧ���ϴ�����
		TextView tvAppDownFlow;//Ӧ����������
      
      
		public ViewHolder(View view) {
			this.appIcon = (ImageView) view.findViewById(R.id.fragment3_imgApp);
			this.tvAppLabel = (TextView) view.findViewById(R.id.fragment3_tvAppLabel);
//			this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);
//			this.tvProcessId = (TextView) view.findViewById(R.id.tvProcessId);
//			this.tvProcessName = (TextView) view.findViewById(R.id.tvProcessName);
			this.tvAppUpFlow=(TextView)view.findViewById(R.id.fragment3_tvuplabel);
			this.tvAppDownFlow=(TextView)view.findViewById(R.id.fragment3_tvdownlabel);
		}
	}
}