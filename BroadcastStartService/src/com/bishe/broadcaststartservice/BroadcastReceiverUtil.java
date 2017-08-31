package com.bishe.broadcaststartservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverUtil extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("reveiver收到广播消息：");
		// TODO 自动生成的方法存根
		 //广播接收器（接收方）判断Action为“com.genwoxue.action.ABOUTSERVICE”则启动服务   
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())){  
            context.startService(new Intent(context,ServiceUtil.class));  
        }  

	}

}
