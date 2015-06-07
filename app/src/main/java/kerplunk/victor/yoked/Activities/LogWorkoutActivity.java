package kerplunk.victor.yoked.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kerplunk.victor.yoked.Classes.Workout;
import kerplunk.victor.yoked.R;


public class LogWorkoutActivity extends ActionBarActivity {
    private static AlertDialog alert;
    protected ParseObject object;

    View alertDialogView;
    CheckBox tempCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);

        Intent intent = getIntent();
        final String obId = intent.getStringExtra("OBJECT_ID");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Program");
        query.whereEqualTo("objectId", obId);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                object = parseObject;

                //Get the name of the workout
                final String workoutName = object.getString("workout_name");
                //Get the workout subtitle
                final String workoutSubtitle = object.getString("workout_subtitle");
                //Get the workout icon
                final String workoutIcon = object.getString("workout_icon");
                //Get the muscles worked
                final String musclesWorked = object.getString("muscles_worked");
                //Get the full name of the workout
                final String fullWorkoutName = object.getString("full_workout_name");

                //Retrieve the exercises for this workout as a JSONArray from the database
                JSONArray tempExercisesPerformed = object.getJSONArray("exercises_performed");
                //Convert it to a string array
                final String exercisesPerformed[] = new String[tempExercisesPerformed.length()];
                //Iterate through the number of exercises
                for (int i = 0; i < tempExercisesPerformed.length(); i++) {
                    try {
                        exercisesPerformed[i] = tempExercisesPerformed.getString(i);
                    } catch (JSONException f) {
                        f.printStackTrace();
                    }
                }

                //Retrieving and indexing the rep ranges
                JSONArray tempRepRanges = object.getJSONArray("rep_ranges");
                String repRanges[] = new String[tempRepRanges.length()];
                for (int i = 0; i < tempRepRanges.length(); i++) {
                    try {
                        repRanges[i] = tempRepRanges.getString(i);
                    } catch (JSONException f) {
                        f.printStackTrace();
                    }
                }

                //Retrieving and indexing the number of sets
                JSONArray tempNumSets = object.getJSONArray("number_of_sets");
                final int numberOfSets[] = new int[tempNumSets.length()];
                for (int i = 0; i < tempNumSets.length(); i++) {
                    try {
                        numberOfSets[i] = Integer.parseInt(tempNumSets.getString(i));
                    } catch (JSONException f) {
                        f.printStackTrace();
                    }
                }

                //Code to retrieve the main layout and add items programmatically.
                LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);

                //Arraylist of checkboxes, we may reference and change values this way
                ArrayList<CheckBox> sets = new ArrayList<>();

                //Used to keep track of what set we are in for the whole workout
                int setIndex = 0;

                //Programmatically add the title to the activity
                TextView programName = new TextView(getApplicationContext());
                programName.setText(workoutName);
                programName.setTextSize(20);
                programName.setTypeface(null, Typeface.BOLD);
                programName.setTextColor(Color.parseColor("#FFFFFF"));
                programName.setGravity(Gravity.CENTER);
                layout.addView(programName);

                /*For the amount of exercises that compose the workout, we will iterate through
                * them, programmatically creating the correct number of content and displaying
                * the rep ranges accordingly by using nested loops and indexing in the right
                * places*/
                for (int i = 0; i < exercisesPerformed.length; i++) {
                    //Add the exercise
                    TextView temporaryExercise = new TextView(getApplicationContext());
                    temporaryExercise.setText("\n" + exercisesPerformed[i]);
                    temporaryExercise.setTextColor(Color.parseColor("#FFFFFF"));
                    temporaryExercise.setTextSize(16);
                    temporaryExercise.setTypeface(null, Typeface.BOLD);
                    layout.addView(temporaryExercise);
                    //Add a divider under each exercise name
                    ImageView exerciseDivider = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams lp2 =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4);
                    exerciseDivider.setLayoutParams(lp2);
                    exerciseDivider.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    layout.addView(exerciseDivider);

                    //Inner loop which creates the appropriate number of sets for each exercise
                    for (int j = 0; j < numberOfSets[i]; j++) {
                        //Add the sets
                        sets.add(new CheckBox(getApplicationContext()));
                        //Index to the rep range
                        sets.get(setIndex).setText(repRanges[i]);
                        sets.get(setIndex).setTextColor(Color.parseColor("#FFFFFF"));
                        layout.addView(sets.get(setIndex));
                        setIndex++;
                    }

                    //Call the method that will see if the user clicks a check box to log a set
                    listenForChanges(sets);
                }


                Button saveWorkoutButton = (Button) findViewById(R.id.saveButton);
                //Copy of our checkboxes that we can manipulate for use in saving our workout
                final ArrayList<CheckBox> finalSets = sets;
                saveWorkoutButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        //The final string to be saved to our database
                        String WorkoutCompleted = "";
                        int indexer = 0;
                        for (int h = 0; h < exercisesPerformed.length; h++) {
                            String originalIfEmpty = WorkoutCompleted;
                            WorkoutCompleted += exercisesPerformed[h] + "\n";
                            int ifEmptyCount = 0;


                            for (int i = 0; i < numberOfSets[h]; i++) {
                                if ((finalSets.get(indexer).getText().toString()).contains("x")) {
                                    WorkoutCompleted += finalSets.get(indexer).getText() + "\n";
                                } else {
                                    ifEmptyCount++;
                                }

                                //If the user did not complete any sets for an exercise, we skip it.
                                if (ifEmptyCount == numberOfSets[h]) {
                                    WorkoutCompleted = originalIfEmpty;
                                }

                                indexer++;
                            }
                            WorkoutCompleted += "\n";
                        }

                        //Get the current date here
                        String pattern = "MM-dd-yyyy";
                        String currentDate = new SimpleDateFormat(pattern).format(new Date());

                        //Create a new workout object and save all attributes for our class
                        Workout newWorkout = new Workout();
                        newWorkout.setLog(WorkoutCompleted);
                        newWorkout.setUser(ParseUser.getCurrentUser());
                        newWorkout.setDate(currentDate);
                        newWorkout.setUsername(ParseUser.getCurrentUser().getUsername());
                        newWorkout.setWorkoutName(workoutName);
                        newWorkout.setIcon(workoutIcon);
                        newWorkout.setMusclesWorked(musclesWorked);
                        newWorkout.setFullName(fullWorkoutName);
                        newWorkout.setWorkoutSubtitle(workoutSubtitle);

                        //Save the results to our database
                        newWorkout.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                } else {
                                    Toast.makeText(
                                            LogWorkoutActivity.this,
                                            "Error saving: " + e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                        //Send the user to an activity where they can view the completed workout
                        Intent intent = new Intent(LogWorkoutActivity.this, ViewWorkoutActivity.class);
                        intent.putExtra("RetrieveWorkout", WorkoutCompleted);
                        intent.putExtra("RetrieveDate", currentDate);
                        intent.putExtra("RetrieveWorkoutName", workoutName);
                        intent.putExtra("RetriveSubtitle", workoutSubtitle);
                        intent.putExtra("FROM_ACTIVITY", "Log_Workout");
                        startActivity(intent);
                    }
                });
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_workout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.WeightCalculator) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflaterb
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View weightCalcView = inflater.inflate(R.layout.weight_cal, null);
            Button calcButton = (Button) weightCalcView.findViewById(R.id.calc_button);
            calcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText weight = (EditText) weightCalcView.findViewById(R.id.et_weight);

                    if (!(weight.equals(""))) {
                        double desiredWeight = Double.parseDouble(weight.getText().toString());
                        if(desiredWeight <= 45) {
                            Toast.makeText(weightCalcView.getContext(), "Weight must be larger than 45",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            DecimalFormat df = new DecimalFormat("#.0");
                            double weightForEachSide = (desiredWeight - 45) / 2;
                            TextView weightToUse = (TextView) weightCalcView.findViewById(R.id.weightToUse_tv);
                            weightToUse.setText(" " + df.format(weightForEachSide) + "lbs");
                        }
                    } else {
                        Toast.makeText(weightCalcView.getContext(), "Make sure you entered a valid value!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });

            builder.setView(weightCalcView)

                    // Add action buttons
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.show();
        }

        if (id == R.id.PercentCalculator) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflaterb
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View percentCalcView = inflater.inflate(R.layout.percent_calc, null);
            Button calcButton = (Button) percentCalcView.findViewById(R.id.calc_button);
            calcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText weight = (EditText) percentCalcView.findViewById(R.id.et_weight);
                    EditText percent = (EditText) percentCalcView.findViewById(R.id.percentage_et);
                    String weightToString = weight.getText().toString();
                    String percentToString = percent.getText().toString();
                        if (!(weightToString.equals("") || percentToString.equals(""))) {
                            double valueOfWeight = Double.parseDouble(weightToString);
                            double valueOfPercent = Double.parseDouble(percentToString);
                            double resultingWeight = valueOfWeight * (valueOfPercent/100);
                            DecimalFormat df = new DecimalFormat("#.0");
                            TextView weightToUse = (TextView) percentCalcView.findViewById(R.id.weightToUse_tv);
                            weightToUse.setText(" " + df.format(resultingWeight) + "lbs");
                        } else {
                            Toast.makeText(percentCalcView.getContext(), "Make sure you filled in both fields!",
                                    Toast.LENGTH_SHORT).show();
                        }

                }
            });

            builder.setView(percentCalcView)

                    // Add action buttons
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    private void listenForChanges(ArrayList<CheckBox> sets) {
        final ArrayList<CheckBox> tempSets = sets;

        for(int i = 0; i < sets.size(); i++) {
            final String tempCheckText = sets.get(i).getText().toString();
            final int tempIndex = i;
            sets.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub

                    if (buttonView.isChecked()) {

                        CheckBox tempCheckBox = giveAlert(tempSets.get(tempIndex));

                        tempSets.get(tempIndex).setText(tempCheckBox.getText().toString());

                    } else {
                        tempSets.get(tempIndex).setText(tempCheckText);

                    }

                }
            });
        }
    }

    public CheckBox giveAlert(CheckBox box) {
        tempCheckbox = box;

        String templateValue = box.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialogView = inflater.inflate(R.layout.enter_set, null);


            builder.setView(alertDialogView)
                    // Add action buttons
                    .setPositiveButton("Log", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {




                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            tempCheckbox.setChecked(false);
                            dialog.cancel();
                        }
                    });
            alert = builder.create();
            alert.show();


        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new CustomListener(alert));





        return tempCheckbox;
    }


    private class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {

            EditText weight = (EditText) alertDialogView.findViewById(R.id.et_weight);
            EditText reps = (EditText) alertDialogView.findViewById(R.id.et_reps);

            String tempWeight = weight.getText().toString();
            String tempReps = reps.getText().toString();



            if(!(tempWeight.equals("") || tempReps.equals(""))){
                String tempString = tempWeight + " lbs x " + tempReps + " reps";
                tempCheckbox.setText(tempString);
                tempCheckbox.setChecked(true);
                dialog.dismiss();

            }else{
                Toast.makeText(LogWorkoutActivity.this, "Make sure you have a value for both fields!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
