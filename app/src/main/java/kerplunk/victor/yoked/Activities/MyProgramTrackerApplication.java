package kerplunk.victor.yoked.Activities;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import kerplunk.victor.yoked.Classes.Program;
import kerplunk.victor.yoked.Classes.Workout;

/**
 * Created by Victor on 5/24/2015.
 */

/*
    This class will enable us to use our Parse classes that we have created online with our app
* */
public class MyProgramTrackerApplication extends Application {

    public void onCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Workout.class);
        ParseObject.registerSubclass(Program.class);
        Parse.initialize(this, "HCQo4lAuvlUWNEW4xpsN25tpHbN4uZsRaIc4ut89", "BqElKdXcHmyRoJyXWarfsaW5lVGp51B8ttUif0Ej");
    }
}

