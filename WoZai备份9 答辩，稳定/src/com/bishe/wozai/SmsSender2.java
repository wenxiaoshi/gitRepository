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

public class SmsSender2 extends Service{
	 SharedPreferences prefer;
	    Editor editor2;  
	    int i=1;
		 private Timer Timer = null;  
		 private TimerTask TimerTask = null;  

		 private Handler Handler = null; 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO �Զ����ɵķ������
		return null;
	}
	
	
	@Override
	 public void onCreate() {
	  super.onCreate();
	 
	  SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
 final	String GPSInfo = gpsinfo.getString("gpsinfo", "");
 System.out.println("���ŷ�����");
 if(i<5){
		startTimer();
	}else{
		stopTimer();
		System.out.println("��ʱ��ֹͣ������");
	}
	 Handler = new Handler(){  
		  
         public void handleMessage(Message msg) {  
             switch (msg.what) {  
             case 1:  
             ////todo something...
             	System.out.println("hander��ִ������to do something,");
             	System.out.println("���Ͷ��Ű���" + new Date());
             	System.out.println("SmsSenderȡ���ļ��е�����" + GPSInfo);
             	//
             	//SmsManager smsManager;
             	//smsManager = SmsManager.getDefault();
             	//smsManager.sendTextMessage("15388268798", null, GPSInfo,
//             			null, null);
             	System.out.println("hander��ִ������");
             	System.out.println("i�ĵ�ǰֵ�ǣ�"+i);
                 break;  
             default:  
                 break;  
             }  
         }  
     };  

}
private void stopTimer() {
	// TODO �Զ����ɵķ������
	System.out.println("��ʱ��ֹͣ");
 
 if (Timer != null) {  
     Timer.cancel();  
     Timer = null;  
 }  

 if (TimerTask != null) {  
     TimerTask.cancel();  
     TimerTask = null;  
 }     

 i = 0;  
 System.out.println("��ʱ��ֹͣ��i�ĵ�ǰֵ�ǣ�"+i);

}
private void startTimer() {
	// TODO �Զ����ɵķ������
	System.out.println("��ʱ����������");
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
	// TODO �Զ����ɵķ������
	  
 if (Handler != null) {  
		Message msg = new Message();
		msg.what = 1;
		Handler.sendMessage(msg);
		System.out.println("timer��handler������Ϣ");

 }   	
	}
	
	 @Override
	 public void onDestroy() {
	 
	  super.onDestroy();
	 }
}
