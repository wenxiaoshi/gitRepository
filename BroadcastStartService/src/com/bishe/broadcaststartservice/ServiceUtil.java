package com.bishe.broadcaststartservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceUtil extends Service{
	 private static final String TAG="AboutService";   

	@Override
	public IBinder onBind(Intent intent) {
		// TODO �Զ����ɵķ������
		return null;
	}
	 @Override  
	    public void onCreate(){  
	        Log.i(TAG,"����:onCreate()");
	        
	    }  
	      
	    //����   
	    @Override  
	    public int onStartCommand(Intent intent,int flags,int startId){  
	        Log.i(TAG, "����������onStart()=>Intent"+intent+",startID="+startId);  
	        return Service.START_CONTINUATION_MASK;  
	    }  
	      
	    @Override  
	    public void onDestroy(){  
	        Log.i(TAG,"����:onDestroy()");  
	    }  

}
