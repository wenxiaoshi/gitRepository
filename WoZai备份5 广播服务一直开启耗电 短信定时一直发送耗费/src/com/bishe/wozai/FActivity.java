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
	           
	           bt3=(Button) findViewById(R.id.button_tellmylocation);
	           final EditText txtPhoneNo = (EditText) findViewById(R.id.editText_pnone);
	           final EditText txtMessage = (EditText) findViewById(R.id.editText);
	   		   final Context mContext=this;
	   		 paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0); 
	           smsManager = SmsManager.getDefault();
	   		   bt3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					// TODO �Զ����ɵķ������
					
	   				String phoneNo =txtPhoneNo.getText().toString();
		             String message = txtMessage.getText().toString();
	   				
	                if (phoneNo.length()==11&&message.length()>0){                
	                	Log.v(FAG, "will begin sendSMS");
	                	Toast.makeText(FActivity.this, 
		                        "������ʼ����", 
		                        Toast.LENGTH_LONG).show();
	                	//�Զ���ֶ���
	                	ArrayList<String> texts = smsManager.divideMessage(message);
	                	//��������
	                	for(String text:texts){
	                	
	                	smsManager.sendTextMessage(phoneNo, null, message , paIntent, 
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

	           editText=(EditText)findViewById(R.id.editText);
	           lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	           
	           //�ж�GPS�Ƿ���������
	           if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	               Toast.makeText(this, "�뿪��GPS����...", Toast.LENGTH_LONG).show();
	               //���ؿ���GPS�������ý��棬�ο�����������תҳ��
	               Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
	               startActivityForResult(intent,0); 
	               return;
	           }
	           
	           //Ϊ��ȡ����λ����Ϣʱ���ò�ѯ����
	           String bestProvider = lm.getBestProvider(getCriteria(), true);
	           //��ȡλ����Ϣ
	           //��������ò�ѯҪ��getLastKnownLocation��������Ĳ���ΪLocationManager.GPS_PROVIDER
	           Location location= lm.getLastKnownLocation(bestProvider);    
	           updateView(location);
	           //����״̬
	           lm.addGpsStatusListener(listener);
	           //�󶨼�������4������    
	           //����1���豸����GPS_PROVIDER��NETWORK_PROVIDER����
	           //����2��λ����Ϣ�������ڣ���λ����    
	           //����3��λ�ñ仯��С���룺��λ�þ���仯������ֵʱ��������λ����Ϣ    
	           //����4������    
	           //��ע������2��3���������3��Ϊ0�����Բ���3Ϊ׼������3Ϊ0����ͨ��ʱ������ʱ���£�����Ϊ0������ʱˢ��   
	           
	           // 1�����һ�Σ�����Сλ�Ʊ仯����1�׸���һ�Σ�
	           //ע�⣺�˴�����׼ȷ�ȷǳ��ͣ��Ƽ���service��������һ��Thread����run��sleep(10000);Ȼ��ִ��handler.sendMessage(),����λ��
	           lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
	       }
	       
	     

	 	//λ�ü���
	       private LocationListener locationListener=new LocationListener() {
	           
	           /**
	            * λ����Ϣ�仯ʱ����
	            */
	           public void onLocationChanged(Location location) {
	               updateView(location);
	               Log.i(FAG, "ʱ�䣺"+location.getTime()); 
	               Log.i(FAG, "���ȣ�"+location.getLongitude()); 
	               Log.i(FAG, "γ�ȣ�"+location.getLatitude()); 
	               Log.i(FAG, "���Σ�"+location.getAltitude()); 
	           }
	           
	           /**
	            * GPS״̬�仯ʱ����
	            */
	           public void onStatusChanged(String provider, int status, Bundle extras) {
	               switch (status) {
	               //GPS״̬Ϊ�ɼ�ʱ
	               case LocationProvider.AVAILABLE:
	                   Log.i(FAG, "��ǰGPS״̬Ϊ�ɼ�״̬");
	                   break;
	               //GPS״̬Ϊ��������ʱ
	               case LocationProvider.OUT_OF_SERVICE:
	                   Log.i(FAG, "��ǰGPS״̬Ϊ��������״̬");
	                   break;
	               //GPS״̬Ϊ��ͣ����ʱ
	               case LocationProvider.TEMPORARILY_UNAVAILABLE:
	                   Log.i(FAG, "��ǰGPS״̬Ϊ��ͣ����״̬");
	                   break;
	               }
	           }
	       
	           /**
	            * GPS����ʱ����
	            */
	           public void onProviderEnabled(String provider) {
	               Location location=lm.getLastKnownLocation(provider);
	               updateView(location);
	           }
	       
	           /**
	            * GPS����ʱ����
	            */
	           public void onProviderDisabled(String provider) {
	               updateView(null);
	           }

	       
	       };
	       
	       //״̬����
	       GpsStatus.Listener listener = new GpsStatus.Listener() {
	           public void onGpsStatusChanged(int event) {
	               switch (event) {
	               //��һ�ζ�λ
	               case GpsStatus.GPS_EVENT_FIRST_FIX:
	                   Log.i(FAG, "��һ�ζ�λ");
	                   break;
	               //����״̬�ı�
	               case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
	                   Log.i(FAG, "����״̬�ı�");
	                   //��ȡ��ǰ״̬
	                   GpsStatus gpsStatus=lm.getGpsStatus(null);
	                   //��ȡ���ǿ�����Ĭ�����ֵ
	                   int maxSatellites = gpsStatus.getMaxSatellites();
	                   //����һ�������������������� 
	                   Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
	                   int count = 0;     
	                   while (iters.hasNext() && count <= maxSatellites) {     
	                       GpsSatellite s = iters.next();     
	                       count++;     
	                   }   
	                   System.out.println("��������"+count+"������");
	                   break;
	               //��λ����
	               case GpsStatus.GPS_EVENT_STARTED:
	                   Log.i(FAG, "��λ����");
	                   break;
	               //��λ����
	               case GpsStatus.GPS_EVENT_STOPPED:
	                   Log.i(FAG, "��λ����");
	                   break;
	               }
	           };
	       };
	       
	       /**
	        * ʵʱ�����ı�����
	        * 
	        * @param location
	        */
	       
	       private void updateView(Location location){
	    
	           if(location!=null){   

				   //���ַ����ٶ�ת��Ϊ����������
				  
				   //���ٶ��ɺ���ÿСʱתΪ��ÿ��
				   double speed2=0.514*location.getSpeed();
				   final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ	
	               editText.setText("���ȣ�");
	               editText.append(String.valueOf(location.getLongitude()));
	               editText.append("\nγ�ȣ�");
	               editText.append(String.valueOf(location.getLatitude()));
	               editText.append("\n���Σ�");
	               editText.append(String.valueOf(location.getAltitude())+"��");
//	               editText.append("\n�ٶȣ�");
//	               editText.append(speed2+"m/s");
//	               editText.append("\n��λ�ǣ�");
//	               editText.append(String.valueOf(location.getBearing())+"��");
	               editText.append("\nʱ�䣺");
	               editText.append(df.format(new Date()));
	               
	           }else{
	               //���EditText����
	               editText.getEditableText().clear();
	           }
	       }
	      
	      
		/**
	        * ���ز�ѯ����
	        * @return
	   */
	       private Criteria getCriteria(){
	           Criteria criteria=new Criteria();
	           //���ö�λ��ȷ�� Criteria.ACCURACY_COARSE�Ƚϴ��ԣ�Criteria.ACCURACY_FINE��ȽϾ�ϸ 
	           criteria.setAccuracy(Criteria.ACCURACY_FINE);    
	           //�����Ƿ�Ҫ���ٶ�
	           criteria.setSpeedRequired(true);
	           // �����Ƿ�������Ӫ���շ�  
	           criteria.setCostAllowed(true);
	           //�����Ƿ���Ҫ��λ��Ϣ
	           criteria.setBearingRequired(true);
	           //�����Ƿ���Ҫ������Ϣ
	           criteria.setAltitudeRequired(true);
	           // ���öԵ�Դ������  
	           criteria.setPowerRequirement(Criteria.POWER_LOW);
	           return criteria;
	          
			   

	 	}
	      
   			 
   		   
 	}	  
	
         
         
  

	 		 
 		


