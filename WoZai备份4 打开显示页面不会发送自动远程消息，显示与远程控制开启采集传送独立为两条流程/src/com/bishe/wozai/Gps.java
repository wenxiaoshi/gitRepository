package com.bishe.wozai;

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
  * 初始化 
  * 
  * @param ctx
  */
 public Gps(Context ctx) {
  context=ctx;
  locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
  location = locationManager.getLastKnownLocation(getProvider());
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
  //第一个参数是驱动，一般有gps和network两种，此处是GPS驱动，第二个参数是更新的时间，单位是毫秒，1000毫秒也就是一秒，
  //第三个参数是更新的距离，单位是米，第四个参数是位置服务的监听

 }
 
 
 // 获取Location Provider
 private String getProvider() {
  // 构建位置查询条件
  Criteria criteria = new Criteria();
  // 查询精度：高
  criteria.setAccuracy(Criteria.ACCURACY_FINE);
  // 是否查询海拨：是
  criteria.setAltitudeRequired(true);
  // 是否查询方位角 : 是
  criteria.setBearingRequired(true);
  // 是否允许付费：是
  criteria.setCostAllowed(true);
//设置是否要求速度：是
  criteria.setSpeedRequired(true);
//电量要求：低
  criteria.setPowerRequirement(Criteria.POWER_LOW);
  // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
  return locationManager.getBestProvider(criteria, true);
 }
 
 private LocationListener locationListener = new LocationListener() {
  // 位置服务监听，在位置发生改变后调用
  public void onLocationChanged(Location l) {
   if(l!=null){
    location=l;
   }
  }
 //如果位置不为null,将位置信息赋予location
  
  // provider 被用户关闭后调用
  public void onProviderDisabled(String provider) {
   location=null;
  }
 
  // provider 被用户开启后调用
  public void onProviderEnabled(String provider) {
   Location l = locationManager.getLastKnownLocation(provider);
   if(l!=null){
    location=l;
   }
     
  }
 
  // provider 状态变化时调用
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