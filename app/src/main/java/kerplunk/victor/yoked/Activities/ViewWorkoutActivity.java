package kerplunk.victor.yoked.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kerplunk.victor.yoked.R;


public class ViewWorkoutActivity extends ActionBarActivity {

    @InjectView(R.id.workout_tv) TextView mWorkoutText;
    @InjectView(R.id.date_tv) TextView mDateText;
    @InjectView(R.id.workoutLabel) TextView mWorkoutName;
    @InjectView(R.id.subtitle_tv) TextView mWorkoutSubtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        Intent intent = getIntent();

        if(intent.getStringExtra("FROM_ACTIVITY").equals("PastWorkouts")){

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
            String receivedDate = intent.getStringExtra("DATE_VALUE");
            String receivedName = intent.getStringExtra("NAME_VALUE");
            query.whereEqualTo("date", receivedDate);
            query.whereEqualTo("full_workout_name", receivedName);
            query.orderByAscending("date");

            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    mWorkoutText.setText(parseObject.getString("workout_log"));
                    mWorkoutName.setText(parseObject.getString("workout_name"));
                    mWorkoutSubtitle.setText(parseObject.getString("workout_subtitle"));
                }

            });

            mDateText.setText(receivedDate);


        }

        else{
            String workoutToDisplay = intent.getStringExtra("RetrieveWorkout");
            String subtitleToDisplay = intent.getStringExtra("RetrieveSubtitle");
            String workoutNameToDisplay = intent.getStringExtra("RetrieveWorkoutName");
            String dateToDisplay = intent.getStringExtra("RetrieveDate");
            mDateText.setText(dateToDisplay);
            mWorkoutText.setText(workoutToDisplay);
            mWorkoutName.setText(workoutNameToDisplay);
            mWorkoutSubtitle.setText(subtitleToDisplay);

        }

        Button mBackButton = (Button) findViewById(R.id.button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewWorkoutActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
