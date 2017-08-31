package com.bishe.servicesavedata;

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
import android.telephony.CellInfo;
import android.util.Log;
import android.content.Context;
import android.content.Intent;









import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;





public class GpsService extends Service {
	
	 ArrayList<com.bishe.servicesavedata.CellInfo> cellIds = null;
	 private Gps gps=null;
	 private boolean threadDisable=false; 
	 private final static String TAG=GpsService.class.getSimpleName();
	 Intent intent =new Intent();
	 final Context mContext=this;
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
	    
	      //尝试在GPS service中保存数据，将位置信息保存在文件中
	      try {
			prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
				editor2=prefer.edit();
				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
      Editor edit=preferences.edit();
      editor2.putString("gpsinfo", "经度："+location.getLatitude()+"纬度："+location.getLongitude()+
				"海拔："+location.getAltitude()+"m\n时间："+new Date());
      editor2.commit();
      SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
  	String GPSInfo = gpsinfo.getString("gpsinfo", "");
      
     System.out.println("GPS service中执行数据保存至文件"+GPSInfo);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	     
	      //发送广播
	      System.out.println("GPS service发送广播");
	      Intent intent=new Intent();
	      intent.setAction("com.bishe.servicesavedata.GpsService");
	      intent.putExtra("lat", location==null?"0":location.getLatitude()+""); 
	      intent.putExtra("lon", location==null?"0":location.getLongitude()+""); 
	      intent.putExtra("alt", location==null?"0":location.getAltitude()+""); 
	      intent.putExtra("spe", location==null?"0":location.getSpeed()+""); 
	      intent.putExtra("bea", location==null?"0":location.getBearing()+""); 
	      intent.putExtra("time", new Date());
	      sendBroadcast(intent);
	      System.out.println("GPS service已经发送广播");
	     }
	 
	    }
	   }
	  }).start();
	 
	//  mContext.startService(new Intent(mContext,GpsService.class)); 
	  
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
