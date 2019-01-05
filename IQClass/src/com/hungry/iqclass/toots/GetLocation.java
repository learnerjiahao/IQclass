package com.hungry.iqclass.toots;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;



public class GetLocation {
	
	 private LocationManager locationManager;  
     private String locationProvider;
     private Location location;
     private double latitude ;
     private double longitude ;
     
     
	 public GetLocation(Context context) {
		 //获取地理位置管理器  
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
          //获取所有可用的位置提供器  
        List<String> providers = locationManager.getProviders(true);  
        if(providers.contains(LocationManager.GPS_PROVIDER)){  
            //如果是GPS  
             locationProvider = LocationManager.GPS_PROVIDER;  
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){  
            //如果是Network  
             locationProvider = LocationManager.NETWORK_PROVIDER;  
        }else if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(context, "请开启GPS！", Toast.LENGTH_SHORT).show();
            //无网络没开GPS时提醒并跳转到GPS设置界面;
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);  
        }  
        //获取Location  
        location = locationManager.getLastKnownLocation(locationProvider);  
        if(location!=null){  
            //不为空,显示地理位置经纬度  
            setLocation(); 
        }  
        //监视地理位置变化  
        locationManager.requestLocationUpdates(locationProvider, 1000, 0, locationListener);  
          
    }  
	 
	 /**
		 * @return the latitude
		 */
		public double getLatitude() {
			return latitude;
		}

		/**
		 * @return the longitude
		 */
		public double getLongitude() {
			return longitude;
		}

		 /** 
	     * 获取地理位置经度和纬度信息 
	     * @param location 
	     */  
	    private void setLocation(){  
	    	this.latitude = location.getLatitude();    
	        this.longitude = location.getLongitude();  
	    }  
	      
    /** 
     * LocationListern监听器 
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器 
     */  
      
    LocationListener locationListener =  new LocationListener() {  
          
        @Override  
        public void onStatusChanged(String provider, int status, Bundle arg2) {  
              
        }  
          
        @Override  
        public void onProviderEnabled(String provider) {  
              
        }  
          
        @Override  
        public void onProviderDisabled(String provider) {  
              
        }  
          
        @Override  
        public void onLocationChanged(Location location) {  
            //如果位置发生变化,重新显示  
            setLocation();
            
        }
        

		
    };  
      
  
    protected void destroyLocationListener() {  
        if(locationManager!=null){  
            //移除监听器  
            locationManager.removeUpdates(locationListener);  
        }  
    }  

}
