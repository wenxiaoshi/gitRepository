package com.bishe.servicesavedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.gsm.SmsManager;
import android.view.View.OnCreateContextMenuListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
public class GpsReceiver extends BroadcastReceiver{
	
	 SharedPreferences prefer;
	    Editor editor2;  
    
   
	// ��������ת������ ���ַ�����ת��Ϊ������
		 float stringToFloat(String floatstr)
		   {
		     Float floatee;
		     floatee = Float.valueOf(floatstr);
		     return floatee.floatValue();
		   }
		 
	@Override
	public void onReceive(Context context, Intent intent) {
		
		
		// TODO �Զ����ɵķ������
		System.out.println("GPSReceiver�յ�GPS�㲥��Ϣ:");
		 Bundle bundle=intent.getExtras();      
		   String lon=intent.getStringExtra("lon");    
		   String lat=intent.getStringExtra("lat"); 
		   String alt=intent.getStringExtra("alt");
		   String spe=intent.getStringExtra("spe");
		   String bea=intent.getStringExtra("bea");
		   String time=intent.getStringExtra("time");
		  
		  float speed=stringToFloat(spe);
		   System.out.println("����:"+lon +"  γ�ȣ�"+lat  +"  ���Σ�"+alt+"  �ٶȣ�"+spe+"  ��λ�ǣ�"+bea+new Date());
		final  String message="����:"+lon  +"  γ�ȣ�"+lat  +"  ���Σ�"+alt+"  �ٶȣ�"+spe+"  ��λ�ǣ�"+bea+new Date();
		  
		
	
			  System.out.println("GpsReceiver�յ���γ����Ϣ��������������ģʽ��ʱ����");
              context.startService(new Intent(context,SmsSender.class)); 
		 
		 

	 	  
		
	}
	
	
	
}
