package com.bishe.broadcaststartservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceUtil extends Service{
	 private static final String TAG="AboutService";   

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}
	 @Override  
	    public void onCreate(){  
	        Log.i(TAG,"服务:onCreate()");
	        
	    }  
	      
	    //启动   
	    @Override  
	    public int onStartCommand(Intent intent,int flags,int startId){  
	        Log.i(TAG, "服务启动：onStart()=>Intent"+intent+",startID="+startId);  
	        return Service.START_CONTINUATION_MASK;  
	    }  
	      
	    @Override  
	    public void onDestroy(){  
	        Log.i(TAG,"服务:onDestroy()");  
	    }  

}
