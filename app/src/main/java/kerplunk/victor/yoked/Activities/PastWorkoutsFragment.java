package kerplunk.victor.yoked.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import kerplunk.victor.yoked.Adapters.PastWorkoutsAdapter;
import kerplunk.victor.yoked.R;


public class PastWorkoutsFragment extends ListFragment {
    List<String> workoutDates = new ArrayList<String>();
    List<String> workoutNames = new ArrayList<String>();
    List<ParseObject> workouts;
    static String tempString;
    static String tempWorkoutName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View V = inflater.inflate(R.layout.fragment_select_workout, container, false);


        return  V;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String currUser =  ParseUser.getCurrentUser().getUsername();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("username", currUser);
        query.orderByDescending("date");
        workoutDates.clear();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list == null) {

                } else {

                    for (int i = 0; i < list.size(); i++) {
                        tempString = list.get(i).getString("date");
                        tempWorkoutName = list.get(i).getString("full_workout_name");
                        workoutNames.add(tempWorkoutName);
                        workoutDates.add(tempString);
                    }
                    workouts = list;

                }
                PastWorkoutsAdapter adapter = new PastWorkoutsAdapter
                        (getListView().getContext(), workouts);
                setListAdapter(adapter);
                getListView().setDivider(null);
                getListView().setDividerHeight(0);
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), ViewWorkoutActivity.class);
        intent.putExtra("DATE_VALUE",workoutDates.get(position));
        intent.putExtra("NAME_VALUE", workoutNames.get(position));

        intent.putExtra("FROM_ACTIVITY", "PastWorkouts");
        startActivity(intent);
    }
}
