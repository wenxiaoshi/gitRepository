package com.bishe.jishuqi;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
 int i=1;
 private Timer Timer = null;  
 private TimerTask TimerTask = null;  

 private Handler Handler = null;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(i<10){
			startTimer();
		}else{
			stopTimer();
			System.out.println("��ʱ��ֹͣ������");
		}
		 Handler = new Handler(){  
			  
	            @Override  
	            public void handleMessage(Message msg) {  
	                switch (msg.what) {  
	                case 1:  
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
                    } while (i>9);  
                      
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
//            Message message = Message.obtain(Handler, id);     
//            Handler.sendMessage(message);   
        }  
    
	}  	
}
//Timer timer;
//timer = new Timer(true);
//
//
//		//����
//		TimerTask task;
//task = new TimerTask() {
//	public void run() {
//		Message msg = new Message();
//		msg.what = 1;
//		handler.sendMessage(msg);
//		System.out.println("timer��handler������Ϣ");
//
//	}
//
//};
//System.out.println("timer��handler������Ϣ���");
//if (a>=8) {
//	timer.cancel();
//	System.out.println("��ʱȡ��");
//}
//System.out.println("if��ʱȡ����ִ�����");
//
////��������ģʽ��ʱ�� 1���Ӷ�ʱ��
//timer.schedule(task, 0, 20 * 1000);
//System.out.println("��ʱ������");	
//	
//	
//
////else {
////	//��������ģʽ��ʱ�� 1���Ӷ�ʱ��
////	timer.schedule(task, 0, 20 * 1000);
////	System.out.println("��ʱ������");
////	timer.cancel();
////	System.out.println("��ʱȡ��");
////}