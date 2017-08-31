package com.bishe.servicesavedata;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
 
public class Gps{
 private Location location = null;
 private LocationManager locationManager = null;
 private Context context = null;
 
 /**
  * ��ʼ�� 
  * 
  * @param ctx
  */
 public Gps(Context ctx) {
  context=ctx;
  locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
  location = locationManager.getLastKnownLocation(getProvider());
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
  //��һ��������������һ����gps��network���֣��˴���GPS�������ڶ��������Ǹ��µ�ʱ�䣬��λ�Ǻ��룬1000����Ҳ����һ�룬
  //�����������Ǹ��µľ��룬��λ���ף����ĸ�������λ�÷���ļ���

 }
 
 
 // ��ȡLocation Provider
 private String getProvider() {
  // ����λ�ò�ѯ����
  Criteria criteria = new Criteria();
  // ��ѯ���ȣ���
  criteria.setAccuracy(Criteria.ACCURACY_FINE);
  // �Ƿ��ѯ��������
  criteria.setAltitudeRequired(true);
  // �Ƿ��ѯ��λ�� : ��
  criteria.setBearingRequired(true);
  // �Ƿ������ѣ���
  criteria.setCostAllowed(true);
//�����Ƿ�Ҫ���ٶȣ���
  criteria.setSpeedRequired(true);
//����Ҫ�󣺵�
  criteria.setPowerRequirement(Criteria.POWER_LOW);
  // ��������ʵķ���������provider����2������Ϊtrue˵�� , ���ֻ��һ��provider����Ч��,�򷵻ص�ǰprovider
  return locationManager.getBestProvider(criteria, true);
 }
 
 private LocationListener locationListener = new LocationListener() {
  // λ�÷����������λ�÷����ı�����
  public void onLocationChanged(Location l) {
   if(l!=null){
    location=l;
   }
  }
 //���λ�ò�Ϊnull,��λ����Ϣ����location
  
  // provider ���û��رպ����
  public void onProviderDisabled(String provider) {
   location=null;
  }
 
  // provider ���û����������
  public void onProviderEnabled(String provider) {
   Location l = locationManager.getLastKnownLocation(provider);
   if(l!=null){
    location=l;
   }
     
  }
 
  // provider ״̬�仯ʱ����
  public void onStatusChanged(String provider, int status, Bundle extras) {
  }
 
 };
  
 public Location getLocation(){
  return location;
 }
  
 public void closeLocation(){
  if(locationManager!=null){
   if(locationListener!=null){
    locationManager.removeUpdates(locationListener);
    locationListener=null;
   }
   locationManager=null;
  }
 }
 
 
}