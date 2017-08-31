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
	//����ģʽ�µĶ��ŷ���ģ�飬ʱ������Ϊ1���ӣ�GPS����ÿ��1���ӷ���һ��
//	 private MyReceiver receiver=null; 
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
	 
	  SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
    final	String GPSInfo = gpsinfo.getString("gpsinfo", "");
    
 
    	
	
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {

					//todo something...
					System.out.println("hander��ִ������to do something,");
					System.out.println("���Ͷ��Ű���" + new Date());
					System.out.println("SmsSenderȡ���ļ��е�����" + GPSInfo);
					
					SmsManager smsManager;
					smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage("15388268798", null, GPSInfo,
							null, null);
					
				}

			}

		};
		final Timer timer = new Timer(true);
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
		
			timer.schedule(task, 0, 10 * 1000);
			System.out.println("��ʱ������");		
			timer.cancel();
			System.out.println("��ʱ��ȡ�������ŷ������");
		
	}
	
	
	 @Override
	 public void onDestroy() {
	 
	  super.onDestroy();
	 }
}
