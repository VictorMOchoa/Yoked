package kerplunk.victor.yoked.Activities;


import android.content.Intent;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import kerplunk.victor.yoked.Adapters.ProgramPickerAdapter;
import kerplunk.victor.yoked.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class SelectProgramActivityFragment extends ListFragment {
    List<ParseObject> routines;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_select_existing, container, false);
    }


    @Override
    public void onResume(){
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Program");
        query.whereEqualTo("user", "Master");

        query.orderByAscending("full_workout_name");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                routines = list;
                int index = 0;
                ArrayList<String> routineNames = new ArrayList<String>();
                ArrayList<String> musclesWorked = new ArrayList<String>();
                ArrayList<String> workoutIcon = new ArrayList<String>();
                ArrayList<String> workoutSubtitle = new ArrayList<String>();
                for (ParseObject p : routines) {
                    routineNames.add(routines.get(index).getString("workout_name"));
                    musclesWorked.add(routines.get(index).getString("muscles_worked"));
                    workoutIcon.add(routines.get(index).getString("workout_icon"));
                    workoutSubtitle.add(routines.get(index).getString("workout_subtitle"));

                    index++;
                }
                ProgramPickerAdapter adapter = new ProgramPickerAdapter(getListView().getContext(), routineNames, musclesWorked, workoutIcon, workoutSubtitle);
                setListAdapter(adapter);
            }


        });


        getListView().setDivider(null);
        getListView().setDividerHeight(0);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

            Intent intent = new Intent(getActivity(), LogWorkoutActivity.class);

            intent.putExtra("OBJECT_ID",routines.get(position).getObjectId());
            startActivity(intent);

    }
}
