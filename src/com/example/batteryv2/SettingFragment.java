package com.example.batteryv2;

import com.example.ClearMemory.ClearMemoryMethod;
import com.example.ModeUI.RoundProgressBar;
import com.example.SavePowerMode.SavePowerModeActivity;
import com.example.SetCpuMode.SetCpuModeActivity;
import com.example.batteryv2.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by admin on 13-11-23.
 */
public class SettingFragment extends Fragment {

	// ��Բ
	private int progress;
	private RoundProgressBar mRoundProgressBar1;

	private LinearLayout cpuModeSetBtn;
	private LinearLayout cpuInfoBtn;
	private LinearLayout savePButton;
	private Button clearButton;
	
	
	

	private View mView;

	// ��ȡ��Activity
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment4, null);
		mRoundProgressBar1 = (RoundProgressBar) mView.findViewById(R.id.roundProgressBar1);
		cpuModeSetBtn = (LinearLayout) mView.findViewById(R.id.sel_mode_liner);
		clearButton = (Button) mView.findViewById(R.id.cle_mem_btn);
		cpuInfoBtn = (LinearLayout) mView.findViewById(R.id.cpu_info_liner);
		savePButton = (LinearLayout) mView.findViewById(R.id.sel_low_power_mode_liner);

		// Fragment ����������Activity���ڣ���������ʱ�򣬱����ȡ��getActivity()����

		cpuModeSetBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SetCpuModeActivity.class);
				startActivity(intent);

			}
		});
		
		
		//ʡ��ģʽ
		savePButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SavePowerModeActivity.class);
				startActivity(intent);
				
			}
		});
		
		/**
		 * ���� CPU���� ��Ϣ��ѯ ��ť����
		 */
		
		cpuInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				progress = 0;
				new ClearMemoryMethod(context).clearMemory();
				new Thread(new Runnable() {
					@Override
					public void run() {

						while (progress <= 100) {
							progress += 3;

							// System.out.println(progress);

							mRoundProgressBar1.setProgress(progress);

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// ���߳��ڣ����Toast�����������Looper������������뵽�߳���
						Looper.prepare();
						Toast toast = Toast.makeText(getActivity(), "�������",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.TOP, 0, 220);
						toast.show();
						Looper.loop();
					}
				}).start();

			}
		});
		return mView;
	}

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
