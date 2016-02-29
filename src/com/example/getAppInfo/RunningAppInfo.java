package com.example.getAppInfo;



//Model�� �������洢Ӧ�ó�����Ϣ
import android.graphics.drawable.Drawable;



//Model�� �������洢Ӧ�ó�����Ϣ
public class RunningAppInfo {

	private String appLabel;    //Ӧ�ó����ǩ
	private Drawable appIcon ;  //Ӧ�ó���ͼ��
	private String pkgName ;    //Ӧ�ó�������Ӧ�İ���
	
	private int pid ;  //��Ӧ�ó������ڵĽ��̺�
	private String processName ;  // ��Ӧ�ó������ڵĽ�����
	private long upFlow;//��Ӧ�ó����ϴ���������
	private long downFlow;//��Ӧ�ó������ص�������
	public RunningAppInfo(){}
	
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}
	public Drawable getAppIcon() {

			return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
//		if(appIcon.getIntrinsicHeight()>100||appIcon.getIntrinsicWidth()>100)
//		{
//			Log.i("sizechanged", "!!!!!!!");
//			int width=appIcon.getIntrinsicWidth();
//			int height=appIcon.getIntrinsicHeight();
//			System.out.println("w"+width+"h"+height);
//			//ȡdrawable����ɫ��ʽ
//			Bitmap.Config config=appIcon.getOpacity()!=PixelFormat.OPAQUE? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
//			//������Ӧbitmap
//			Bitmap oldBitmap=Bitmap.createBitmap(width, height, config);
//			//������Ӧbitmap�Ļ���
//			Canvas canvas=new Canvas(oldBitmap);
//			appIcon.setBounds(0, 0, width, height);
//			appIcon.draw(canvas);//��drawable���ݻ���������
//			//�������ű���
//			Matrix matrix=new Matrix();
//			float scaleWidth=(float) (50.0/width);
//			float scaleHeight=(float) (50.0/height);
//			System.out.println("sw"+scaleWidth+"sh"+scaleHeight);
//			matrix.postScale(scaleWidth, scaleHeight);
//			
//			Bitmap newBitmap=Bitmap.createBitmap(oldBitmap,0,0,width,height,matrix,true);
//			this.appIcon=new BitmapDrawable(newBitmap);
//		}
//		else
			this.appIcon = appIcon;
	}
	public String getPkgName(){
		return pkgName ;
	}
	public void setPkgName(String pkgName){
		this.pkgName=pkgName ;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public long getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(long upFlow) {
		if(upFlow==-1)
			upFlow=0;
		this.upFlow = upFlow;
	}

	public long getDownFlow() {
		return downFlow;
	}
	
	public void setDownFlow(long downFlow) {
		if(downFlow==-1)
			downFlow=0;
		this.downFlow = downFlow;
	}
	//ͳ��������Ϣ
	public long getStatistic() {
		return upFlow+downFlow;
	}

	
	
}

