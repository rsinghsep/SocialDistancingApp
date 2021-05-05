package com.staysilly.socialdistancingapp.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

class ProximityAlarm
    {
//        public static void main(String[] args)
//        {
//        	ProximityAlarm proxAlarm = new ProximityAlarm();
//
//            double[] LatLongsArraySrc = { 17.46782, 78.36348 };
//
//            double[][] LatLongsArrayDest = {{17.46794, 78.36348},{17.46896, 78.36348},{17.46898, 78.36348}};
//            ArrayList<double[]> latLongsList = new ArrayList<>();
//            latLongsList.add(LatLongsArrayDest[0]);
//            latLongsList.add(LatLongsArrayDest[1]);
//            latLongsList.add(LatLongsArrayDest[2]);
//
//            boolean proximityCheck = proxAlarm.proximity_Notification(LatLongsArraySrc,latLongsList);
//
//          if (proximityCheck)
//          {
//              System.out.println("Alarm");
//          }
//          else
//          {
//          	System.out.println("No Alarm");
//          }
//        }
        
        public static boolean isProtocolBroken(LatLng myCurrentLocation, ArrayList<LatLng> onlineUsersLocation){
            double[] myLatLong = {myCurrentLocation.longitude, myCurrentLocation.longitude};
            ArrayList<double[]> otherUsersLocation = new ArrayList<>();
            for (LatLng otherUser : onlineUsersLocation){
                double[] latLngArray = {otherUser.longitude, otherUser.longitude};
                otherUsersLocation.add(latLngArray);
            }
            return proximity_Notification(myLatLong, otherUsersLocation);
        }
        
        public static boolean proximity_Notification(double[] latlongsarraySrc, ArrayList<double[]> latLongsList)
        {
        	ProximityAlarm proxAlarm = new ProximityAlarm();
        	//By This step we will be ready with Latitudes and longitudes of Source and list of Lat and Long of Dest coordinates
            boolean proximityCheck = false;
            for (double[] latlongsdest : latLongsList)
            {
                double Dist_In_Feet = distance(latlongsarraySrc[0], latlongsarraySrc[1],latlongsdest[0], latlongsdest[1],'F');
                System.out.println(Dist_In_Feet + " fts");
                proximityCheck = proxAlarm.InsideProximityCheck(Dist_In_Feet);
                if (proximityCheck)
                {
                	System.out.println("A person in "+Math.round(Dist_In_Feet) + " fts proximity is Observed. Be careful! Alarm ALarm Alarm!");
                    break;
                }
            }
            
            return(proximityCheck);
        	
        }


        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //:::                                                                         :::
        //:::  This routine calculates the distance between two points (given the     :::
        //:::  latitude/longitude of those points). It is being used to calculate     :::
        //:::  the distance between two locations using GeoDataSource(TM) products    :::
        //:::                                                                         :::
        //:::  Definitions:                                                           :::
        //:::    South latitudes are negative, east longitudes are positive           :::
        //:::                                                                         :::
        //:::  Passed to function:                                                    :::
        //:::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :::
        //:::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :::
        //:::    unit = the unit you desire for results                               :::
        //:::           where: 'M' is statute miles (default)                         :::
        //:::                  'K' is kilometers                                      :::
        //:::                  'N' is nautical miles                                  :::
        //:::                                                                         :::
        //:::  Worldwide cities and other features databases with latitude longitude  :::
        //:::  are available at https://www.geodatasource.com                         :::
        //:::                                                                         :::
        //:::  For enquiries, please contact sales@geodatasource.com                  :::
        //:::                                                                         :::
        //:::  Official Web site: https://www.geodatasource.com                       :::
        //:::                                                                         :::
        //:::           GeoDataSource.com (C) All Rights Reserved 2018                :::
        //:::                                                                         :::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        private static double distance(double lat1, double lon1, double lat2, double lon2, char unit)
        {
            if ((lat1 == lat2) && (lon1 == lon2))
            {
                return 0;
            }
            else
            {
                double theta = lon1 - lon2;
                double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
                dist = Math.acos(dist);
                dist = rad2deg(dist);
                //dist = dist * 60 * 1.1515;
                dist = dist * (126.5/180) * Math.PI;
                if (unit == 'K')
                {
                    dist = dist * 1.609344;
                }
                else if (unit == 'N')
                {
                    dist = dist * 0.8684;
                }
                else if (unit == 'F')
                {
                    //dist = dist * 1.609344* 3280.84;
                    //dist = dist * 364795.2;
                    dist = dist * 5280;
                }

                return (dist);
            }
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::  This function converts decimal degrees to radians             :::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        private static double deg2rad(double deg)
        {
            return (deg * Math.PI / 180.0);
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::  This function converts radians to decimal degrees             :::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        private static double rad2deg(double rad)
        {
            return (rad / Math.PI * 180.0);
        }

        private boolean InsideProximityCheck(double Dist)
        {
        	boolean Proximity_check;
            Proximity_check = Math.abs(Dist) < 6 ? true : false;
            return Proximity_check;
        }


    }

//double Dist_In_Feet = program.distance(17.46782, 78.36338, 17.46814, 78.36312, 'F');
//Console.WriteLine(Dist_In_Feet);

//double[][] LatLongsArrayDest;
//LatLongsArrayDest = new double [1][]{ new double[2] { 17.46814, 78.36313 } };
//var LatLongsArrayDest = [17.46814, 78.36313 ];

////By This step we will be ready with Latitudes and longitudes of Source and list of Lat and Long of Dest coordinates
//boolean proximityCheck = false;
//for (double[] LatLongsDest : latLongsList)
//{
//  double Dist_In_Feet = program.distance(LatLongsArraySrc[0], LatLongsArraySrc[1],LatLongsDest[0], LatLongsDest[1],'F');
//  System.out.println(Dist_In_Feet + " fts");
//  proximityCheck = program.InsideProximityCheck(Dist_In_Feet);
//  if (proximityCheck)
//  {
//  	System.out.println("A person in "+Math.round(Dist_In_Feet) + " fts proximity is Observed. Be careful! Alarm ALarm Alarm!");
//      break;
//  }
//}
