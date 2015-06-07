package kerplunk.victor.yoked.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kerplunk.victor.yoked.R;

/**
 * Created by Victor on 5/24/2015.
 /*
 */

public class ProgramPickerAdapter extends ArrayAdapter<String> {
    protected Context mContext;
    protected ArrayList<String> mWorkouts;
    protected ArrayList<String> mWorkoutIcon;
    protected ArrayList<String> mMusclesWorked;
    protected ArrayList<String> mSubtitle;

    public ProgramPickerAdapter(Context context, ArrayList<String> workouts,
                                ArrayList<String> musclesWorked, ArrayList<String> workoutIcon, ArrayList<String> workoutSubtitle) {
        super(context, R.layout.workout_item, workouts);
        mContext = context;
        mWorkouts = workouts;
        mWorkoutIcon = workoutIcon;
        mMusclesWorked = musclesWorked;
        mSubtitle = workoutSubtitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.workout_item, null);

        }

        String workouts = mWorkouts.get(position);
        if(workouts != null) {
            ImageView icon = (ImageView) v.findViewById(R.id.icon_iv);
            TextView workoutName = (TextView) v.findViewById(R.id.workoutLabel);
            TextView muscles = (TextView) v.findViewById(R.id.musclesworked_tv);
            TextView subtitle = (TextView) v.findViewById(R.id.subtitle_tv);
            String workoutIcon = mWorkoutIcon.get(position);
            if (workoutIcon.equals("CHEST")){
                icon.setImageResource(R.drawable.chest);
            }
            else if(workoutIcon.equals("LEGS")){
                icon.setImageResource(R.drawable.legs);
            }
            else if (workoutIcon.equals("BACK")){
                icon.setImageResource(R.drawable.back);
            }
            else if (workoutIcon.equals("SHOULDERS")){
                icon.setImageResource(R.drawable.shoulders);
            }
            else if (workoutIcon.equals("TRAPS")){
                icon.setImageResource(R.drawable.neck);
            }
            else if(workoutIcon.equals("CHEST"))
            {
                icon.setImageResource(R.drawable.chest);
            }
            else if(workoutIcon.equals("ARMS"))
            {
                icon.setImageResource(R.drawable.arms);
            }

            workoutName.setText(workouts);
            muscles.setText(mMusclesWorked.get(position));
            subtitle.setText(mSubtitle.get(position));
        }


        return v;
    }

}

