package com.bishe.wozai;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class SActivity extends Activity{
	private BroadcastReceiver receiver=null; 
	
	 SharedPreferences prefer;
	    Editor editor2;  
	    //�ַ�������ת��Ϊ�����ͺ���
	    float stringToFloat(String floatstr)
		   {
		     Float floatee;
		     floatee = Float.valueOf(floatstr);
		     return floatee.floatValue();
		   }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		
		
		
		 //��ȡ�㲥����
		 class MyReceiver extends BroadcastReceiver{
	
		  
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO �Զ����ɵķ������
			Bundle bundle=intent.getExtras();      
			  System.out.println("SActivity�յ�GPS�㲥��Ϣ:"); 
			final   String lon=intent.getStringExtra("lon");    
			   String lat=intent.getStringExtra("lat"); 
			   String alt=intent.getStringExtra("alt");
			   String spe=intent.getStringExtra("spe");
			   String bea=intent.getStringExtra("bea");
			   String time=intent.getStringExtra("time");
			   //���ַ����ٶ�ת��Ϊ����������
			   float speed=stringToFloat(spe);
			   //���ٶ��ɺ���ÿСʱתΪ��ÿ��
			   double speed2=0.514*speed;
			   
			   prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
				editor2=prefer.edit();
				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
	           Editor edit=preferences.edit();
	           final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	           
	           editor2.putString("gpsinfo", "���ȣ�"+lon+"\nγ�ȣ�"+lat+"\n���Σ�"+alt+"m\nʱ�䣺"+df.format(new Date()));
	           
	           editor2.commit();
	           
		}
		 }
		  
		 
		
		
		
		
	}
	@Override
	 protected void onDestroy() {
	  //ע������
	  unregisterReceiver(receiver);
	  //��������������÷���һֱ���о�ע���˾�
	  stopService(new Intent(this, GpsService.class));
	  super.onDestroy();
	 }
}
