package com.bishe.wozai;
//���ǵڶ���ҳ��
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
	    //�ַ�������ת��Ϊ�����ͺ���
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
	           
	          
	           
			   
//	 		  //�ж�GPS�Ƿ����
//	 		  Log.i(TAG, UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))+"");
//	 		  if(!UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))){
//	 		   Toast.makeText(FActivity.this, "GSP��ǰ�ѽ��ã���������ϵͳ������Ļ������", Toast.LENGTH_LONG).show();
//	 		   Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
//	 		   startActivity(callGPSSettingIntent);
//	 		            return;
//	 		  }   
	 		   
	 		  //��������
	 		  startService(new Intent(this, GpsService.class));
	 		
	 		   
	 		  //ע��㲥
	 		  receiver=new MyReceiver();
	 		  IntentFilter filter=new IntentFilter();
	 		  filter.addAction("com.bishe.wozai.GpsService");
	 		  registerReceiver(receiver, filter);
	 		  
	 		  
	 		//���ŷ��ʹ�������
	           paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0); 
	           smsManager = SmsManager.getDefault();
	           
	           bt3=(Button) findViewById(R.id.button_tellmylocation);
	           final EditText txtPhoneNo = (EditText) findViewById(R.id.editText_pnone);
	           final EditText txtMessage = (EditText) findViewById(R.id.editText);
	   		   final Context mContext=this;

	   			 
	   		   //�����֪�Է���ťʱʵ�ֶ��ŷ��ͣ�GPS�����Զ�Ƕ���ڶ���������
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
		                        "������ʼ����", 
		                        Toast.LENGTH_LONG).show();
	                	//�Զ���ֶ���
	                	ArrayList<String> texts = smsManager.divideMessage(GPSInfo);
	                	//��������
	                	for(String text:texts){
	                	
	                	smsManager.sendTextMessage(phoneNo, null, GPSInfo , paIntent, 
		                        null); 
	                	Toast.makeText(FActivity.this, 
		                        "���ͳɹ�", 
		                        Toast.LENGTH_LONG).show();
	                }}
	                else
	                    Toast.makeText(FActivity.this, 
	                        "����������", 
	                        Toast.LENGTH_LONG).show();
	   			}
	   		});
	 	}
	 		  
	 		 //��ȡ�㲥����
	 		 private class MyReceiver extends BroadcastReceiver{
	 		  @Override
	 		  public void onReceive(Context context, Intent intent) {
	 		   Bundle bundle=intent.getExtras();      
	 		  System.out.println("FActivity�յ�GPS�㲥��Ϣ:"); 
			final   String lon=intent.getStringExtra("lon");    
			   String lat=intent.getStringExtra("lat"); 
			   String alt=intent.getStringExtra("alt");
			   String spe=intent.getStringExtra("spe");
			   String bea=intent.getStringExtra("bea");
			   String time=intent.getStringExtra("time");
			   //���ַ����ٶ�ת��Ϊ����������
			   float speed=stringToFloat(spe);
			   //���ٶ��ɺ���ÿСʱתΪ��ÿ��
			   double speed2=0.514*speed;
			   final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ		   
//			   prefer=getSharedPreferences("GpsInfo", MODE_PRIVATE);
//				editor2=prefer.edit();
//				SharedPreferences preferences=getSharedPreferences("GpsInfo",MODE_WORLD_WRITEABLE);  
//                Editor edit=preferences.edit();
              
//                
//                editor2.putString("gpsinfo", "���ȣ�"+lon+"\nγ�ȣ�"+lat+"\n���Σ�"+alt+"m\nʱ�䣺"+df.format(new Date()));
//                
//                editor2.commit();
                
                
                
             
//               final  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//         	   System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
//         	  	
 		    if(lon!=null&&!"".equals(lon)&&lat!=null&&!"".equals(lat)){
 		    	
             	
             	   
	 		     
	 		    editText.setText("���� :"+lon+"\nγ�� :"+lat+"\n���Σ�"+alt+"\n�ٶȣ�"+speed2+"m/s\n��λ�ǣ�"+bea+"��\nʱ�䣺"+df.format(new Date()));//+spe+"m/s"+"����:"+alt+"m ��λ��:"+bea+"��\n"+df.format(new Date())
	 		   //"\n�ٶ�:"+spe+"m/s"+"����:"+alt+"m ��λ��:"+bea+"��\n"+
	 		   }else{
	 		    editText.setText("����:"+lon+"\nγ�ȣ�"+lat+"\n���Σ�"+alt+"\n�ٶȣ�"+speed2+"m/s\n��λ�ǣ�"+bea+"��\nʱ�䣺"+df.format(new Date()));//+spe+"m/s"+"����:"+alt+"m ��λ��:"+bea+"��\n"+df.format(new Date())
		 		//"\n�ٶ�:"+spe+"m/s"+"����:"+alt+"m ��λ��:"+bea+"��\n"+ 
	 		   }
	 		  }
	 		 }
	 		  
	 		 @Override
	 		 protected void onDestroy() {
	 		  //ע������
	 		  unregisterReceiver(receiver);
	 		  //��������������÷���һֱ���о�ע���˾�
	 		  stopService(new Intent(this, GpsService.class));
	 		  super.onDestroy();
	 		 }
	 		  
	 		 
	 		

}
