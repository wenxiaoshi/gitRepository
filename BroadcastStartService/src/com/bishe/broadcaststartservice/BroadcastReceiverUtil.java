package com.bishe.broadcaststartservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverUtil extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("reveiver�յ��㲥��Ϣ��");
		// TODO �Զ����ɵķ������
		 //�㲥�����������շ����ж�ActionΪ��com.genwoxue.action.ABOUTSERVICE������������   
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())){  
            context.startService(new Intent(context,ServiceUtil.class));  
        }  

	}

}
