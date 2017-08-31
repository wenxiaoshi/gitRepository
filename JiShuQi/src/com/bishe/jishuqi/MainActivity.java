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
			System.out.println("计时器停止被触发");
		}
		 Handler = new Handler(){  
			  
	            @Override  
	            public void handleMessage(Message msg) {  
	                switch (msg.what) {  
	                case 1:  
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
                    } while (i>9);  
                      
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
//            Message message = Message.obtain(Handler, id);     
//            Handler.sendMessage(message);   
        }  
    
	}  	
}
//Timer timer;
//timer = new Timer(true);
//
//
//		//任务
//		TimerTask task;
//task = new TimerTask() {
//	public void run() {
//		Message msg = new Message();
//		msg.what = 1;
//		handler.sendMessage(msg);
//		System.out.println("timer向handler发送消息");
//
//	}
//
//};
//System.out.println("timer向handler发送消息完毕");
//if (a>=8) {
//	timer.cancel();
//	System.out.println("定时取消");
//}
//System.out.println("if定时取消处执行完毕");
//
////启动低速模式定时器 1分钟定时器
//timer.schedule(task, 0, 20 * 1000);
//System.out.println("定时器启动");	
//	
//	
//
////else {
////	//启动低速模式定时器 1分钟定时器
////	timer.schedule(task, 0, 20 * 1000);
////	System.out.println("定时器启动");
////	timer.cancel();
////	System.out.println("定时取消");
////}