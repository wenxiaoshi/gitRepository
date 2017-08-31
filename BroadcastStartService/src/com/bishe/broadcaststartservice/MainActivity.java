package com.bishe.broadcaststartservice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.app.Activity;  
import android.content.Intent;  
import android.content.IntentFilter;  
  
public class MainActivity extends Activity {  
  
    private BroadcastReceiverUtil util=null;  
    private Button btnSend=null;  
      
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        btnSend=(Button)super.findViewById(R.id.send);  
        btnSend.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View v){  
                //���͹㲥����ActionΪ��com.genwoxue.action.ABOUTSERVICE��   
                Intent intent=new Intent("android.provider.Telephony.SMS_RECEIVED");  
                MainActivity.this.sendBroadcast(intent);  
                  
                  
                //ʵ�����㲥��������ֻ������ActionΪ"com.genwoxue.action.ABOUTSERVICE"��   
                IntentFilter filter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");  
                //ʵ�����㲥�����������շ���   
                util=new BroadcastReceiverUtil();  
                //ע��BroadcastReceiver������Ϊ�������������   
                MainActivity.this.registerReceiver(util, filter);  
            }  
        });  
    }  
      
    @Override  
    protected void onStop(){  
        //ֹͣ�㲥   
        super.unregisterReceiver(util);  
        super.onStop();  
          
        //ֹͣ����   
        Intent intent=new Intent(MainActivity.this,ServiceUtil.class);  
        MainActivity.this.stopService(intent);  
    }  
  
}  
