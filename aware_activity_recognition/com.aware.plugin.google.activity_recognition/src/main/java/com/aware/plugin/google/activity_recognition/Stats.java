package com.aware.plugin.google.activity_recognition;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.aware.plugin.google.activity_recognition.Google_AR_Provider.Google_Activity_Recognition_Data;
import com.google.android.gms.location.DetectedActivity;

import java.util.HashMap;

public class Stats {
	
	/**
	 * Get the total amount of time still between two timestamps
	 * @param resolver
	 * @param timestamp_start
	 * @param timestamp_end
	 * @return total_time_still
	 */
	public static long getTimeStill(ContentResolver resolver, long timestamp_start, long timestamp_end) {
		long total_time_still = 0;
		
		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
		if( activity_raw != null && activity_raw.moveToFirst() ) {
			int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
			
			while(activity_raw.moveToNext()) {
				int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
				long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
				
				if( activity == DetectedActivity.STILL && last_activity == DetectedActivity.STILL ) { //continuing still
					total_time_still += activity_timestamp-last_activity_timestamp;
				}
				
				last_activity = activity;
				last_activity_timestamp = activity_timestamp;
			}
		}
		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
		return total_time_still;
	}

	public static long getLastTimeStill(ContentResolver resolver, long timestamp_start, long timestamp_end) {
		long last_time_still = 0;

		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
		Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
		if( activity_raw != null && activity_raw.moveToFirst() ) {
			int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

			while(activity_raw.moveToNext()) {
				int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
				long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

				if( activity == DetectedActivity.STILL && last_activity == DetectedActivity.STILL ) { //continuing still
					if (last_time_still < activity_timestamp) {
						last_time_still = activity_timestamp;
					}
				}

				last_activity = activity;
				last_activity_timestamp = activity_timestamp;
			}
		}
		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
		return last_time_still;
	}

	/**
	 * Get the total amount of time biking between two timestamps
	 * @param resolver
	 * @param timestamp_start
	 * @param timestamp_end
	 * @return total_time_still
	 */
	public static long getTimeBiking(ContentResolver resolver, long timestamp_start, long timestamp_end) {
		long total_time_bike = 0;
		
		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
		if( activity_raw != null && activity_raw.moveToFirst() ) {
			int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
			
			while(activity_raw.moveToNext()) {
				int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
				long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
				
				if( activity == DetectedActivity.ON_BICYCLE && last_activity == DetectedActivity.ON_BICYCLE ) { //continuing biking 
                    total_time_bike += activity_timestamp-last_activity_timestamp;
				}
				
				last_activity = activity;
				last_activity_timestamp = activity_timestamp;
			}
		}
		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
		return total_time_bike;
	}

    public static long getLastTimeBiking(ContentResolver resolver, long timestamp_start, long timestamp_end) {
        long last_time_bike = 0;

        String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
        if( activity_raw != null && activity_raw.moveToFirst() ) {
            int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
            long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

            while(activity_raw.moveToNext()) {
                int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
                long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

                if( activity == DetectedActivity.ON_BICYCLE && last_activity == DetectedActivity.ON_BICYCLE ) { //continuing biking
                    if (last_time_bike < activity_timestamp) {
                        last_time_bike = activity_timestamp;
                    }
                }

                last_activity = activity;
                last_activity_timestamp = activity_timestamp;
            }
        }
        if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
        return last_time_bike;
    }
	
	/**
	 * Get the total amount of time still between two timestamps
	 * @param resolver
	 * @param timestamp_start
	 * @param timestamp_end
	 * @return total_time_still
	 */
	public static long getTimeVehicle(ContentResolver resolver, long timestamp_start, long timestamp_end) {
		long total_time_vehicle = 0;
		
		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
		if( activity_raw != null && activity_raw.moveToFirst() ) {
			int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
			
			while(activity_raw.moveToNext()) {
				int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
				long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
				
				if( activity == DetectedActivity.IN_VEHICLE && last_activity == DetectedActivity.IN_VEHICLE ) { //continuing in vehicle 
                    total_time_vehicle += activity_timestamp-last_activity_timestamp;
				}
				
				last_activity = activity;
				last_activity_timestamp = activity_timestamp;
			}
		}
		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
		return total_time_vehicle;
	}

    public static long getLastTimeVehicle(ContentResolver resolver, long timestamp_start, long timestamp_end) {
        long last_time_vehicle = 0;

        String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
        if( activity_raw != null && activity_raw.moveToFirst() ) {
            int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
            long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

            while(activity_raw.moveToNext()) {
                int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
                long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

                if( activity == DetectedActivity.IN_VEHICLE && last_activity == DetectedActivity.IN_VEHICLE ) { //continuing in vehicle
                    if (last_time_vehicle < activity_timestamp) {
                        last_time_vehicle = activity_timestamp;
                    }
                }

                last_activity = activity;
                last_activity_timestamp = activity_timestamp;
            }
        }
        if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
        return last_time_vehicle;
    }

	/**
	 * Get the total amount of time still between two timestamps
	 * @param resolver
	 * @param timestamp_start
	 * @param timestamp_end
	 * @return total_time_still
	 */
	public static long getTimeWalking(ContentResolver resolver, long timestamp_start, long timestamp_end) {
		long total_time_walking = 0;
		
		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
		if( activity_raw != null && activity_raw.moveToFirst() ) {

            int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
			
			while(activity_raw.moveToNext()) {
				int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
				long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
				
				if( activity == DetectedActivity.ON_FOOT && last_activity == DetectedActivity.ON_FOOT ) { //continuing on foot
                    total_time_walking += activity_timestamp-last_activity_timestamp;
				}
				
				last_activity = activity;
				last_activity_timestamp = activity_timestamp;
			}
		}
		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
		return total_time_walking;
	}

    public static long getLastTimeWalking(ContentResolver resolver, long timestamp_start, long timestamp_end) {
        long last_time_walking = 0;

        String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
        Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");
        if( activity_raw != null && activity_raw.moveToFirst() ) {

            int last_activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
            long last_activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

            while(activity_raw.moveToNext()) {
                int activity = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
                long activity_timestamp = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));

                if( activity == DetectedActivity.ON_FOOT && last_activity == DetectedActivity.ON_FOOT ) { //continuing on foot
                    if (last_time_walking < activity_timestamp) {
                        last_time_walking = activity_timestamp;
                    }
                }

                last_activity = activity;
                last_activity_timestamp = activity_timestamp;
            }
        }
        if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
        return last_time_walking;
    }

	public static void getActivity(ContentResolver resolver, long timestamp_start, long timestamp_end, int i) {
		String selection = Google_Activity_Recognition_Data.TIMESTAMP + " between " + timestamp_start + " AND " + timestamp_end;
		Cursor activity_raw = resolver.query(Google_Activity_Recognition_Data.CONTENT_URI, null, selection, null, Google_Activity_Recognition_Data.TIMESTAMP + " ASC");

		if( activity_raw != null && activity_raw.moveToLast() ) {
			for (int k = 0; k < i; k++) {
				activity_raw.moveToPrevious();
			}

			Plugin.type = activity_raw.getInt(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.ACTIVITY_TYPE));
			Plugin.time = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.TIMESTAMP));
			Plugin.confidence = activity_raw.getLong(activity_raw.getColumnIndex(Google_Activity_Recognition_Data.CONFIDENCE));
		}

		if( activity_raw != null && ! activity_raw.isClosed() ) activity_raw.close();
	}
}
