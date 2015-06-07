package kerplunk.victor.yoked.Classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Victor on 5/24/2015.
 */
@ParseClassName("Workout")
public class Workout extends ParseObject {
    public Workout(){

    }

    public String getMusclesWorked() {return  getString("muscles_worked");}

    public String getFullName() {return getString("full_workout_name");}

    public void setFullName(String name) { put("full_workout_name", name);}

    public String getWorkoutSubtitle(){
        return  getString("workout_subtitle");
    }

    public void setWorkoutSubtitle(String name){
        put("workout_subtitle", name);
    }

    public void setMusclesWorked(String muscles) { put("muscles_worked", muscles);}

    public String getWorkoutName(){
        return  getString("workout_name");
    }

    public void setWorkoutName(String name){
        put("workout_name", name);
    }

    public String getIcon(){
        return  getString("workout_icon");
    }

    public void setIcon(String icon){
        put("workout_icon", icon);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }

    public String getLog() {
        return getString("workout_log");
    }

    public void setLog(String log) {
        put("workout_log", log);
    }


    public String getDate() {
        return getString("date");
    }

    public void setDate(String date) {
        put("date", date);
    }

    public String getUsername() {
        return getString("username");
    }

    public void setUsername(String username) {
        put("username", username);
    }


}
