package com.bishe.wozai;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.SmsManager;

public class SmsSender4 extends Service{
	//静止模式下的短信发送模块，每隔5分钟发送一次短信
	 SharedPreferences prefer;
	    Editor editor2;  
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	
	@Override
	 public void onCreate() {
	  super.onCreate();
	 
	  SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
    final	String GPSInfo = gpsinfo.getString("gpsinfo", "");
    	
    	
	final Handler handler  = new Handler(){
		    public void handleMessage(Message msg) {
		        super.handleMessage(msg);
		        if(msg.what == 1){
		        	 
		        		//todo something...
		 	        	 System.out.println("hander内执行任务，to do something,");
		 	        	System.out.println("发送短信啊啊"+new Date());
		 	        	System.out.println("SmsSender取出文件中的数据"+GPSInfo);
		 				  SmsManager smsManager;
		 					 smsManager = SmsManager.getDefault();
		 					smsManager.sendTextMessage("15828109485", null,GPSInfo, null, 
		 			                null);
		        		  }

		        	}
		        
		};
		 
		 
		Timer timer = new Timer(true);
		 
		//任务
		TimerTask task = new TimerTask() {
		  public void run() {
		    Message msg = new Message();
		    msg.what = 1;
		    handler.sendMessage(msg);
		    System.out.println("timer向handler发送消息");
		  }
		};
		 
		//启动定时器 5分钟定时器
		timer.schedule(task, 0, 60*1000);
		 System.out.println("定时器启动");

	 }

}

