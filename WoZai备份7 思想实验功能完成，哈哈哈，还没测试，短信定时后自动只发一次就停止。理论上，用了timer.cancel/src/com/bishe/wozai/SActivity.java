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
	    //字符型数据转化为浮点型函数
	    float stringToFloat(String floatstr)
		   {
		     Float floatee;
		     floatee = Float.valueOf(floatstr);
		     return floatee.floatValue();
		   }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		
		
		
		 //获取广播数据
		 class MyReceiver extends BroadcastReceiver{
	
		  
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			Bundle bundle=intent.getExtras();      
			  System.out.println("SActivity收到GPS广播信息:"); 
			final   String lon=intent.getStringExtra("lon");    
			   String lat=intent.getStringExtra("lat"); 
			   String alt=intent.getStringExtra("alt");
			   String spe=intent.getStringExtra("spe");
			   String bea=intent.getStringExtra("bea");
			   String time=intent.getStringExtra("time");
			   //将字符型速度转化为浮点型数据
			   float speed=stringToFloat(spe);
			   //将速度由海里每小时转为米每秒
			   double speed2=0.514*speed;
			   
			   prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
				editor2=prefer.edit();
				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
	           Editor edit=preferences.edit();
	           final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	           
	           editor2.putString("gpsinfo", "经度："+lon+"\n纬度："+lat+"\n海拔："+alt+"m\n时间："+df.format(new Date()));
	           
	           editor2.commit();
	           
		}
		 }
		  
		 
		
		
		
		
	}
	@Override
	 protected void onDestroy() {
	  //注销服务
	  unregisterReceiver(receiver);
	  //结束服务，如果想让服务一直运行就注销此句
	  stopService(new Intent(this, GpsService.class));
	  super.onDestroy();
	 }
}
