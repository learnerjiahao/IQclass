package com.hungry.iqclass.toots;


public class GetDistance {

	private static final double EARTH_RADIUS = 6378137.0;  //地球半径,单位为米
	
	//得到老师和学生的距离,单位为米
	public static double getDistance(double lat1,double lon1,double lat2,double lon2){
		double Lat1 = rad(lat1);
		double Lat2 = rad(lat2);
		double a = Lat1 - Lat2; 
		double b = rad(lon1) -rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
			    + Math.cos(Lat1) * Math.cos(Lat2)  
			    * Math.pow(Math.sin(b / 2), 2)));  
			  s = s * EARTH_RADIUS;  
			  s = Math.round(s * 10000) / 10000;  
		return s;  
		
	}
	
	 private static double rad(double d) { 
		 return d * Math.PI / 180.0;
	}  
}  

