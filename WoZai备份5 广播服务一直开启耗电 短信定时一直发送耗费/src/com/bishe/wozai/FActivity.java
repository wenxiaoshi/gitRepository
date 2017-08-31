package com.bishe.wozai;
//这是第二个页面
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;














import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class FActivity extends Activity{
	
	
	 private EditText editText = null;
	
	 private final static String TAG=FActivity.class.getSimpleName();
	private Button bt3;
	private Context mContext;
	private Button bt4;
	private Context mContext2;
	private EditText editText1;
	private EditText editText_phone;
	private LocationManager lm;
	private static final String FAG="FActivity"; 
	 PendingIntent paIntent;
	 SmsManager smsManager;
	 SharedPreferences prefer;
	    Editor editor2;  
	    //字符型数据转化为浮点型函数
	    float stringToFloat(String floatstr)
		   {
		     Float floatee;
		     floatee = Float.valueOf(floatstr);
		     return floatee.floatValue();
		   }
	   
	 
		@Override
	       public void onCreate(Bundle savedInstanceState) {
	           super.onCreate(savedInstanceState);
	           setContentView(R.layout.factivity);
	           
	           bt3=(Button) findViewById(R.id.button_tellmylocation);
	           final EditText txtPhoneNo = (EditText) findViewById(R.id.editText_pnone);
	           final EditText txtMessage = (EditText) findViewById(R.id.editText);
	   		   final Context mContext=this;
	   		 paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0); 
	           smsManager = SmsManager.getDefault();
	   		   bt3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					// TODO 自动生成的方法存根
					
	   				String phoneNo =txtPhoneNo.getText().toString();
		             String message = txtMessage.getText().toString();
	   				
	                if (phoneNo.length()==11&&message.length()>0){                
	                	Log.v(FAG, "will begin sendSMS");
	                	Toast.makeText(FActivity.this, 
		                        "即将开始发送", 
		                        Toast.LENGTH_LONG).show();
	                	//自动拆分短信
	                	ArrayList<String> texts = smsManager.divideMessage(message);
	                	//迭代发送
	                	for(String text:texts){
	                	
	                	smsManager.sendTextMessage(phoneNo, null, message , paIntent, 
		                        null); 
	                	Toast.makeText(FActivity.this, 
		                        "发送成功", 
		                        Toast.LENGTH_LONG).show();
	                }}
	                else
	                    Toast.makeText(FActivity.this, 
	                        "请重新输入", 
	                        Toast.LENGTH_LONG).show();
				}
			});

	           editText=(EditText)findViewById(R.id.editText);
	           lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	           
	           //判断GPS是否正常启动
	           if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	               Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_LONG).show();
	               //返回开启GPS导航设置界面，参考用作短信跳转页面
	               Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
	               startActivityForResult(intent,0); 
	               return;
	           }
	           
	           //为获取地理位置信息时设置查询条件
	           String bestProvider = lm.getBestProvider(getCriteria(), true);
	           //获取位置信息
	           //如果不设置查询要求，getLastKnownLocation方法传入的参数为LocationManager.GPS_PROVIDER
	           Location location= lm.getLastKnownLocation(bestProvider);    
	           updateView(location);
	           //监听状态
	           lm.addGpsStatusListener(listener);
	           //绑定监听，有4个参数    
	           //参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
	           //参数2，位置信息更新周期，单位毫秒    
	           //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息    
	           //参数4，监听    
	           //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新   
	           
	           // 1秒更新一次，或最小位移变化超过1米更新一次；
	           //注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
	           lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
	       }
	       
	     

	 	//位置监听
	       private LocationListener locationListener=new LocationListener() {
	           
	           /**
	            * 位置信息变化时触发
	            */
	           public void onLocationChanged(Location location) {
	               updateView(location);
	               Log.i(FAG, "时间："+location.getTime()); 
	               Log.i(FAG, "经度："+location.getLongitude()); 
	               Log.i(FAG, "纬度："+location.getLatitude()); 
	               Log.i(FAG, "海拔："+location.getAltitude()); 
	           }
	           
	           /**
	            * GPS状态变化时触发
	            */
	           public void onStatusChanged(String provider, int status, Bundle extras) {
	               switch (status) {
	               //GPS状态为可见时
	               case LocationProvider.AVAILABLE:
	                   Log.i(FAG, "当前GPS状态为可见状态");
	                   break;
	               //GPS状态为服务区外时
	               case LocationProvider.OUT_OF_SERVICE:
	                   Log.i(FAG, "当前GPS状态为服务区外状态");
	                   break;
	               //GPS状态为暂停服务时
	               case LocationProvider.TEMPORARILY_UNAVAILABLE:
	                   Log.i(FAG, "当前GPS状态为暂停服务状态");
	                   break;
	               }
	           }
	       
	           /**
	            * GPS开启时触发
	            */
	           public void onProviderEnabled(String provider) {
	               Location location=lm.getLastKnownLocation(provider);
	               updateView(location);
	           }
	       
	           /**
	            * GPS禁用时触发
	            */
	           public void onProviderDisabled(String provider) {
	               updateView(null);
	           }

	       
	       };
	       
	       //状态监听
	       GpsStatus.Listener listener = new GpsStatus.Listener() {
	           public void onGpsStatusChanged(int event) {
	               switch (event) {
	               //第一次定位
	               case GpsStatus.GPS_EVENT_FIRST_FIX:
	                   Log.i(FAG, "第一次定位");
	                   break;
	               //卫星状态改变
	               case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
	                   Log.i(FAG, "卫星状态改变");
	                   //获取当前状态
	                   GpsStatus gpsStatus=lm.getGpsStatus(null);
	                   //获取卫星颗数的默认最大值
	                   int maxSatellites = gpsStatus.getMaxSatellites();
	                   //创建一个迭代器保存所有卫星 
	                   Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
	                   int count = 0;     
	                   while (iters.hasNext() && count <= maxSatellites) {     
	                       GpsSatellite s = iters.next();     
	                       count++;     
	                   }   
	                   System.out.println("搜索到："+count+"颗卫星");
	                   break;
	               //定位启动
	               case GpsStatus.GPS_EVENT_STARTED:
	                   Log.i(FAG, "定位启动");
	                   break;
	               //定位结束
	               case GpsStatus.GPS_EVENT_STOPPED:
	                   Log.i(FAG, "定位结束");
	                   break;
	               }
	           };
	       };
	       
	       /**
	        * 实时更新文本内容
	        * 
	        * @param location
	        */
	       
	       private void updateView(Location location){
	    
	           if(location!=null){   

				   //将字符型速度转化为浮点型数据
				  
				   //将速度由海里每小时转为米每秒
				   double speed2=0.514*location.getSpeed();
				   final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式	
	               editText.setText("经度：");
	               editText.append(String.valueOf(location.getLongitude()));
	               editText.append("\n纬度：");
	               editText.append(String.valueOf(location.getLatitude()));
	               editText.append("\n海拔：");
	               editText.append(String.valueOf(location.getAltitude())+"米");
//	               editText.append("\n速度：");
//	               editText.append(speed2+"m/s");
//	               editText.append("\n方位角：");
//	               editText.append(String.valueOf(location.getBearing())+"°");
	               editText.append("\n时间：");
	               editText.append(df.format(new Date()));
	               
	           }else{
	               //清空EditText对象
	               editText.getEditableText().clear();
	           }
	       }
	      
	      
		/**
	        * 返回查询条件
	        * @return
	   */
	       private Criteria getCriteria(){
	           Criteria criteria=new Criteria();
	           //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细 
	           criteria.setAccuracy(Criteria.ACCURACY_FINE);    
	           //设置是否要求速度
	           criteria.setSpeedRequired(true);
	           // 设置是否允许运营商收费  
	           criteria.setCostAllowed(true);
	           //设置是否需要方位信息
	           criteria.setBearingRequired(true);
	           //设置是否需要海拔信息
	           criteria.setAltitudeRequired(true);
	           // 设置对电源的需求  
	           criteria.setPowerRequirement(Criteria.POWER_LOW);
	           return criteria;
	          
			   

	 	}
	      
   			 
   		   
 	}	  
	
         
         
  

	 		 
 		


