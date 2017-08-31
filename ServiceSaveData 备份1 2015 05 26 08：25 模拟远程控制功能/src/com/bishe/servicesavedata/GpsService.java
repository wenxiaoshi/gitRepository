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
	     
	      
	     if(gps!=null){ //����������ʱgpsΪ��
	      //��ȡ��γ��
	   Location location=gps.getLocation();
	      //���gps�޷���ȡ��γ�ȣ����û�վ��λ��ȡ
	      if(location==null){
	    	  System.out.println("gps location null");
	       //2.���ݻ�վ��Ϣ��ȡ��γ��
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
	    
	      //������GPS service�б������ݣ���λ����Ϣ�������ļ���
	      try {
			prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
				editor2=prefer.edit();
				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
      Editor edit=preferences.edit();
      editor2.putString("gpsinfo", "���ȣ�"+location.getLatitude()+"γ�ȣ�"+location.getLongitude()+
				"���Σ�"+location.getAltitude()+"m\nʱ�䣺"+new Date());
      editor2.commit();
      SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
  	String GPSInfo = gpsinfo.getString("gpsinfo", "");
      
     System.out.println("GPS service��ִ�����ݱ������ļ�"+GPSInfo);
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	     
	      //���͹㲥
	      System.out.println("GPS service���͹㲥");
	      Intent intent=new Intent();
	      intent.setAction("com.bishe.servicesavedata.GpsService");
	      intent.putExtra("lat", location==null?"0":location.getLatitude()+""); 
	      intent.putExtra("lon", location==null?"0":location.getLongitude()+""); 
	      intent.putExtra("alt", location==null?"0":location.getAltitude()+""); 
	      intent.putExtra("spe", location==null?"0":location.getSpeed()+""); 
	      intent.putExtra("bea", location==null?"0":location.getBearing()+""); 
	      intent.putExtra("time", new Date());
	      sendBroadcast(intent);
	      System.out.println("GPS service�Ѿ����͹㲥");
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
