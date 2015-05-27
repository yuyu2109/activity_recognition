package com.aware.plugin.google.activity_recognition;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.utils.Converters;
import com.aware.utils.IContextCard;
import com.google.android.gms.location.DetectedActivity;

/**
 * New Stream UI cards<br/>
 * Implement here what you see on your Plugin's UI.
 * @author denzilferreira
 */
public class ContextCard implements IContextCard {
    public static final int IND_MAX = 20;
    public ContextCard(){}
	
	public View getContextCard(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mInflated = inflater.inflate(R.layout.layout, null);
		
		TextView still = (TextView) mInflated.findViewById(R.id.time_still);
        TextView walking = (TextView) mInflated.findViewById(R.id.time_walking);
        TextView biking = (TextView) mInflated.findViewById(R.id.time_biking);
        TextView vehicle = (TextView) mInflated.findViewById(R.id.time_vehicle);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        //Modify time to be at the begining of today
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        long now = mCalendar.getTime().getTime();

        long lastTimeStill = Stats.getLastTimeStill(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis());
        long lastTimeWalking = Stats.getLastTimeWalking(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis());
        long lastTimeBiking = Stats.getLastTimeBiking(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis());
        long lastTimeVehicle = Stats.getLastTimeVehicle(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis());

        //Get stats for today
        //still.setText(Converters.readable_elapsed(Stats.getLastTimeStill(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));
        still.setText(Converters.readable_elapsed(getTime(now, lastTimeStill)));
        walking.setText(Converters.readable_elapsed(getTime(now,lastTimeWalking)));
        biking.setText(Converters.readable_elapsed(getTime(now, lastTimeBiking)));
        vehicle.setText(Converters.readable_elapsed(getTime(now, lastTimeVehicle)));

        for (int k = 0; k < IND_MAX; k++) {
            Stats.getActivity(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis(), k);
            setInfo(context, mInflated, now, k);
        }

		return mInflated;
	}

    private long getTime(long now, long time) {
        if (time == 0)
            return time;
        return time - now;
    }

    private void setInfo(Context context, View mInflated, long now ,int indice) {
        int image_resource = context.getResources().getIdentifier("icon_show" + indice, "id", context.getPackageName());
        int text_resource = context.getResources().getIdentifier("textShow"  +indice, "id", context.getPackageName());

        ImageView icon = (ImageView) mInflated.findViewById(image_resource);
        TextView text = (TextView) mInflated.findViewById(text_resource);

        switch (Plugin.type) {
            case DetectedActivity.IN_VEHICLE:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_vehicle_own);
                else
                    icon.setImageResource(R.drawable.ic_action_vehicle);

                text.setText("Vehicle at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.ON_BICYCLE:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_biking_own);
                else
                    icon.setImageResource(R.drawable.ic_action_biking);

                text.setText("Bicycle at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.ON_FOOT:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_walking_own);
                else
                    icon.setImageResource(R.drawable.ic_action_walking);

                text.setText("On foot at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.STILL:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_still_own);
                else
                    icon.setImageResource(R.drawable.ic_action_still);

                text.setText("Still at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.UNKNOWN:
                if (Plugin.confidence >= 50)
                    icon.setImageResource(R.drawable.unknown_black_own);
                else
                    icon.setImageResource(R.drawable.unknown_grey_own);

                text.setText("Unknown at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.TILTING:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_tilting_own);
                else
                    icon.setImageResource(R.drawable.ic_action_tilting_grey_own);

                text.setText("I'm not sure what you are doing at " + Converters.readable_elapsed(getTime(now, Plugin.time)));
                break;
            case DetectedActivity.RUNNING:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_walking_own);
                else
                    icon.setImageResource(R.drawable.ic_action_walking);

                text.setText("Running at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            case DetectedActivity.WALKING:
                if (Plugin.confidence >= 70)
                    icon.setImageResource(R.drawable.ic_action_walking_own);
                else
                    icon.setImageResource(R.drawable.ic_action_walking);

                text.setText("Walking at " + Converters.readable_elapsed(getTime(now, Plugin.time)) + " with confidence " + Plugin.confidence);
                break;
            default:
                text.setText("Error : activity code not found " + Plugin.current_activity);
        }
    }
}
