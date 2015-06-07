package kerplunk.victor.yoked.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import kerplunk.victor.yoked.R;

/**
 * Created by Victor on 5/24/2015.
/*
 */

public class PastWorkoutsAdapter extends ArrayAdapter<ParseObject> {
    protected Context mContext;
    protected List<ParseObject> mWorkouts;

    public PastWorkoutsAdapter(Context context, List<ParseObject> workouts) {
        super(context, R.layout.workout_item, workouts);
        mContext = context;
        mWorkouts = workouts;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.program_name, null);

        }

        ParseObject workouts = mWorkouts.get(position);
        if(workouts != null) {

            ImageView icon = (ImageView) v.findViewById(R.id.icon_iv);
            TextView workoutName = (TextView) v.findViewById(R.id.workoutLabel);
            TextView workoutDate = (TextView) v.findViewById(R.id.date_tv);
            TextView musclesWorked = (TextView) v.findViewById(R.id.musclesworked_tv);
            TextView workoutSubtitle = (TextView) v.findViewById(R.id.subtitle_tv);

            if (workouts.getString("workout_icon").equals("TRAPS")) {
                icon.setImageResource(R.drawable.neck);
            } else if (workouts.getString("workout_icon").equals("ARMS")) {
                icon.setImageResource(R.drawable.arms);
            } else if (workouts.getString("workout_icon").equals("SHOULDERS")) {
                icon.setImageResource(R.drawable.shoulders);
            } else if (workouts.getString("workout_icon").equals("CHEST")) {
                icon.setImageResource(R.drawable.chest);
            } else if (workouts.getString("workout_icon").equals("LEGS")) {
                icon.setImageResource(R.drawable.legs);
            } else {
                icon.setImageResource(R.drawable.legs);
            }

            workoutName.setText(workouts.getString("workout_name"));
            workoutDate.setText(workouts.getString("date"));
            musclesWorked.setText(workouts.getString("muscles_worked"));
            workoutSubtitle.setText(workouts.getString("workout_subtitle"));
        }
        return v;
    }

}

