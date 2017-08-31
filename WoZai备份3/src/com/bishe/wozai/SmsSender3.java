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

public class SmsSender3 extends Service{
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
final	String GPSInfo = gpsinfo.getString("gpsinfo", "为空为空为空");
	
	
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
	 					smsManager.sendTextMessage("18200251737", null,"文实"+GPSInfo+new Date(), null, 
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
	 
	//启动定时器
	timer.schedule(task, 0, 5*1000);
	 System.out.println("定时器启动");

 }


}
