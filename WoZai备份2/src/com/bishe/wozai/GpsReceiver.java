package com.bishe.wozai;

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
    
   
	
		 float stringToFloat(String floatstr)
		   {
		     Float floatee;
		     floatee = Float.valueOf(floatstr);
		     return floatee.floatValue();
		   }
	@Override
	public void onReceive(Context context, Intent intent) {
		
		
		// TODO 自动生成的方法存根
		System.out.println("GPSReceiver收到GPS广播信息:");
		 Bundle bundle=intent.getExtras();      
		   String lon=intent.getStringExtra("lon");    
		   String lat=intent.getStringExtra("lat"); 
		   String alt=intent.getStringExtra("alt");
		   String spe=intent.getStringExtra("spe");
		   String bea=intent.getStringExtra("bea");
		   String time=intent.getStringExtra("time");
		  
		  float speed=stringToFloat(spe);
		   System.out.println("经度:"+lon +"  纬度："+lat  +"  海拔："+alt+"  速度："+spe+"  方位角："+bea+new Date());
		final  String message="经度:"+lon  +"  纬度："+lat  +"  海拔："+alt+"  速度："+spe+"  方位角："+bea+new Date();
		  
		  
		  
		  

		  if (speed>0&&speed<30) {
	
			  System.out.println("GpsReceiver收到经纬度信息，即将启动低速模式计时器：");
              context.startService(new Intent(context,SmsSender.class)); 
		 }
		  else if(speed>=30&&speed<60){
			  System.out.println("GpsReceiver收到经纬度信息，即将启动中速模式计时器：");
              context.startService(new Intent(context,SmsSender2.class)); 
		  }
		  else if(speed>=60){
			  System.out.println("GpsReceiver收到经纬度信息，即将启动高速模式计时器：");
              context.startService(new Intent(context,SmsSender3.class)); 
		  } 
		  else if (speed==0) {
			  System.out.println("GpsReceiver收到经纬度信息，即将启动静止模式计时器：");
              context.startService(new Intent(context,SmsSender4.class)); 
			
		}

	 	  
		
	}
	
	
	
}
