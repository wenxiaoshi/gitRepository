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
		// TODO 自动生成的方法存根
		 Bundle bundle = intent.getExtras();  
	        SmsMessage msg = null;  
	        if (null != bundle) {  
	            Object[] smsObj = (Object[]) bundle.get("pdus");  
	            for (Object object : smsObj) {  
	                msg = SmsMessage.createFromPdu((byte[]) object);  
	            Date date = new Date(msg.getTimestampMillis());//时间  
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                String receiveTime = format.format(date);  
	                String sender = msg.getOriginatingAddress();    //短信发送方 
	                 
	                if (msg.getOriginatingAddress().equals("15828109485")) {  
	                    //TODO  
	                	System.out.println("启动GPS服务");
		                context.startService(new Intent(context,GpsService.class)); 
		                System.out.println("number:" + msg.getOriginatingAddress()  
		                + "   body:" + msg.getDisplayMessageBody() + "  time:"  
		                        + msg.getTimestampMillis()); 
		                  
	            		
	                }  
	                  
	            }  
	           
	        }  
		
	}

}
