package kerplunk.victor.yoked.Classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

/**
 * Created by Victor on 5/22/2015.
 */
@ParseClassName("Program")
public class Program extends ParseObject{
/*    private String mExerciseNames[];
    private String mProgramName;
    private String mRepRanges[];
    private int mNumberOfSets[];*/

    public Program () {
/*        mProgramName = programName;
        mExerciseNames = exercises;
        mRepRanges = repRanges;
        mNumberOfSets = numOfSets;*/
    }

    public JSONArray getExercisesPerformed() {
        return getJSONArray("exercises_perfomed");
    }

    public void setExercisesPerformed(JSONArray exercises) {
        put("exercises_performed", exercises);
    }

    public JSONArray getRepRanges() {
        return getJSONArray("rep_ranges");
    }

    public void setRepRanges(JSONArray repRanges) {
        put("rep_ranges", repRanges);
    }

    public JSONArray getNumberOfSets() {
        return getJSONArray("number_of_sets");
    }

    public void setNumberOfSets(JSONArray numOfSets) {
        put("number_of_sets", numOfSets);
    }

    public String getMusclesWorked() {
        return getString("muscles_worked");
    }

    public void setMusclesWorked(String muscles) {
        put("muscles_worked", muscles);
    }

    public String getWorkoutName(){
        return  getString("workout_name");
    }

    public void setWorkoutName(String name){
        put("workout_name", name);
    }

    public String getWorkoutSubtitle(){
        return  getString("workout_subtitle");
    }

    public void setWorkoutSubtitle(String name){
        put("workout_subtitle", name);
    }

    public String getFullName() {return getString("full_workout_name");}

    public void setFullName(String name) { put("full_workout_name", name);}

    public String getIcon(){
        return  getString("workout_icon");
    }

    public void setIcon(String icon){
        put("workout_icon", icon);
    }

    public String getUser() {
        return getString("user");
    }

    public void setUser(String user){
        put("user", user);
    }

}
