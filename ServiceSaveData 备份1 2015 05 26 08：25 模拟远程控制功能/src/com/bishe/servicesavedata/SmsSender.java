package com.bishe.servicesavedata;

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

public class SmsSender extends Service{
	//����ģʽ�µĶ��ŷ���ģ�飬ʱ������Ϊ1���ӣ�GPS����ÿ��1���ӷ���һ��
	
	 SharedPreferences prefer;
	    Editor editor2;  
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO �Զ����ɵķ������
		return null;
	}
	
	
	@Override
	 public void onCreate() {
	  super.onCreate();
	  System.out.println("���ŷ���ģ������");
	  SharedPreferences gpsinfo = getSharedPreferences( "GpsInfomation", 0);
    final	String GPSInfo = gpsinfo.getString("gpsinfo", "");
    System.out.println("��������ȡ��");	
    	
	final Handler handler  = new Handler(){
		    public void handleMessage(Message msg) {
		        super.handleMessage(msg);
		        if(msg.what == 1){
		        	 
		        		//todo something...
		 	        	 System.out.println("hander��ִ������to do something,");
		 	        	System.out.println("���Ͷ��Ű���"+new Date());
		 	        	System.out.println("SmsSenderȡ���ļ��е�����"+GPSInfo);
//		 				  SmsManager smsManager;
//		 					 smsManager = SmsManager.getDefault();
//		 					smsManager.sendTextMessage("18200251737", null,GPSInfo, null, 
//		 			                null);
		        		  }

		        	}
		        
		};
		 
		 
		Timer timer = new Timer(true);
		 
		//����
		TimerTask task = new TimerTask() {
		  public void run() {
		    Message msg = new Message();
		    msg.what = 1;
		    handler.sendMessage(msg);
		    System.out.println("timer��handler������Ϣ");
		  }
		};
		 
		//��������ģʽ��ʱ�� 1���Ӷ�ʱ��
		timer.schedule(task, 0, 20*1000);
		 System.out.println("��ʱ������");

	 }

}
