package com.bishe.wozai;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.R.array;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;

public class SmsSender extends Service{
	//低速模式下的短信发送模块，时间间隔定为1分钟，GPS数据每隔1分钟发送一次
//	 private MyReceiver receiver=null; 
	 SharedPreferences prefer;
	    Editor editor2;  
	    int i=1;
		 private Timer Timer = null;  
		 private TimerTask TimerTask = null;  

		 private Handler Handler = null; 
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
    System.out.println("短信服务开启");
    if(i<5){
		startTimer();
	}else{
		stopTimer();
		System.out.println("计时器停止被触发");
	}
	 Handler = new Handler(){  
		  
            public void handleMessage(Message msg) {  
                switch (msg.what) {  
                case 1:  
                ////todo something...
                	System.out.println("hander内执行任务，to do something,");
                	System.out.println("发送短信啊啊" + new Date());
                	System.out.println("SmsSender取出文件中的数据" + GPSInfo);
                	//
                	//SmsManager smsManager;
                	//smsManager = SmsManager.getDefault();
                	//smsManager.sendTextMessage("15388268798", null, GPSInfo,
//                			null, null);
                	System.out.println("hander内执行任务");
                	System.out.println("i的当前值是："+i);
                    break;  
                default:  
                    break;  
                }  
            }  
        };  

 }
private void stopTimer() {
	// TODO 自动生成的方法存根
	System.out.println("计时器停止");
    
    if (Timer != null) {  
        Timer.cancel();  
        Timer = null;  
    }  

    if (TimerTask != null) {  
        TimerTask.cancel();  
        TimerTask = null;  
    }     

    i = 0;  
    System.out.println("计时器停止，i的当前值是："+i);

}
private void startTimer() {
	// TODO 自动生成的方法存根
	System.out.println("计时器工作开启");
    if (Timer == null) {  
        Timer = new Timer();  
    }  

    if (TimerTask == null) {  
        TimerTask = new TimerTask() {  
            @Override  
            public void run() {  
                sendMessage();  
                  
                do {  
                    try {  
                          
                        Thread.sleep(1000);  
                    } catch (InterruptedException e) {  
                    }     
                } while (i>4);  
                  
                i ++;    
            }

        };  
    }  

    if(Timer != null && TimerTask != null )  
        Timer.schedule(TimerTask, 0, 5*1000);  

}
private void sendMessage() {
	// TODO 自动生成的方法存根
	  
    if (Handler != null) {  
		Message msg = new Message();
		msg.what = 1;
		Handler.sendMessage(msg);
		System.out.println("timer向handler发送消息");

    }   	
	}
	
	 @Override
	 public void onDestroy() {
	 
	  super.onDestroy();
	 }
}
////todo something...
//System.out.println("hander内执行任务，to do something,");
//System.out.println("发送短信啊啊" + new Date());
//System.out.println("SmsSender取出文件中的数据" + GPSInfo);
//
//SmsManager smsManager;
//smsManager = SmsManager.getDefault();
//smsManager.sendTextMessage("15388268798", null, GPSInfo,
//		null, null);