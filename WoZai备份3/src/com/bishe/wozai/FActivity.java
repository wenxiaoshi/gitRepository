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
	 private MyReceiver receiver=null; 
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
	           
	   
	           editText=(EditText)findViewById(R.id.editText);
	           
	          
	           
			   
//	 		  //判断GPS是否可用
//	 		  Log.i(TAG, UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))+"");
//	 		  if(!UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))){
//	 		   Toast.makeText(FActivity.this, "GSP当前已禁用，请在您的系统设置屏幕启动。", Toast.LENGTH_LONG).show();
//	 		   Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
//	 		   startActivity(callGPSSettingIntent);
//	 		            return;
//	 		  }   
	 		   
	 		  //启动服务
	 		  startService(new Intent(this, GpsService.class));
	 		
	 		   
	 		  //注册广播
	 		  receiver=new MyReceiver();
	 		  IntentFilter filter=new IntentFilter();
	 		  filter.addAction("com.bishe.wozai.GpsService");
	 		  registerReceiver(receiver, filter);
	 		  
	 		  
	 		//短信发送代码如下
	           paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0); 
	           smsManager = SmsManager.getDefault();
	           
	           bt3=(Button) findViewById(R.id.button_tellmylocation);
	           final EditText txtPhoneNo = (EditText) findViewById(R.id.editText_pnone);
	           final EditText txtMessage = (EditText) findViewById(R.id.editText);
	   		   final Context mContext=this;

	   			 
	   		   //点击告知对方按钮时实现短信发送，GPS数据自动嵌套在短信内容中
	           bt3.setOnClickListener(new MyClickListener() {
	   			@SuppressWarnings("deprecation")
				@Override
	   			public void onClick(View v) {
	   				SharedPreferences gpsinfo = getSharedPreferences( "GpsInfo", 0);
	            	String GPSInfo = gpsinfo.getString("gpsinfo", "");
	   				String phoneNo =txtPhoneNo.getText().toString();
		             String message = txtMessage.getText().toString();
	   				
	                if (phoneNo.length()==11&&GPSInfo.length()>0){                
	                	Log.v(FAG, "will begin sendSMS");
	                	Toast.makeText(FActivity.this, 
		                        "即将开始发送", 
		                        Toast.LENGTH_LONG).show();
	                	//自动拆分短信
	                	ArrayList<String> texts = smsManager.divideMessage(GPSInfo);
	                	//迭代发送
	                	for(String text:texts){
	                	
	                	smsManager.sendTextMessage(phoneNo, null, GPSInfo , paIntent, 
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
	 	}
	 		  
	 		 //获取广播数据
	 		 private class MyReceiver extends BroadcastReceiver{
	 		  @Override
	 		  public void onReceive(Context context, Intent intent) {
	 		   Bundle bundle=intent.getExtras();      
	 		  System.out.println("FActivity收到GPS广播信息:"); 
			final   String lon=intent.getStringExtra("lon");    
			   String lat=intent.getStringExtra("lat"); 
			   String alt=intent.getStringExtra("alt");
			   String spe=intent.getStringExtra("spe");
			   String bea=intent.getStringExtra("bea");
			   String time=intent.getStringExtra("time");
			   //将字符型速度转化为浮点型数据
			   float speed=stringToFloat(spe);
			   //将速度由海里每小时转为米每秒
			   double speed2=0.514*speed;
			   final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式		   
//			   prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
//				editor2=prefer.edit();
//				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
//                Editor edit=preferences.edit();
              
//                
//                editor2.putString("gpsinfo", "经度："+lon+"\n纬度："+lat+"\n海拔："+alt+"m\n时间："+df.format(new Date()));
//                
//                editor2.commit();
                
                
                
             
//               final  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//         	   System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
//         	  	
 		    if(lon!=null&&!"".equals(lon)&&lat!=null&&!"".equals(lat)){
 		    	
             	
             	   
	 		     
	 		    editText.setText("经度 :"+lon+"\n纬度 :"+lat+"\n海拔："+alt+"\n速度："+speed2+"m/s\n方位角："+bea+"°\n时间："+df.format(new Date()));//+spe+"m/s"+"海拔:"+alt+"m 方位角:"+bea+"°\n"+df.format(new Date())
	 		   //"\n速度:"+spe+"m/s"+"海拔:"+alt+"m 方位角:"+bea+"°\n"+
	 		   }else{
	 		    editText.setText("经度:"+lon+"\n纬度："+lat+"\n海拔："+alt+"\n速度："+speed2+"m/s\n方位角："+bea+"°\n时间："+df.format(new Date()));//+spe+"m/s"+"海拔:"+alt+"m 方位角:"+bea+"°\n"+df.format(new Date())
		 		//"\n速度:"+spe+"m/s"+"海拔:"+alt+"m 方位角:"+bea+"°\n"+ 
	 		   }
	 		  }
	 		 }
	 		  
	 		 @Override
	 		 protected void onDestroy() {
	 		  //注销服务
	 		  unregisterReceiver(receiver);
	 		  //结束服务，如果想让服务一直运行就注销此句
	 		  stopService(new Intent(this, GpsService.class));
	 		  super.onDestroy();
	 		 }
	 		  
	 		 
	 		

}
