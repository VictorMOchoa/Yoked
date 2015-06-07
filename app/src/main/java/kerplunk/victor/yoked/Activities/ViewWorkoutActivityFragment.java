package kerplunk.victor.yoked.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kerplunk.victor.yoked.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ViewWorkoutActivityFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_view_workout, container, false);

    }
}
