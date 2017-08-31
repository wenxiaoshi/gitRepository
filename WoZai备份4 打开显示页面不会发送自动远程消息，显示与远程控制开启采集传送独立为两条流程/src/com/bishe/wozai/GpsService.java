package com.bishe.wozai;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;










import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;




public class GpsService extends Service {
	
	 ArrayList<CellInfo> cellIds = null;
	 private Gps gps=null;
	 private boolean threadDisable=false; 
	 private final static String TAG=GpsService.class.getSimpleName();
	 Intent intent =new Intent();
	 
	 SharedPreferences prefer;
	    Editor editor2;  
	 
	    
	 
	 @Override
	 public void onCreate() {
	  super.onCreate();
	  
	
	   
	  gps=new Gps(GpsService.this);
	  cellIds=UtilTool.init(GpsService.this);
	  
	  
	  
	   
	  new Thread(new Runnable(){
	   @Override
	   public void run() {
	    while (!threadDisable) { 
	     try {
	      Thread.sleep(1000);
	     } catch (InterruptedException e) {
	      e.printStackTrace();
	     }
	     
	     
	     startService(new Intent());
	     
	      
	     if(gps!=null){ //当结束服务时gps为空
	      //获取经纬度
	   Location location=gps.getLocation();
	      //如果gps无法获取经纬度，改用基站定位获取
	      if(location==null){
	    	  System.out.println("gps location null");
	       //2.根据基站信息获取经纬度
	       try {
	        location = UtilTool.callGear(GpsService.this, cellIds);
	       } catch (Exception e) {
	        location=null;
	        e.printStackTrace();
	       }
	       if(location==null){
	    	   System.out.println("cell location null");   
	      
	       }
	      }
	      
	     
	      //发送广播
	      Intent intent=new Intent();
	      intent.setAction("com.bishe.wozai.GpsService");
	      intent.putExtra("lat", location==null?"0":location.getLatitude()+""); 
	      intent.putExtra("lon", location==null?"0":location.getLongitude()+""); 
	      intent.putExtra("alt", location==null?"0":location.getAltitude()+""); 
	      intent.putExtra("spe", location==null?"0":location.getSpeed()+""); 
	      intent.putExtra("bea", location==null?"0":location.getBearing()+""); 
	      intent.putExtra("time", new Date());
	      sendBroadcast(intent);
	    
	     }
	 
	    }
	   }
	  }).start();
	
	  
	 }
	 
	 @Override
	 public void onDestroy() {
	  threadDisable=true;
	  if(cellIds!=null&&cellIds.size()>0){
	   cellIds=null;
	  }
	  if(gps!=null){
	   gps.closeLocation();
	   gps=null;
	  }
	  super.onDestroy();
	 }
	 
	 @Override
	 public IBinder onBind(Intent arg0) {
	  return null;
	 }
	
	 
	}
